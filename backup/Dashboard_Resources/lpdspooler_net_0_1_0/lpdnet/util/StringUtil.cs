using System;
using System.Collections;
using System.Text;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// Class for manipulating strings
	/// </summary>
	public class StringUtil
	{
		/// <summary>
		/// This parses the "name" field passed in the header of the control file
		/// or the data files.
		/// </summary>
		/// <param name="header"></param>
		/// <returns></returns>
		/// <example>
		/// <p>Control File header = cfa001MyComputer</p>
		/// <p>Data File header = dfa001MyComputer</p>
		/// </example>
		public static IList parsePrintFileName(String header)
		{
			IList result = new ArrayList();
			String first3Chars = header.Substring(0, 3);
			String jobNumber = header.Substring(3, 6);
			String hostName = header.Substring(6);
			result.Add(first3Chars);
			result.Add(jobNumber);
			result.Add(hostName);
			return result;
		}

		/// <summary>
		/// Constructs a String of the fixed length given.
		/// If the string passed in is greater than length,
		/// then the string is truncated to length.
		/// If the string is greater than length then the string
		/// If s is empty or null, then an empty string of spaces is returned.
		/// is padded with spaces.
		/// </summary>
		/// <param name="s">String to operate on</param>
		/// <param name="length">Desired length</param>
		/// <returns>Fixed length String</returns>
		public static String createFixedLengthString(String s, int length)
		{
			String result = null;
			if (isEmpty(s))
			{
				StringBuilder sb = new StringBuilder(length);
				for (int i = 0; i < length; i++)
				{
					sb.Append(" ");
				}
				result = sb.ToString();
			}
			else
			{
				if (s.Length == length)
				{
					result = s;
				}
				else if (s.Length > length)
				{
					result = s.Substring(0, length);
				}
				else
				{
					// s.length() is < length
					StringBuilder sb = new StringBuilder(length);
					sb.Append(s);
					for (int i = s.Length; i < length; i++)
					{
						sb.Append(" ");
					}
					result = sb.ToString();
				}
			}
			return result;
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="str"></param>
		/// <returns></returns>
		public static bool isEmpty(String str)
		{
			bool result = true;
			if (null != str && str.Length > 0)
			{
				result = false;
			}
			return result;
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="list"></param>
		/// <returns></returns>
		public static String parse(IList list)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < list.Count; i++)
			{
				sb.Append(list[i]).Append(" ");
			}

			return sb.ToString();
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="bytes"></param>
		/// <returns></returns>
		public static String parse(byte[] bytes)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.Length; i++)
			{
				sb.Append((char) bytes[i]);
			}

			return sb.ToString();
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="bytes"></param>
		/// <returns></returns>
		public static String parseHumanRead(byte[] bytes)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.Length; i++)
			{
				sb.Append(Convert.ToChar(bytes[i]));
			}

			return sb.ToString();
		}
	}
}