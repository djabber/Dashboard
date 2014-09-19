using System;
using sf.net.lpdnet.queue;

namespace sf.net.lpdnet.manager.ui
{
	/// <summary>
	/// Summary description for QueuedPrintJobModel.
	/// </summary>
	public class QueuedPrintJobModel
	{
		private String id;
		private QueuedPrintJobInfo jobInfo;
		private String queueName;
		private String status;

		/// <summary>
		/// 
		/// </summary>
		public static String STATUS_COMPLETE = "Completed";

		/// <summary>
		/// 
		/// </summary>
		public static String STATUS_IN_PROGRESS = "In progress";

		/// <summary>
		/// 
		/// </summary>
		/// <param name="jobInfo"></param>
		/// <param name="queueName"></param>
		/// <param name="jobStatus"></param>
		public QueuedPrintJobModel(QueuedPrintJobInfo jobInfo, string queueName, string jobStatus)
		{
			this.jobInfo = jobInfo;
			this.queueName = queueName;
			status = jobStatus;
			id = makeId(queueName, jobInfo.Id);
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="queue"></param>
		/// <param name="jobId"></param>
		/// <returns></returns>
		public static String makeId(String queue, long jobId)
		{
			return String.Format("{0}-{1}", queue, jobId.ToString("000"));
		}

		/// <summary>
		/// 
		/// </summary>
		public string Status
		{
			get { return status; }
			set { status = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public QueuedPrintJobInfo JobInfo
		{
			get { return jobInfo; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string QueueName
		{
			get { return queueName; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string Id
		{
			get { return id; }
		}
	}
}