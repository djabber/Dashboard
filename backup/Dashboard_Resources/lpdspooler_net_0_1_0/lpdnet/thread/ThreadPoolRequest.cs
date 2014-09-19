namespace sf.net.lpdnet.thread
{
	/// <summary>
	/// Class for holding a thread request
	/// </summary>
	public class ThreadPoolRequest
	{
		private Runnable target;

		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="target">Thread object</param>
		public ThreadPoolRequest(Runnable target)
		{
			this.target = target;
		}

		/// <summary>
		/// Thread object
		/// </summary>
		public Runnable Target
		{
			get { return target; }
		}
	}
}