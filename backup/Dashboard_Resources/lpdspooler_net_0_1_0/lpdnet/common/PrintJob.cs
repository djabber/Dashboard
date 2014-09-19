using System;
using sf.net.lpdnet.exception;

namespace sf.net.lpdnet.common
{
	/// <summary>
	/// Print job
	/// </summary>
	public class PrintJob
	{
		/// <summary>
		/// Job id default length
		/// </summary>
		public static int JOB_ID_LENGTH = 8;

		/// <summary>
		/// Job name default length
		/// </summary>
		public static int JOB_NAME_LENGTH = 20;

		/// <summary>
		/// Job owner default length
		/// </summary>
		public static int JOB_OWNER_LENGTH = 15;

		/// <summary>
		/// Job date default length
		/// </summary>
		public static int JOB_DATE_LENGTH = 20;

		/// <summary>
		/// Job size default length
		/// </summary>
		public static int JOB_SIZE_LENGTH = 9;

		private ControlFile controlFile;
		private DataFile dataFile;

		/// <summary>
		/// Constructor requires a ControlFile and a DataFile.
		/// </summary>
		/// <param name="controlFile"></param>
		/// <param name="dataFile"></param>
		/// <exception cref="LPDException"></exception>
		public PrintJob(ControlFile controlFile, DataFile dataFile)
		{
			this.controlFile = controlFile;
			this.dataFile = dataFile;
		}

		/// <summary>
		/// Job name
		/// </summary>
		public String Name
		{
			get { return controlFile.ControlFileCommands.JobName; }
		}

		/// <summary>
		/// Job size
		/// </summary>
		public int Size
		{
			get { return dataFile.Contents.Length; }
		}

		/// <summary>
		/// Job owner
		/// </summary>
		public String Owner
		{
			get { return controlFile.ControlFileCommands.UserId; }
		}

		/// <summary>
		/// job control file
		/// </summary>
		public ControlFile ControlFile
		{
			get { return controlFile; }
		}

		/// <summary>
		/// job data file
		/// </summary>
		public DataFile DataFile
		{
			get { return dataFile; }
		}
	}
}