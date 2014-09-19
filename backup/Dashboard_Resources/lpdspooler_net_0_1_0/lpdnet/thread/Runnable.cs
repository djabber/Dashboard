namespace sf.net.lpdnet.thread
{
	/// <summary>
	/// The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread. 
	/// </summary>
	public interface Runnable
	{
		/// <summary>
		/// Starts the thread
		/// </summary>
		void run();

		/// <summary>
		/// Stops the thread
		/// </summary>
		void stop();

		/// <summary>
		/// Indicates if the thread is running or not
		/// </summary>
		/// <returns></returns>
		bool isRunning();
	}
}