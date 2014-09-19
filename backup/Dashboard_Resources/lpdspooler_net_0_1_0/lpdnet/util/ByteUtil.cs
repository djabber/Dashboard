using System;
using System.Collections;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// 
	/// </summary>
	public class ByteUtil
	{
		/// <summary>
		/// Creates a list of byte arrays from the command passed in.
		/// </summary>
		/// <param name="command">command octet</param>
		/// <returns>IList object</returns>
		public static IList parseCommand(byte[] command)
		{
			IList result = new ArrayList();
			IList bytes = new ArrayList();

			if (null != command && command.Length > 0)
			{
				// get the command code (the first byte)
				byte[] commandCode = new byte[1];
				commandCode[0] = command[0];
				result.Add(commandCode);

				for (int i = 1; i < command.Length; i++)
				{
					// if we see a space or a line feed
					// then add the bytes acquired to the Vector
					if (32 == command[i] || 10 == command[i])
					{
						result.Add(listToByteArray(bytes));
						bytes = new ArrayList();
					}
					else
					{
						bytes.Add(command[i]);
					}
				}
			}

			return result;
		}

		/// <summary>
		/// Converts a list into an array of bytes
		/// </summary>
		/// <param name="list">List to convert</param>
		/// <returns>list as byte[]</returns>
		public static byte[] listToByteArray(IList list)
		{
			byte[] tmp = new byte[list.Count];

			for (int i = 0; i < list.Count; i++)
			{
				tmp[i] = Convert.ToByte(list[i]);
			}

			return tmp;
		}
	}
}