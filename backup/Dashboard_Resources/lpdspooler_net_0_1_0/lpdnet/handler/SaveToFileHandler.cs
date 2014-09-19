using System;
using System.IO;
using log4net;
using sf.net.lpdnet.common;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.handler
{
	/// <summary>
	/// Handler that saves the print job data into a specified directory
	/// </summary>
	public class SaveToFileHandler : HandlerInterface
	{
		private static ILog log = LogManager.GetLogger(typeof (SaveToFileHandler));

		private String outputDirectory = null;
		private String extension = ".txt";

		/// <summary>
		/// Directory where the print job is going to be saved
		/// </summary>
		public string OutputDirectory
		{
			get { return outputDirectory; }
			set
			{
				if (Directory.Exists(value))
				{
					outputDirectory = value;
				}
				else
				{
					outputDirectory = null;
				}
			}
		}

		/// <summary>
		/// File extension
		/// </summary>
		public string Extension
		{
			get { return extension; }
			set { extension = value; }
		}

		/// <summary>
		/// Saves the print job data into a file
		/// </summary>
		/// <param name="printJob"></param>
		/// <returns></returns>
		public bool process(PrintJob printJob)
		{
			bool result = false;
			if (null != printJob
			    && null != printJob.ControlFile
			    && null != printJob.DataFile)
			{
				String fileName = null;

				if (outputDirectory == null)
				{
					fileName = printJob.Name + printJob.ControlFile.JobNumber + ".txt";
				}
				else
				{
					fileName = outputDirectory + printJob.ControlFile.JobNumber + extension;
				}

				try
				{
					FileUtil.writeFile(printJob.DataFile.Contents, fileName);
					result = true;
				}
				catch (IOException e)
				{
					log.Error(e.Message, e);
				}
			}
			else
			{
				log.Error("The printJob or printJob.getControlFile() or printJob.getDataFile() were empty");
			}
			return result;
		}
	}
}