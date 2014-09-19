using System;

namespace sf.net.lpdnet.exception
{
	/// <summary>
	/// Handler exception
	/// </summary>
	public class HandlerException : Exception
	{
		/// <summary>
		/// Constructor
		/// </summary>
		/// <param name="message">Exception message</param>
		public HandlerException(string message)
			: base(message)
		{
		}

		/// <summary>
		/// Constructor
		/// </summary>
		/// <param name="message">Exception message</param>
		/// <param name="innerException">Inner exception</param>
		public HandlerException(string message, Exception innerException)
			: base(message, innerException)
		{
		}
	}
}