namespace sf.net.lpdnet.common
{
	/// <summary>
	/// Holds control information
	/// </summary>
	public class ControlFile : PrintFile
	{
		private ControlFileCommands controlFileCommands;

		/// <summary>
		/// Control file class
		/// </summary>
		public ControlFile()
		{
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="command"></param>
		public void setControlFileCommands(byte[] command)
		{
			controlFileCommands = new ControlFileCommands(command);
		}

		/// <summary>
		/// 
		/// </summary>
		public ControlFileCommands ControlFileCommands
		{
			get { return controlFileCommands; }
		}
	}
}