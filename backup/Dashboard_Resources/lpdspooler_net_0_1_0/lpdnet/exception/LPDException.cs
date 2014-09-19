using System;

namespace sf.net.lpdnet.exception
{
	/// <summary>
	/// Summary description for LPDException.
	/// </summary>
	public class LPDException : Exception
	{
		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		public LPDException(string message)
			: base(message)
		{
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		/// <param name="innerException"></param>
		public LPDException(string message, Exception innerException)
			: base(message, innerException)
		{
		}
	}
}