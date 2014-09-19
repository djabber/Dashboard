using System;
using System.Collections;
using System.IO;
using log4net;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// Class for manipulating files
	/// </summary>
	public class FileUtil
	{
		private static ILog log = LogManager.GetLogger(typeof (FileUtil));

		/// <summary>
		/// Writes the byte[] data to a file named filename.
		/// </summary>
		/// <param name="data">A byte[] that will be written to file</param>
		/// <param name="filename">The file that the inputStream will be written to</param>
		public static void writeFile(byte[] data, String filename)
		{
			StreamWriter writer = null;
			IEnumerator enu = data.GetEnumerator();

			try
			{
				writer = File.CreateText(filename);

				while (enu.MoveNext())
				{
					writer.Write(Convert.ToChar(enu.Current));
				}

				writer.Flush();
			}
			catch (Exception e)
			{
				log.Error(e.Message, e);
			}
			finally
			{
				if (writer != null)
				{
					writer.Close();
				}
			}
		}
	}
}