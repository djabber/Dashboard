using System;

namespace sf.net.lpdnet.queue
{
	/// <summary>
	/// Encapsulates any Object that is stored in a Queue
	/// </summary>
	public class QueuedObject
	{
		private long id;
		private DateTime timeStamp;
		private Object obj;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="id">Unique identifier for this queued object.</param>
		/// <param name="timeStamp">Timestamp of when the queued object was queued.</param>
		/// <param name="obj">Object that is queued.</param>
		public QueuedObject(long id, DateTime timeStamp, object obj)
		{
			this.id = id;
			this.timeStamp = timeStamp;
			this.obj = obj;
		}

		/// <summary>
		/// Object id
		/// </summary>
		public long Id
		{
			get { return id; }
		}

		/// <summary>
		/// Object date/time
		/// </summary>
		public DateTime TimeStamp
		{
			get { return timeStamp; }
		}

		/// <summary>
		/// Object
		/// </summary>
		public object Obj
		{
			get { return obj; }
		}

		/// <summary>
		/// 
		/// </summary>
		/// <returns></returns>
		public override int GetHashCode()
		{
			return base.GetHashCode();
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="obj"></param>
		/// <returns></returns>
		public override bool Equals(object obj)
		{
			bool rval = false;

			if (obj is QueuedObject)
			{
				QueuedObject tmp = (QueuedObject) obj;
				rval = (tmp.id == id);
			}
			return rval;
		}
	}
}