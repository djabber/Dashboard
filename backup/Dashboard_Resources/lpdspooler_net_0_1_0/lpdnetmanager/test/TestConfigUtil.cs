using System;
using sf.net.lpdnet.utils;
using NUnit.Framework;

namespace sf.net.lpdnet.test
{
	/// <summary>
	/// 
	/// </summary>
	public class TestConfigUtil : TestBase
	{
		private ConfigUtil config = new ConfigUtil();

		/// <summary>
		/// 
		/// </summary>
		[Test]
		public void readInteger()
		{
			int i = config.getInteger("i");
			Assert.AreEqual(i, 1);
		}

		/// <summary>
		/// 
		/// </summary>
		[Test]
		public void readBoolean()
		{
			bool b = config.getBoolean("b");
			Assert.AreEqual(b, true);
		}

		/// <summary>
		/// 
		/// </summary>
		[Test]
		public void readString()
		{
			String s = config.getString("root");
			Assert.AreEqual(s, "root");
		}
	}
}