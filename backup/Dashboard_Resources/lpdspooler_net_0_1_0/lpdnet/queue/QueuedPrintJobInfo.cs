using System;
using System.Text;
using sf.net.lpdnet.common;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.queue
{
	/// <summary>
	/// Encapsulates the PrintJob that is stored in the Queue.
	/// A copy of the actual stored PrintJob is returned.  This class
	/// makes it easy to expose more attibutes of the Print Job
	/// as necessary.
	/// </summary>
	public class QueuedPrintJobInfo
	{
		private long id;
		private DateTime timeStamp;
		private int size;
		private String name;
		private String owner;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="queuedObject">A queued print job.</param>
		public QueuedPrintJobInfo(QueuedObject queuedObject)
		{
			id = queuedObject.Id;
			timeStamp = queuedObject.TimeStamp;
			PrintJob printJob = (PrintJob) queuedObject.Obj; // can throw ClassCastException
			size = printJob.Size;
			name = printJob.Name;
			owner = printJob.Owner;
		}

		/// <summary>
		/// Job Id
		/// </summary>
		public long Id
		{
			get { return id; }
		}

		/// <summary>
		/// Job date/time
		/// </summary>
		public DateTime TimeStamp
		{
			get { return timeStamp; }
		}

		/// <summary>
		/// Job data file size
		/// </summary>
		public int Size
		{
			get { return size; }
		}

		/// <summary>
		/// Job data file name
		/// </summary>
		public string Name
		{
			get { return name; }
		}

		/// <summary>
		/// Job owner
		/// </summary>
		public string Owner
		{
			get { return owner; }
		}

		/// <summary>
		/// Returns a short description of the print job
		/// </summary>
		/// <returns>Short description of the print job</returns>
		public String getShortDescription()
		{
			String jobId = id.ToString("000");

			StringBuilder sb = new StringBuilder();
			// job id
			sb.Append(StringUtil.createFixedLengthString(jobId, PrintJob.JOB_ID_LENGTH));
			// job name
			sb.Append(StringUtil.createFixedLengthString(name, PrintJob.JOB_NAME_LENGTH));
			// job owner
			sb.Append(StringUtil.createFixedLengthString(owner, PrintJob.JOB_OWNER_LENGTH));
			return sb.ToString();
		}

		/// <summary>
		/// Returns a long description of the print job
		/// </summary>
		/// <returns>Long description of the print job</returns>
		public String getLongDescription()
		{
			String jobId = id.ToString("000");
			String jobSize = size.ToString("000");

			StringBuilder sb = new StringBuilder();

			// job id
			sb.Append(StringUtil.createFixedLengthString(jobId, PrintJob.JOB_ID_LENGTH));
			// job name
			sb.Append(StringUtil.createFixedLengthString(name, PrintJob.JOB_NAME_LENGTH));
			// job owner
			sb.Append(StringUtil.createFixedLengthString(owner, PrintJob.JOB_OWNER_LENGTH));
			// print time
			sb.Append(StringUtil.createFixedLengthString(DateUtil.createDateString(timeStamp), PrintJob.JOB_DATE_LENGTH));
			// job size
			sb.Append(StringUtil.createFixedLengthString(jobSize, PrintJob.JOB_SIZE_LENGTH));
			return sb.ToString();
		}
	}
}