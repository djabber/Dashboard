using System;
using System.Configuration;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// Provides methods to read from the configuration file in a safe way.
	/// </summary>
	public class ConfigUtil
	{
		private AppSettingsReader settings = null;

		/// <summary>
		/// Default constructor
		/// </summary>
		public ConfigUtil()
		{
			settings = new AppSettingsReader();
		}

		/// <summary>
		/// Gets the value of a key from the configuration file as string
		/// </summary>
		/// <param name="key">Key to read</param>
		/// <returns>Key value as string</returns>
		public String getString(String key)
		{
			Object rval = getValue(key, typeof (String));

			try
			{
				return Convert.ToString(rval);
			}
			catch
			{
				return null;
			}
		}

		/// <summary>
		/// Gets the value of the key from the configuration file as integer
		/// </summary>
		/// <param name="key">Key to read</param>
		/// <returns>Key value as integer</returns>
		public int getInteger(String key)
		{
			Object rval = getValue(key, typeof (String));

			try
			{
				return Convert.ToInt32(rval);
			}
			catch
			{
				return Int32.MinValue;
			}
		}

		/// <summary>
		/// Gets the value of the key from the configuration file as boolean
		/// </summary>
		/// <param name="key">Key to read</param>
		/// <returns>Key value as boolean</returns>
		public bool getBoolean(String key)
		{
			Object rval = getValue(key, typeof (bool));

			try
			{
				return Convert.ToBoolean(rval);
			}
			catch
			{
				return false;
			}
		}

		private Object getValue(String key, Type type)
		{
			Object rval = null;

			if (null != key)
			{
				try
				{
					rval = settings.GetValue(key, type);
				}
				catch
				{
					//do nothing;
				}
			}

			return rval;
		}
	}
}