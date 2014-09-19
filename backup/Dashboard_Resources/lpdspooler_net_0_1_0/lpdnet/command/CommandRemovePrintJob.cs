using System;
using System.Collections;
using System.IO;
using log4net;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.queue;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.command
{
	/// <summary>
	/// This class handles the Remove jobs Command in RFC1179.
	/// The RFC description is below:
	/// <br></br>
	/// <br></br>
	/// 5.5 05 - Remove jobs
	/// <br></br>
	/// <br></br>
	/// +----+-------+----+-------+----+------+----+<br></br>
	/// | 05 | Queue | SP | Agent | SP | List | LF |<br></br>
	/// +----+-------+----+-------+----+------+----+<br></br>
	/// <br></br>
	/// Command code - 05<br></br>
	/// Operand 1 - Printer queue name<br></br>
	/// Operand 2 - User name making request (the agent)<br></br>
	/// Other operands - User names or job numbers<br></br>
	/// <p>
	/// This command deletes the print jobs from the specified queue which
	/// are listed as the other operands.  If only the agent is given, the
	/// command is to delete the currently active job.  Unless the agent is
	/// "root", it is not possible to delete a job which is not owned by the
	/// user.  This is also the case for specifying user names instead of
	/// numbers.  That is, agent "root" can delete jobs by user name but no
	/// other agents can.
	/// </p>
	/// <p>
	/// NOTE: If user is Administrator or root they can delete any job.
	/// </p>
	/// </summary>
	/// 
	public class CommandRemovePrintJob : CommandHandler
	{
		private static ILog log = LogManager.GetLogger(typeof (CommandRemovePrintJob));

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="command">Command received from client connection</param>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		public CommandRemovePrintJob(byte[] command, StreamReader reader, StreamWriter writer)
			: base(command, reader, writer)
		{
		}

		/// <summary>
		/// Removes the print jobs specified by the parameters passed in.
		/// The queue and user need to be set.  The final parameter needs
		/// to be a print job number.
		/// </summary>
		/// <exception cref="LPDException"></exception>
		public override void execute()
		{
			IList info = ByteUtil.parseCommand(command);
			if (null != info && info.Count > 1)
			{
				byte[] cmd = (byte[]) info[0];
				byte[] queue = (byte[]) info[1];
				byte[] user = (byte[]) info[2];
				byte[] jobNumber = new byte[0];
				String queueName = (StringUtil.parse(queue)).Trim(); // parseCommand() should guarantee queue is not null
				String userName = (StringUtil.parse(user)).Trim();
				String jobId = "";

				try
				{
					if (info.Count > 2)
					{
						jobNumber = (byte[]) info[3];
						jobId = StringUtil.parse(jobNumber);
					}
					else
					{
						// query queue for last print job if no print job number given
						IList queueudPrintJobInfos = Queues.getInstance().listAllPrintJobs(queueName);
						QueuedPrintJobInfo queuedPrintJobInfo =
							(QueuedPrintJobInfo) queueudPrintJobInfos[queueudPrintJobInfos.Count - 1];
						jobId = Convert.ToString(queuedPrintJobInfo.Id);
					}

					// remove job command
					if (0x5 == cmd[0])
					{
						log.Debug("Remove Print Job Command");
						Queues.getInstance().removePrintJob(queueName, userName, jobId);
						// removes a print job based on the "list" of user names or job numbers passed in
					}
					else
					{
						throw new LPDException("cmd[0]=" + cmd[0] + ",should of been 0x5");
					}
				}
				catch (QueueException e)
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