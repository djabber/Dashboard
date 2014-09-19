using System;

namespace sf.net.lpdnet.exception
{
	/// <summary>
	/// Summary description for ObjectNotFoundException.
	/// </summary>
	public class ObjectNotFoundException : Exception
	{
		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		public ObjectNotFoundException(string message)
			: base(message)
		{
		}
	}
}