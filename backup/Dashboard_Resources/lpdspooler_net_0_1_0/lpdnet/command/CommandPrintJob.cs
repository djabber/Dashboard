using System.Collections;
using System.IO;
using log4net;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.command
{
	/// <summary>
	/// This class handles the Print any waiting jobs Command in RFC1179.
	/// The RFC description is below:
	/// <br></br>
	/// <br></br>
	/// 5.1 01 - Print any waiting jobs
	/// <br></br>
	/// <br></br>
	/// +----+-------+----+<br></br>
	/// | 01 | Queue | LF |<br></br>
	/// +----+-------+----+<br></br>
	/// <br></br>
	/// Command code - 01<br></br>
	/// Operand - Printer queue name<br></br>
	/// <p>
	/// This command starts the printing process if it not already running.
	/// NOTE: This is not implemented since we intend to hold the print job
	/// </p>
	/// </summary>
	public class CommandPrintJob : CommandHandler
	{
		private static ILog log = LogManager.GetLogger(typeof (CommandPrintJob));

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="command">Command received from client connection</param>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		public CommandPrintJob(byte[] command, StreamReader reader, StreamWriter writer)
			: base(command, reader, writer)
		{
		}

		/// <summary>
		/// Parse the command and do nothing since we intend to hold the print job
		/// </summary>
		public override void execute()
		{
			IList info = ByteUtil.parseCommand(command);

			if (null != info && info.Count > 0)
			{
				byte[] cmd = (byte[]) info[0];
				// print job command
				if (0x1 == cmd[0])
				{
					log.Debug("Print Job Command");
				}
				else
				{
					throw new LPDException("cmd[0]=" + cmd[0] + ",should of been 0x1");
				}
			}
			else
			{
				throw new LPDException("Command not understood, command=" + StringUtil.parseHumanRead(command));
			}
		}
	}
}