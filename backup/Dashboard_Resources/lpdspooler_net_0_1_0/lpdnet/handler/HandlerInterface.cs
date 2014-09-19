using sf.net.lpdnet.common;

namespace sf.net.lpdnet.handler
{
	/// <summary>
	/// Defines the interface for any class looking to process
	/// printJobs that are in the PrintQueue.  The QueueMonitor
	/// passes work to this Interface.
	/// </summary>
	public interface HandlerInterface
	{
		/// <summary>
		/// Processes the PrintJob in some manner.
		/// </summary>
		/// <param name="printJob">The PrintJob to process</param>
		/// <returns>The result of the processing</returns>
		bool process(PrintJob printJob);
	}
}