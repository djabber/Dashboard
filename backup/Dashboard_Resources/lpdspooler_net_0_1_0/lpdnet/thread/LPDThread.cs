using System.Threading;

namespace sf.net.lpdnet.thread
{
	/// <summary>
	/// Wrapper class for creating and manipulating threads.
	/// </summary>
	public class LPDThread : Runnable
	{
		private Runnable runnable;
		private ThreadStart threadStart = null;
		private Thread thread = null;

		/// <summary>
		/// Constructor
		/// </summary>
		/// <param name="runnable">Runnable object</param>
		public LPDThread(Runnable runnable)
		{
			this.runnable = runnable;
		}

		/// <summary>
		/// Constructor
		/// </summary>
		public LPDThread()
		{
			runnable = this;
		}

		/// <summary>
		/// Starts the thread
		/// </summary>
		public void start()
		{
			threadStart = new ThreadStart(runnable.run);
			thread = new Thread(threadStart);
			thread.Start();
		}

		/// <summary>
		/// Excecutes the runnable class
		/// </summary>
		public virtual void run()
		{
			if (!runnable.Equals(this))
			{
				runnable.run();
			}
		}

		/// <summary>
		/// Stops the thread
		/// </summary>
		public virtual void stop()
		{
			if (!runnable.Equals(this))
			{
				runnable.stop();
			}
		}

		/// <summary>
		/// Indicates if the thread is running
		/// </summary>
		/// <returns></returns>
		public virtual bool isRunning()
		{
			return runnable.isRunning();
		}

		/// <summary>
		/// Returns the inner thread
		/// </summary>
		public Thread innerThread
		{
			get { return thread; }
		}
	}
}