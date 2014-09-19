using System.Collections;
using System.IO;
using log4net;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.command
{
	/// <summary>
	/// Class to handle all the commands defined in the RFC1179
	/// </summary>
	public class LPDCommands
	{
		private static ILog log = LogManager.GetLogger(typeof (LPDCommands));

		/// <summary>
		/// Creates the concrete instance of the command class required.
		/// Then it calls that class' execute() method.
		/// </summary>
		/// <param name="command">Command received from client connection</param>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		/// <exception cref="LPDException">
		/// If the command is not suppoerted or something went wrong during the command excecution
		/// </exception>
		public void handleCommand(byte[] command, StreamReader reader, StreamWriter writer)
		{
			CommandHandler commandHandler = null;
			try
			{
				commandHandler = createCommandHandler(command, reader, writer);
				commandHandler.execute();
			}
			catch (LPDException e)
			{
				log.Error("Could not properly handle command:" + StringUtil.parseHumanRead(command));
				log.Error(e.Message);
			}
		}

		/// <summary>
		/// Creates a command handler depending on the command code. All LPD commands defined in the RFC1179
		/// are implemented here.
		/// </summary>
		/// <param name="command">Command received from client connection</param>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		/// <returns>CommandHandler object</returns>
		private CommandHandler createCommandHandler(byte[] command, StreamReader reader, StreamWriter writer)
		{
			CommandHandler result = null;
			IList info = ByteUtil.parseCommand(command);

			try
			{
				if (null != info && info.Count > 0)
				{
					byte[] cmd = (byte[]) info[0];
					// receive job command
					if (0x1 == cmd[0])
					{
						log.Debug("Print Job Command");
						result = new CommandPrintJob(command, reader, writer);
					}
					else if (0x2 == cmd[0])
					{
						log.Debug("Receive Job Command");
						result = new CommandReceiveJob(command, reader, writer);
					}
					else if (0x3 == cmd[0])
					{
						log.Debug("Report Queue State Short Command");
						result = new CommandReportQueueStateShort(command, reader, writer);
					}
					else if (0x4 == cmd[0])
					{
						log.Debug("Report Queue State Long Command");
						result = new CommandReportQueueStateLong(command, reader, writer);
					}
					else if (0x5 == cmd[0])
					{
						log.Debug("Remove Print Job Command");
						result = new CommandRemovePrintJob(command, reader, writer);
					}
					else
					{
						throw new LPDException("We do not support command:" + StringUtil.parseHumanRead(cmd));
					}
				}
				else
				{
					throw new LPDException("Command passed in was bad, command=" + StringUtil.parseHumanRead(command));
				}
			}
			catch (LPDException e)
			{
				log.Error("Could not properly handle command:" + StringUtil.parseHumanRead(command));
				log.Error(e.Message);
			}

			return result;
		}
	}
}