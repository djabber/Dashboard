using System;

namespace sf.net.lpdnet.common
{
	/// <summary>
	/// The abstract super-class that represents the 2 files sent to us from the CommandReceiveJob.
	/// </summary>
	public abstract class PrintFile
	{
		private String jobNumber;
		private String hostName;
		private String size;
		private byte[] contents;

		/// <summary>
		/// Job number
		/// </summary>
		public string JobNumber
		{
			get { return jobNumber; }
			set { jobNumber = value; }
		}

		/// <summary>
		/// Host name
		/// </summary>
		public string HostName
		{
			get { return hostName; }
			set { hostName = value; }
		}

		/// <summary>
		/// Print file size
		/// </summary>
		public string Size
		{
			get { return size; }
			set { size = value; }
		}

		/// <summary>
		/// Print file content
		/// </summary>
		public byte[] Contents
		{
			get { return contents; }
			set { contents = value; }
		}
	}
}