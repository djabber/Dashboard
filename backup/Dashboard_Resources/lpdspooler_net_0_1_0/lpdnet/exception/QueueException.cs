using System;

namespace sf.net.lpdnet.exception
{
	/// <summary>
	/// Summary description for QueueException.
	/// </summary>
	public class QueueException : Exception
	{
		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		public QueueException(string message)
			: base(message)
		{
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		/// <param name="innerException"></param>
		public QueueException(string message, Exception innerException)
			: base(message, innerException)
		{
		}
	}
}