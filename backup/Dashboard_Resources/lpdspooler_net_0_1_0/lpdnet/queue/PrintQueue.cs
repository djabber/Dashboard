using System;
using System.Collections;
using System.Threading;
using log4net;
using sf.net.lpdnet.common;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.handler;

namespace sf.net.lpdnet.queue
{
	/// <summary>
	/// The PrintQueue is just a queue that only accepts print jobs.
	/// </summary>
	public class PrintQueue
	{
		private static ILog log = LogManager.GetLogger(typeof (PrintQueue));
		private Lock lockPad;
		private Queue queue;
		private HandlerInterface handler;

		/// <summary>
		/// Default constructor.
		/// </summary>
		/// <param name="name">Name of the print queue</param>
		/// <param name="handler">Queue handler class name</param>
		public PrintQueue(String name, HandlerInterface handler)
		{
			queue = new Queue(name);
			lockPad = new Lock(name);
			this.handler = handler;
		}

		/// <summary>
		/// Queeu job lock.
		/// </summary>
		public Lock LockPad
		{
			get { return lockPad; }
		}

		/// <summary>
		/// Queue name
		/// </summary>
		public string Name
		{
			get { return queue.Name; }
		}

		/// <summary>
		/// Queue job handler
		/// </summary>
		public HandlerInterface Handler
		{
			get { return handler; }
		}

		/// <summary>
		/// Add print job to queue.
		/// </summary>
		/// <param name="printJob">The print job</param>
		/// <returns>The unique identifier of the print job in the queue.</returns>
		public long add(PrintJob printJob)
		{
			return queue.add(printJob);
		}

		/// <summary>
		/// Remove print job from queue.
		/// </summary>
		/// <param name="id"></param>
		/// <returns>PrintJob from the queue or null if it was not found.</returns>
		public void remove(long id)
		{
			try
			{
				queue.remove(id);
			}
			catch (ObjectNotFoundException objectNotFoundException)
			{
				log.Error("print job for id[" + id + "] was not found in the queue[" + queue.Name + "]");
				throw objectNotFoundException;
			}
		}

		/// <summary>
		/// Gets the next PrintJob without removing it.
		/// </summary>
		/// <returns>The next printJob in the queue or null if theres an Error.</returns>
		public QueuedPrintJob getNextPrintJob()
		{
			QueuedPrintJob rval = null;
			try
			{
				QueuedObject queuedObject = queue.getNext();
				long queueId = queuedObject.Id;
				PrintJob printJob = (PrintJob) queuedObject.Obj;
				rval = new QueuedPrintJob(queueId, printJob);
			}
			catch (ObjectNotFoundException e)
			{
				throw e;
			}
			return rval;
		}

		/// <summary>
		/// Remove all print jobs from queue.
		/// </summary>
		/// <returns>Number of jobs removed, or -1 if this failed</returns>
		public int removeAll()
		{
			int rval = queue.removeAll();
			return rval;
		}

		/// <summary>
		/// Size of the print queue.
		/// </summary>
		/// <returns>Size of the print queue.</returns>
		public int size()
		{
			return queue.size();
		}

		/// <summary>
		/// List items in queue.
		/// </summary>
		/// <returns>List of queue items</returns>
		public IList list()
		{
			IList rval = null;
			IList list = new ArrayList();
			IList qlist = queue.list(); // get the items from the queue

			// iterate over them
			for (IEnumerator en = qlist.GetEnumerator(); en.MoveNext();)
			{
				QueuedObject queuedObject = (QueuedObject) en.Current;
				// create queued print job info object
				QueuedPrintJobInfo queuedPrintJobInfo = new QueuedPrintJobInfo(queuedObject);
				list.Add(queuedPrintJobInfo);
			}

			rval = list;
			return rval;
		}

		/// <summary>
		/// Close the queue
		/// </summary>
		public void closeQueue()
		{
			Monitor.PulseAll(this);
		}
	}
}