using sf.net.lpdnet.common;

namespace sf.net.lpdnet.queue
{
	/// <summary>
	/// A copy of a printJob plus the jobId used by the PrintQueue to store the opriginal copy of this PrintJob.
	/// </summary>
	public class QueuedPrintJob
	{
		private long id;
		private PrintJob printJob;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="id">Id the PrintQueue id of the PrintJob</param>
		/// <param name="printJob">A copy of the original PrintJob stored in the PrintQueue</param>
		public QueuedPrintJob(long id, PrintJob printJob)
		{
			this.id = id;
			this.printJob = printJob;
		}

		/// <summary>
		/// Job id
		/// </summary>
		public long Id
		{
			get { return id; }
		}

		/// <summary>
		/// Print job
		/// </summary>
		public PrintJob PrintJob
		{
			get { return printJob; }
		}
	}
}