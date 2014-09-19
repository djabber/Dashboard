using System;

namespace sf.net.lpdnet.exception
{
	/// <summary>
	/// Summary description for CloneNotSupportedException.
	/// </summary>
	public class CloneNotSupportedException : Exception
	{
		/// <summary>
		/// 
		/// </summary>
		public CloneNotSupportedException()
		{
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		public CloneNotSupportedException(string message)
			: base(message)
		{
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		/// <param name="innerException"></param>
		public CloneNotSupportedException(string message, Exception innerException)
			: base(message, innerException)
		{
		}
	}
}