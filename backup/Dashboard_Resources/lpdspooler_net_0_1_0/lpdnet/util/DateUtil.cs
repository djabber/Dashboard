using System;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// Utility class for manipulating Dates
	/// </summary>
	public class DateUtil
	{
		/// <summary>
		/// Creates a String that represents the Date passed in. 
		/// If the date passed in is null the null is returned.
		/// </summary>
		/// <param name="dateTime"></param>
		/// <returns>A string representation of dateTime</returns>
		public static String createDateString(DateTime dateTime)
		{
			String result = null;
			if (dateTime != DateTime.MinValue)
			{
				result = dateTime.ToString("MM/dd/yyyy HH:mm:ss");
			}
			return result;
		}
	}
}