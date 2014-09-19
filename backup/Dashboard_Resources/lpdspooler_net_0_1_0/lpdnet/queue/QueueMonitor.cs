using System;
using System.Threading;
using log4net;
using sf.net.lpdnet.common;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.handler;
using sf.net.lpdnet.thread;

namespace sf.net.lpdnet.queue
{
	/// <summary>
	/// Monitors a single print queue and process all queued jobs using the queue job handler.
	/// A new QueueMonitor should be defined for each <see cref="PrintQueue"/>. 
	/// </summary>
	public class QueueMonitor : Runnable
	{
		private static ILog log = LogManager.GetLogger(typeof (QueueMonitor));

		private bool running;
		private String queueName;
		private Lock queueLock;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="queue">Print queue</param>
		public QueueMonitor(PrintQueue queue)
		{
			running = true;
			queueName = queue.Name;
			queueLock = queue.LockPad;
		}

		/// <summary>
		/// Starts monitoring the queue
		/// </summary>
		public void run()
		{
			log.Info("Started.");
			QueuedPrintJob currentJob = null;
			try
			{
				while (running)
				{
					lock (queueLock)
					{
						// while queue is empty and monitor is running
						while (Queues.getInstance().queueIsEmpty(queueName) && running)
						{
							try
							{
								log.Debug("Nothing to process going to sleep.");
								//waits unitl someone calls a Monitor.PulseAll(queueLock) somewhere
								Monitor.Wait(queueLock);
								log.Debug("Somebody woke me up, cheking Queue: " + queueName);
							}
							catch
							{
								//do nothing...
							}
						}

						//Check if QueueMonitor.stop() was called
						if (!running)
						{
							break;
						}

						// if queue has printJob(s) then process them
						currentJob = Queues.getInstance().getNextPrintJob(queueName);
					} // end lock

					// now process job outside of the lock loop
					log.Info("About to processing print job: " + currentJob.ToString());
					HandlerInterface handler = Queues.getInstance().getQueue(queueName).Handler;

					//Process job using queue job handler
					if (handler.process(currentJob.PrintJob))
					{
						log.Info("Finished processing print job: " + currentJob.Id);
						// remember to delete if nothing goes wrong.
						String userName = currentJob.PrintJob.Owner;
						String jobId = Convert.ToString(currentJob.Id);
						Queues.getInstance().removePrintJob(queueName, userName, jobId);
					}
					else
					{
						log.Error("Error trying to process: " + currentJob.Id);
					}
				}
				log.Info("Stopped.");
			}
			catch (QueueException e)
			{
				log.Error(e.Message);
				log.Fatal("The Error above killed the QueueMonitor for:" + queueName);
			}
		}

		/// <summary>
		/// Stop monitoring the queue
		/// </summary>
		public void stop()
		{
			lock (queueLock)
			{
				running = false;
				Monitor.PulseAll(queueLock);
				log.Debug("Stopping.");
			}
		}

		/// <summary>
		/// Indicates if the queue monitor is running or not
		/// </summary>
		/// <returns></returns>
		public bool isRunning()
		{
			return running;
		}
	}
}