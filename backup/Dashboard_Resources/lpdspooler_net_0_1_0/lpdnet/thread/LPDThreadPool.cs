using System;
using System.Collections;
using System.Threading;
using log4net;

namespace sf.net.lpdnet.thread
{
	/// <summary>
	/// Pool of threads. This class is experimental.
	/// </summary>
	public class LPDThreadPool
	{
		private static ILog log = LogManager.GetLogger(typeof (LPDThreadPool));

		private IList workers;
		private LPDThreadPoolThread[] threads;
		private bool terminated = false;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="n">Number of threads to initialize</param>
		public LPDThreadPool(int n)
		{
			workers = new ArrayList();
			threads = new LPDThreadPoolThread[n];

			for (int i = 0; i < n; i++)
			{
				threads[i] = new LPDThreadPoolThread(this, i);
				threads[i].start();
			}
		}

		/// <summary>
		/// Adds a new worker to the thread pool
		/// </summary>
		/// <param name="target"></param>
		public void add(Runnable target)
		{
			if (terminated)
			{
				throw new SystemException("Thread pool has shutdown");
			}
			lock (workers)
			{
				workers.Add((new ThreadPoolRequest(target)));
				Monitor.Pulse(workers);
			}
		}

		private void waitForAll(bool terminate)
		{
			if (!terminate)
			{
				for (int i = 0; i < threads.Length; i++)
				{
					log.Debug("Signaling Thread " + i + " to stop");
					threads[i].EventsStillFiring = false;
				}
				terminated = true;
			}
		}

		/// <summary>
		/// Closes the pool
		/// </summary>
		public void close()
		{
			waitForAll(false);
			lock (workers)
			{
				Monitor.PulseAll(workers);
			}
		}

		/// <summary>
		/// Gets the queue size
		/// </summary>
		public int Size
		{
			get { return workers.Count; }
		}

		/// <summary>
		/// 
		/// </summary>
		protected internal IList Workers
		{
			get { return workers; }
		}
	}
}