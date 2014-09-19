using System;
using sf.net.lpdnet.queue;
using NUnit.Framework;

namespace sf.net.lpdnet.test
{
	/// <summary>
	/// 
	/// </summary>
	public class TestQueue : TestBase
	{
		/// <summary>
		/// 
		/// </summary>
		[Test]
		public void testQueue()
		{
			// 0. create queue
			Queue testQueue = new Queue("test");
			// 1. create some objects
			String i0 = "0";
			String i1 = "1";
			String i2 = "2";

			// 2. add to the queue
			long id0 = testQueue.add(i0);
			long id1 = testQueue.add(i1);
			long id2 = testQueue.add(i2);

			// 3. remove objects
			testQueue.remove(id0);
			testQueue.remove(id1);
			testQueue.remove(id2);

//         Assert.AreEqual(o0, i0);
//         Assert.AreEqual(o1, i1);
//         Assert.AreEqual(o2, i2);
		}
	}
}