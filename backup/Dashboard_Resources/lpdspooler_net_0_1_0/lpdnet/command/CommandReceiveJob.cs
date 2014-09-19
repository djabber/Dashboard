using System;
using System.Collections;
using System.IO;
using log4net;
using sf.net.lpdnet.common;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.queue;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.command
{
	/// <summary>
	/// This class handles the Receive a printer job Command in RFC1179.
	/// The RFC description is below:
	/// <br></br>
	/// <br></br>
	/// 5.2 02 - Receive a printer job
	/// <br></br>
	/// <br></br>
	/// +----+-------+----+<br></br>
	/// | 02 | Queue | LF |<br></br>
	/// +----+-------+----+<br></br>
	/// <br></br>
	/// Command code - 02<br></br>
	/// Operand - Printer queue name<br></br>
	/// <p>
	/// Receiving a job is controlled by a second level of commands.  The
	/// daemon is given commands by sending them over the same connection.
	/// The commands are described in the next section (6).
	/// </p>
	/// <P>
	/// After this command is sent, the client must read an acknowledgement
	/// octet from the daemon.  A positive acknowledgement is an octet of
	/// zero bits ('\0x').  A negative acknowledgement is an octet of any other
	/// pattern.
	/// </P>
	/// </summary>
	public class CommandReceiveJob : CommandHandler
	{
		private static ILog log = LogManager.GetLogger(typeof (CommandReceiveJob));

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="command">Command received from client connection</param>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		public CommandReceiveJob(byte[] command, StreamReader reader, StreamWriter writer)
			: base(command, reader, writer)
		{
		}

		/// <summary>
		/// Executes command
		/// </summary>
		public override void execute()
		{
			IList info = ByteUtil.parseCommand(command);
			if (null != info && info.Count > 1)
			{
				byte[] cmd = (byte[]) info[0];
				byte[] queue = (byte[]) info[1];

				String queueName = (StringUtil.parse(queue)).Trim(); // parseCommand() should guarantee queue is not null
				// receive job command
				if (0x2 != cmd[0])
				{
					throw new LPDException("Command passed in was bad, cmd[0]=" + StringUtil.parseHumanRead(cmd));
				}
				else if (StringUtil.isEmpty(queueName))
				{
					throw new LPDException("QueueName passed in was empty for command=" + StringUtil.parseHumanRead(command));
				}

				try
				{
					writer.Write(NetUtil.GOOD_ACK); // write ACK to client
					log.Debug("Receive Job Command");
					PrintJob job = receivePrintJob(reader, writer);

					Queues.getInstance().addPrintJob(queueName, job);
				}
				catch (QueueException e)
				{
					log.Error(e.Message);
					throw new LPDException(e.Message);
				}
			}
			else
			{
				throw new LPDException("Command not understood, command=" + StringUtil.parseHumanRead(command));
			}
		}

		/// <summary>
		/// Does the work of receiving the print job.
		/// </summary>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		/// <returns>PrintJob</returns>
		private PrintJob receivePrintJob(StreamReader reader, StreamWriter writer)
		{
			PrintJob printJob = null;
			ControlFile controlFile = null;
			DataFile dataFile = null;

			try
			{
				NetUtil netUtil = new NetUtil();
				byte[] receiveInput = null;
				IList cmd = null;

				//Reads control file or data file, which ever that arrives first
				for (int i = 1; i <= 2; i++)
				{
					receiveInput = netUtil.readNextInput(reader, writer);
					cmd = ByteUtil.parseCommand(receiveInput);
					if (receiveInput[0] == 2)
					{
						controlFile = setControlFile(reader, writer, cmd);
					}
					else if (receiveInput[0] == 3)
					{
						dataFile = setDataFile(reader, writer, cmd);
					}
				}
			}
			catch (Exception e)
			{
				log.Error("Problems reading Input", e);
			}

			if (null != controlFile && null != dataFile)
			{
				printJob = new PrintJob(controlFile, dataFile);
			}

			return printJob;
		}

		/// <summary>
		/// Does the work of receiving the control file
		/// </summary>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		/// <param name="cmd">A list containing the information extracted from the command octet</param>
		/// <returns>ControlFile object populated</returns>
		/// <exception cref="LPDException">
		/// If the control file can not be read or parsed
		/// </exception>
		private ControlFile setControlFile(StreamReader reader, StreamWriter writer, IList cmd)
		{
			ControlFile controlFile = null;

			try
			{
				NetUtil netUtil = new NetUtil();
				String controlFileSize = StringUtil.parse((byte[]) cmd[1]);
				String controlFileHeader = StringUtil.parse((byte[]) cmd[2]);
				IList headerVector = StringUtil.parsePrintFileName(controlFileHeader);
				if (null != headerVector && headerVector.Count == 3)
				{
					byte[] cFile = netUtil.readControlFile(reader, writer);
					controlFile = new ControlFile();
					controlFile.Size = controlFileSize;
					controlFile.JobNumber = (String) headerVector[1];
					controlFile.HostName = (String) headerVector[2];
					controlFile.Contents = cFile;
					log.Debug("Control File=" + StringUtil.parseHumanRead(cFile));
					controlFile.setControlFileCommands(cFile);
					log.Debug("Control File Commands=" + controlFile.ControlFileCommands.ToString());
					return controlFile;
				}
				else
				{
					throw new LPDException("ControlFileHeader did not parse properly, controlFileHeader=" + controlFileHeader);
				}
			}
			catch (IOException e)
			{
				log.Error("Unable to receive the control file.");
				log.Error(e.Message);
				throw new LPDException(e.Message, e);
			}
		}

		/// <summary>
		/// Does the work of receiving the data file
		/// </summary>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		/// <param name="cmd">A list containing the information extracted from the command octet</param>
		/// <returns>DataFile object populated</returns>
		/// <exception cref="LPDException">
		/// If the data file can not be read or parsed
		/// </exception>
		/// 
		private DataFile setDataFile(StreamReader reader, StreamWriter writer, IList cmd)
		{
			DataFile dataFile = null;

			// get the data file
			try
			{
				NetUtil netUtil = new NetUtil();
				String dataFileSize = StringUtil.parse((byte[]) cmd[1]);

				log.Debug("DataFile size=" + dataFileSize);

				String dataFileHeader = StringUtil.parse((byte[]) cmd[2]);
				IList headerVector = StringUtil.parsePrintFileName(dataFileHeader);
				if (null != headerVector && headerVector.Count == 3)
				{
					byte[] dFile = null;
					int dfSize = 0;

					try
					{
						dfSize = Convert.ToInt32(dataFileSize);
					}
					catch (Exception e)
					{
						log.Error(e.Message);
					}

					if (0 == dfSize)
					{
						dFile = netUtil.readPrintFile(reader, writer);
					}
					else
					{
						dFile = netUtil.readPrintFile(reader, writer, dfSize);
					}

					dataFile = new DataFile();
					dataFile.Size = dataFileSize;
					dataFile.HostName = (String) headerVector[2];
					dataFile.JobNumber = (String) headerVector[1];
					dataFile.Contents = dFile;
					log.Debug("Data File=" + StringUtil.parseHumanRead(dFile));
					return dataFile;
				}
				else
				{
					throw new LPDException("DataFileHeader did not parse properly, dataFileHeader=" + dataFileHeader);
				}
			}
			catch (IOException e)
			{
				log.Error("Unable to receive the data file.");
				log.Error(e.Message);
				throw new LPDException(e.Message, e);
			}
		}
	}
}