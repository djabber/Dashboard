using System;

namespace sf.net.lpdnet.common
{
	/// <summary>
	/// Used to lock queues between threads
	/// </summary>
	public class Lock
	{
		private String lockName;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="lockName"></param>
		public Lock(String lockName)
		{
			this.lockName = lockName;
		}

		/// <summary>
		/// Lock name
		/// </summary>
		public string LockName
		{
			get { return lockName; }
			set { lockName = value; }
		}
	}
}