using System;
using System.Collections;
using System.IO;
using System.Text;
using log4net;
using sf.net.lpdnet.common;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.queue;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.command
{
	/// <summary>
	///  This class handles the Send queue state (short) Command in RFC1179.
	/// The RFC description is below:
	/// <br></br>
	/// <br></br>
	/// 5.3 03 - Send queue state (short)
	/// <br></br>
	/// <br></br>
	/// +----+-------+----+------+----+<br></br>
	/// | 03 | Queue | SP | List | LF |<br></br>
	/// +----+-------+----+------+----+<br></br>
	/// <br></br>
	/// Command code - 03<br></br>
	/// Operand 1 - Printer queue name<br></br>
	/// Other operands - User names or job numbers<br></br>
	/// <p>
	/// If the user names or job numbers or both are supplied then only those
	/// jobs for those users or with those numbers will be sent.
	/// </p>
	/// <p>
	/// The response is an ASCII stream which describes the printer queue.
	/// The stream continues until the connection closes.  Ends of lines are
	/// indicated with ASCII LF control characters.  The lines may also
	/// contain ASCII HT control characters.
	/// </p>
	/// </summary>
	public class CommandReportQueueStateShort : CommandHandler
	{
		private static ILog log = LogManager.GetLogger(typeof (CommandReportQueueStateShort));

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="command">Command received from client connection</param>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		public CommandReportQueueStateShort(byte[] command, StreamReader reader, StreamWriter writer)
			: base(command, reader, writer)
		{
		}

		/// <summary>
		/// Writes a breif text table that displays the current print jobs.  This
		/// is invoked by the commands: lpq or lpstat.  A queue name must
		/// be specified by the client.
		/// </summary>
		/// <exception cref="LPDException"></exception>
		public override void execute()
		{
			IList info = ByteUtil.parseCommand(command);
			if (null != info && info.Count > 0)
			{
				byte[] cmd = (byte[]) info[0];
				byte[] queue = (byte[]) info[1];
				String queueName = (StringUtil.parse(queue)).Trim(); // parseCommand() should guarantee queue is not null
				try
				{
					// query queue for state
					IList queueudPrintJobInfos = Queues.getInstance().listAllPrintJobs(queueName);
					// create a ASCII representation of the List
					StringBuilder sb = new StringBuilder();
					sb.Append(StringUtil.createFixedLengthString("Job Id", PrintJob.JOB_ID_LENGTH));
					sb.Append(StringUtil.createFixedLengthString("Name", PrintJob.JOB_NAME_LENGTH));
					sb.Append(StringUtil.createFixedLengthString("Owner", PrintJob.JOB_OWNER_LENGTH));
					sb.Append("\n");

					IEnumerator enu = queueudPrintJobInfos.GetEnumerator();
					// create ASCII string
					while (enu.MoveNext())
					{
						QueuedPrintJobInfo queuedPrintJobInfo = (QueuedPrintJobInfo) enu.Current;
						sb.Append(queuedPrintJobInfo.getShortDescription());
						sb.Append("\n");
					}

					// list print jobs command
					if (0x3 != cmd[0])
					{
						throw new LPDException("cmd[0]=" + cmd[0] + ",should of been 0x3");
					}
					else
					{
						log.Debug("Report Queue State Command Short");
						// write ASCII string to output stream
						log.Debug("Print Queue status short=" + sb.ToString());
						writer.Write(sb.ToString().ToCharArray());
						writer.Flush();
						// close os connection (handled in LPDCommands.java)
					}
				}
				catch (QueueException e)
				{
					log.Error(e.Message);
					throw new LPDException(e.Message, e);
				}
				catch (IOException e)
				{
					log.Error(e.Message);
					throw new LPDException(e.Message, e);
				}
			}
			else
			{
				throw new LPDException("command not understood, command=" + StringUtil.parse(command));
			}
		}
	}
}