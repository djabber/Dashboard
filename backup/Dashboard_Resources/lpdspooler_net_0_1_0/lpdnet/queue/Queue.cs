using System;
using System.Collections;
using log4net;
using sf.net.lpdnet.exception;

namespace sf.net.lpdnet.queue
{
	/// <summary>
	/// Queue of objects
	/// </summary>
	public class Queue
	{
		private static ILog log = LogManager.GetLogger(typeof (Queue));
		private static long counter = 0;

		private IList queue = new ArrayList();
		private String name;

		/// <summary>
		/// Invalid queue id
		/// </summary>
		public static readonly long INVALID_ID = -1;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="name"></param>
		public Queue(String name)
		{
			this.name = name;
		}

		/// <summary>
		/// Queue name
		/// </summary>
		public string Name
		{
			get { return name; }
			set { name = value; }
		}

		/// <summary>
		/// Add method.
		/// </summary>
		/// <param name="obj">Object to add</param>
		/// <returns>Unique identifier of the queued object.</returns>
		public long add(Object obj)
		{
			long rval = INVALID_ID;
			long id = counter++;
			DateTime timeStamp = DateTime.Now;

			int result = queue.Add(new QueuedObject(id, timeStamp, obj));

			if (result > -1)
			{
				rval = id;
			}

			log.Debug("Object added: " + rval);
			return rval;
		}

		/// <summary>
		/// Remove by unique identifier.
		/// </summary>
		/// <param name="id">Unique identifier of the queued object.</param>
		/// <returns>Object matching the identifier</returns>
		/// <exception cref="ObjectNotFoundException">If the object is not found in the queue</exception>
		public void remove(long id)
		{
			lock (queue)
			{
				bool found = false;

				for (int i = 0; i < queue.Count; i++)
				{
					QueuedObject queuedObject = (QueuedObject) queue[i];
					long queuedObjectId = queuedObject.Id;
					if (queuedObjectId == id)
					{
						// the id's match, so remove this object from the queue and
						// return the object to the caller
						queue.RemoveAt(i);
						queuedObject = null;
						found = true;
						break;
					}
				}

				if (found == false)
				{
					throw new ObjectNotFoundException("the object associated with id[" + id + "] was not found.");
				}
			}

			log.Debug("Object removed: " + id);
		}

		/// <summary>
		/// Size of the queue.
		/// </summary>
		/// <returns>Size of the queue.</returns>
		public int size()
		{
			return queue.Count;
		}

		/// <summary>
		/// Remove all items from queue
		/// </summary>
		/// <returns></returns>
		public int removeAll()
		{
			int rval = -1;
			rval = queue.Count;

			lock (queue)
			{
				queue.Clear();
			}

			return rval;
		}

		/// <summary>
		/// List of the items in the queue.
		/// </summary>
		/// <returns>List of queue items</returns>
		public IList list()
		{
			IList rval = null;
			IList list = new ArrayList();

			for (int i = 0; i < queue.Count; i++)
			{
				QueuedObject queuedObject = (QueuedObject) queue[i];
				list.Add(queuedObject); // would prefer to clone here
			}

			rval = list;
			return rval;
		}

		/// <summary>
		/// Gets the last Object in the Queue without removing it
		/// </summary>
		/// <returns>The last QueuedObject in the queue</returns>
		public QueuedObject getNext()
		{
			// declare the return value
			QueuedObject rval = null;

			// get the next printjob
			lock (queue)
			{
				if (queue.Count > 0)
				{
					rval = (QueuedObject) queue[queue.Count - 1];
				}
				else
				{
					log.Error("The queue is empty.");
					throw new ObjectNotFoundException("The queue is empty.");
				}
			}

			log.Debug("Next object in queue: " + rval.Id);
			return rval;
		}
	}
}