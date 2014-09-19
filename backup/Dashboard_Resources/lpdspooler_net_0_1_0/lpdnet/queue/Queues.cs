using System;
using System.Collections;
using System.Threading;
using log4net;
using sf.net.lpdnet.common;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.handler;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.queue
{
	/// <summary>
	/// This class maintains all of the Print queues and contains all of the logic
	/// for performing operations on them.
	/// </summary>
	public class Queues
	{
		private static ILog log = LogManager.GetLogger(typeof (Queues));
		private static Queues INSTANCE = new Queues();
		private Hashtable queues = new Hashtable();

		private const String ROOT = "root";
		private const String ADMIN = "administrator";

		/// <summary>
		/// Delegate for adding a new print job
		/// </summary>
		public delegate void addJobDelegate(Object sender, String queue, QueuedPrintJobInfo job);

		/// <summary>
		/// Delegate for removing a job from queue
		/// </summary>
		public delegate void removeJobDelegate(Object sender, String queue, long jobId);

		/// <summary>
		/// Delegate for removing all print jobs from queue
		/// </summary>
		public delegate void removeAllJobsDelegate(Object sender, String queue);

		/// <summary>
		/// Event that is fired when a new print job is added to queue
		/// </summary>
		public event addJobDelegate addJobEvent;

		/// <summary>
		/// Event that is fired when a print job is removed from queue
		/// </summary>
		public event removeJobDelegate removeJobEvent;

		/// <summary>
		/// Event that is fired when a all print jobs are removed from queue
		/// </summary>
		public event removeAllJobsDelegate removeAllJobsEvent;

		private Queues()
		{
		}

		/// <summary>
		/// This class is a singleton.
		/// </summary>
		/// <returns>The only instance of Queues.</returns>
		public static Queues getInstance()
		{
			return INSTANCE;
		}

		/// <summary>
		/// Gets a print queue from the queues collection
		/// </summary>
		/// <param name="queueName"></param>
		/// <returns>PrintQueue</returns>
		public PrintQueue getQueue(String queueName)
		{
			if (queues.ContainsKey(queueName))
			{
				return (PrintQueue) queues[queueName];
			}
			else
			{
				throw new QueueException("The queue called(" + queueName + ") does no exists.");
			}
		}

		/// <summary>
		/// Creates a Print Queue with the name passed in
		/// </summary>
		/// <param name="queueName">The name of the print queue</param>
		/// <param name="handler"></param>
		/// <returns>New created queue</returns>
		public PrintQueue createQueue(String queueName, HandlerInterface handler)
		{
			PrintQueue queue = null;

			if (StringUtil.isEmpty(queueName))
			{
				log.Error("Empty queue name.");
				throw new QueueException("Empty queue name.");
			}
			else if (queues.ContainsKey(queueName))
			{
				log.Error("The queue called(" + queueName + ") already exists.");
				throw new QueueException("The queue called(" + queueName + ") already exists.");
			}
			else
			{
				queue = new PrintQueue(queueName, handler);
				queues.Add(queueName, queue);
				return queue;
			}
		}

		/// <summary>
		/// Adds a printJob to the queue specified by queueName.
		/// </summary>
		/// <param name="queueName">The name of the print queue</param>
		/// <param name="printJob">The printJob added to the print queue</param>
		/// <exception cref="QueueException"></exception>
		/// 
		public void addPrintJob(String queueName, PrintJob printJob)
		{
			if (StringUtil.isEmpty(queueName))
			{
				log.Error("Empty queue name.");
				throw new QueueException("Queeu name was empty.");
			}
			else if (!queues.ContainsKey(queueName))
			{
				log.Error("The queue called(" + queueName + ") does not exist.");
				throw new QueueException("The queue called(" + queueName + ") does not exist.");
			}
			else if (null == printJob)
			{
				log.Error("PrintJob passed in was null.");
				throw new QueueException("PrintJob passed in was null.");
			}
			else if (null == printJob.DataFile)
			{
				log.Error("DataFile in PrintJob passed in was null.");
				throw new QueueException("DataFile in PrintJob passed in was null.");
			}
			else if (null == printJob.DataFile.Contents)
			{
				log.Error("Contents of DataFile in PrintJob passed in was null.");
				throw new QueueException("Contents of DataFile in PrintJob passed in was null.");
			}
			else
			{
				long jobId = -1;
				PrintQueue queue = (PrintQueue) queues[queueName];
				log.Debug("Queue: " + queueName);
				Lock lockPad = queue.LockPad;
				log.Debug("Queue lock: " + queueName);
				lock (lockPad)
				{
					jobId = queue.add(printJob);
					Monitor.PulseAll(lockPad); //Notifies all waiting threads of a change in the queue state.
					onAddJob(queueName, new QueuedPrintJobInfo(new QueuedObject(jobId, DateTime.Now, printJob)));
					log.Info("PrintJob " + jobId + " was added to queue " + queueName);
					log.Debug("Notify all to Lock object for queue: " + queueName);
				}
			}
		}

		/// <summary>
		/// Removes a printJob based on queueName and the jobNumber.
		/// </summary>
		/// <param name="queueName">The name of the print queue</param>
		/// <param name="user">The user trying to delete the print job</param>
		/// <param name="jobNumber">The print job number assigned by the Queue</param>
		/// <exception cref="QueueException"></exception>
		public void removePrintJob(String queueName, String user, String jobNumber)
		{
			long jobId = 0;
			if (StringUtil.isEmpty(queueName))
			{
				log.Error("Queue name was empty.");
				throw new QueueException("Queue name was empty.");
			}
			else if (!queues.ContainsKey(queueName))
			{
				log.Error("The queue called(" + queueName + ") does not exist.");
				throw new QueueException("The queue called(" + queueName + ") does not exist.");
			}
			else if (StringUtil.isEmpty(user))
			{
				log.Error("User passed in was empty.");
				throw new QueueException("User passed in was empty.");
			}
			else if (StringUtil.isEmpty(jobNumber))
			{
				log.Error("PrintJob passed in was empty.");
				throw new QueueException("PrintJob passed in was empty.");
			}
			else
			{
				try
				{
					jobId = Convert.ToInt64(jobNumber);
				}
				catch (Exception e)
				{
					log.Warn("The jobNumber(" + jobNumber + ") passed in was not a valid: " + jobNumber);
					throw new QueueException(e.Message);
				}

				PrintQueue queue = (PrintQueue) queues[queueName];
				Lock lockPad = queue.LockPad;

				try
				{
					// if user name is root or Administrator just delete
					if (ROOT.ToUpper().Equals(user.Trim().ToUpper()) ||
					    ADMIN.ToUpper().Equals(user.Trim().ToUpper()))
					{
						lock (lockPad)
						{
							queue.remove(jobId);
							onRemoveJob(queueName, jobId);
							log.Info("PrintJob " + jobId + " was removed from the queue " + queueName);
						}
					}
					else
					{
						// if not then only delete if user and job number match
						IList jobs = queue.list();
						IEnumerator enu = jobs.GetEnumerator();

						while (enu.MoveNext())
						{
							QueuedPrintJobInfo qpji = (QueuedPrintJobInfo) enu.Current;
							if (qpji.Id == jobId)
							{
								// match jobId
								String jobOwner = qpji.Owner;
								if (null != jobOwner && jobOwner.Trim().Equals(user.Trim()))
								{
									//match username
									lock (lockPad)
									{
										queue.remove(jobId); // if username and jobId match, then remove
										onRemoveJob(queueName, jobId);
										log.Info("PrintJob " + jobId + " was removed from the queue " + queueName);
									}
								}
								// break here?
							}
						}
					}
				}
				catch (ObjectNotFoundException e)
				{
					log.Error(e.Message);
					throw new QueueException(e.Message, e);
				}
			}
		}

		/// <summary>
		/// Deletes all printJobs for this queue.
		/// </summary>
		/// <param name="queueName">The name of the print queue</param>
		/// <exception cref="QueueException"></exception>
		public void removeAllPrintJobs(String queueName)
		{
			if (StringUtil.isEmpty(queueName))
			{
				throw new QueueException("Queue name was empty.");
			}
			else if (!queues.ContainsKey(queueName))
			{
				throw new QueueException("The queue called(" + queueName + ") does not exist.");
			}
			else
			{
				PrintQueue queue = (PrintQueue) queues[queueName];
				Lock lockPad = queue.LockPad;
				lock (lockPad)
				{
					queue.removeAll();
					onRemoveJobsAll(queueName);
					log.Info("Queue " + queueName + " is empty now.");
				}
			}
		}

		/// <summary>
		/// Returns a IList of all print jobs in the Queue.
		/// </summary>
		/// <param name="queueName">The name of the queue we are wanting to list</param>
		/// <returns>IList of all print jobs in the Queue</returns>
		/// <exception cref="QueueException">If we have problems getting the queue list</exception>
		public IList listAllPrintJobs(String queueName)
		{
			IList result = null;
			if (StringUtil.isEmpty(queueName))
			{
				throw new QueueException("Queue name was empty.");
			}
			else if (!queues.ContainsKey(queueName))
			{
				throw new QueueException("The queue called(" + queueName + ") does not exist.");
			}
			else
			{
				PrintQueue queue = (PrintQueue) queues[queueName];
				Lock lockPad = queue.LockPad;
				lock (lockPad)
				{
					result = queue.list();
				}
			}
			return result;
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="queueName"></param>
		/// <returns></returns>
		public int getQueueSize(String queueName)
		{
			int result = -1;
			if (StringUtil.isEmpty(queueName))
			{
				throw new QueueException("Queue name was empty.");
			}
			else if (!queues.ContainsKey(queueName))
			{
				throw new QueueException("The queue called(" + queueName + ") does not exist.");
			}
			else
			{
				PrintQueue queue = (PrintQueue) queues[queueName];
				Lock lockPad = queue.LockPad;
				lock (lockPad)
				{
					result = queue.size();
				}
			}
			return result;
		}

		/// <summary>
		/// Returns true if the queue is empty
		/// </summary>
		/// <param name="queueName">Queue name</param>
		/// <returns>True if empty, otherwise false</returns>
		public bool queueIsEmpty(String queueName)
		{
			int i = getQueueSize(queueName);
			return i == 0 ? true : false;
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="queueName"></param>
		/// <returns></returns>
		public Lock getQueueLock(String queueName)
		{
			Lock result = null;
			if (StringUtil.isEmpty(queueName))
			{
				throw new QueueException("Queue name was empty.");
			}
			else if (!queues.ContainsKey(queueName))
			{
				throw new QueueException("the queue called(" + queueName + ") does not exist.");
			}
			else
			{
				PrintQueue queue = (PrintQueue) queues[queueName];
				result = queue.LockPad;
			}
			return result;
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="queueName"></param>
		/// <returns></returns>
		public QueuedPrintJob getNextPrintJob(String queueName)
		{
			QueuedPrintJob result = null;
			if (StringUtil.isEmpty(queueName))
			{
				throw new QueueException("Queue name was empty.");
			}
			else if (!queues.ContainsKey(queueName))
			{
				throw new QueueException("The queue called(" + queueName + ") does not exist.");
			}
			else
			{
				PrintQueue queue = (PrintQueue) queues[queueName];
				Lock lockPad = queue.LockPad;

				try
				{
					lock (lockPad)
					{
						result = queue.getNextPrintJob();
					}
				}
				catch (ObjectNotFoundException e)
				{
					throw new QueueException(e.Message, e);
				}
			}
			return result;
		}

		/// <summary>
		/// Fires an event when a new job is 
		/// </summary>
		/// <param name="job"></param>
		/// <param name="queue"></param>
		protected virtual void onAddJob(String queue, QueuedPrintJobInfo job)
		{
			if (addJobEvent != null)
			{
				log.Debug("Invoking OnAddJob event delegates: " + queue + ", " + job.Id);
				addJobEvent(this, queue, job);
			}
		}

		/// <summary>
		/// Fires an event when a job is removed from queue 
		/// </summary>
		/// <param name="queue"></param>
		/// <param name="jobId"></param>
		protected virtual void onRemoveJob(String queue, long jobId)
		{
			if (removeJobEvent != null)
			{
				log.Debug("Invoking OnRemoveJob event delegates: " + queue + ", " + jobId.ToString());
				removeJobEvent(this, queue, jobId);
			}
		}

		/// <summary>
		/// Fires an event when all jobs are removed from queue 
		/// </summary>
		/// <param name="queue"></param>
		protected virtual void onRemoveJobsAll(String queue)
		{
			if (removeAllJobsEvent != null)
			{
				log.Debug("Invoking OnRemoveAllJobs event delegates");
				removeAllJobsEvent(this, queue);
			}
		}
	}
}