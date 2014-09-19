using System;
using System.Threading;
using log4net;

namespace sf.net.lpdnet.thread
{
	/// <summary>
	/// Pool thread
	/// </summary>
	public class LPDThreadPoolThread : LPDThread
	{
		private static ILog log = LogManager.GetLogger(typeof (LPDThreadPoolThread));

		private bool eventsStillFiring = true;
		private bool running = true;
		private LPDThreadPool pool;
		private int id;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="pool">Reference to the thread pool</param>
		/// <param name="i">thread id</param>
		public LPDThreadPoolThread(LPDThreadPool pool, int i)
		{
			this.pool = pool;
			id = i;
		}

		/// <summary>
		/// Waits for a worker object to be queued and executes it
		/// </summary>
		public override void run()
		{
			int poolWorkersCount = 0;
			ThreadPoolRequest poolRequest = null;
			while (running)
			{
				try
				{
					lock (pool.Workers) //lock workers
					{
						//if workers available remove it from queue and and excecutes it
						poolWorkersCount = pool.Workers.Count;
						if (poolWorkersCount > 0)
						{
							poolRequest = (ThreadPoolRequest) pool.Workers[0];
							pool.Workers.RemoveAt(0);
						}
						else
						{
							try
							{
								//if pool is runing or workers are available
								if (eventsStillFiring || (poolWorkersCount > 0))
								{
									log.Debug("Thread " + id + " going to sleep...");
									//if pool.workers is empty wait until it has something in it
									Monitor.Wait(pool.Workers);
									log.Debug("Thread " + id + " is awake...");
								}
							}
							catch (Exception e)
							{
								log.Warn(e.Message, e);
							}
						}
					}
				}
				catch (Exception e)
				{
					log.Error("ThreadPool contained an object other than ThreadPoolRequest.");
					log.Error(e.Message, e);
					poolRequest = null;
				}

				if (poolRequest != null)
				{
					log.Debug("Thread " + id + " start processing");
					poolRequest.Target.run();
					log.Debug("Thread " + id + " finish processing");
				}

				if (!eventsStillFiring && (poolWorkersCount == 0))
				{
					log.Debug("Thread " + id + " stopped");
					running = false;
					return;
				}
				poolRequest = null;
			}
		}

		/// <summary>
		/// 
		/// </summary>
		public override void stop()
		{
		}

		/// <summary>
		/// 
		/// </summary>
		/// <returns></returns>
		public override bool isRunning()
		{
			return false;
		}

		/// <summary>
		/// 
		/// </summary>
		public bool EventsStillFiring
		{
			set { eventsStillFiring = value; }
		}
	}
}