using System.IO;

namespace sf.net.lpdnet.command
{
	/// <summary>
	/// This class is the super class for any class that handles a lpd command.
	/// There will be several subclasses for each top level command of the lpd deamon.
	/// </summary>
	public abstract class CommandHandler
	{
		/// <summary>
		/// The command received. This is an octect.
		/// </summary>
		protected byte[] command;

		/// <summary>
		/// The connection stream reader
		/// </summary>
		protected StreamReader reader;

		/// <summary>
		/// The connection stream writer
		/// </summary>
		protected StreamWriter writer;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="command">Command received from client connection</param>
		/// <param name="reader">Stream reader</param>
		/// <param name="writer">Stream writer</param>
		public CommandHandler(byte[] command, StreamReader reader, StreamWriter writer)
		{
			this.command = command;
			this.reader = reader;
			this.writer = writer;
		}

		/// <summary>
		/// Processes the command in the concrete subclass.  When a command
		/// is sent by the user a concrete class such as CommandReceiveJob
		/// runs its execute method to handle the command.
		/// <exception>Throws an LPDException if an Error occurs.</exception>
		/// </summary>
		public abstract void execute();
	}
}