using System;
using log4net;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.handler
{
	/// <summary>
	/// Creates the concrete implementation of the Handler we
	/// will use to process PrintJobs based on the
	/// properties file.  The default if there are problems is FILE.
	/// </summary>
	public class HandlerFactory
	{
		private static ILog log = LogManager.GetLogger(typeof (HandlerFactory));
		private static HandlerFactory INSTANCE = new HandlerFactory();
		private HandlerInterface handler = null;
		private String defaultHandler;

		private HandlerFactory()
		{
			ConfigUtil config = new ConfigUtil();
			defaultHandler = config.getString("lpd.queues.defaultHandle");
		}

		/// <summary>
		/// This class is a singleton.
		/// </summary>
		/// <returns>The only instance of HandlerFactory</returns>
		public static HandlerFactory getInstance()
		{
			return INSTANCE;
		}

		/// <summary>
		/// Returns the concrete implementation of the HandlerInterface based
		/// on the settings in the properties file.
		/// </summary>
		/// <returns>HandlerInterface the concrete implementation of the HandlerInterface</returns>
		public HandlerInterface getPrintHandler()
		{
			return handler;
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="handlerClassName"></param>
		/// <returns></returns>
		public HandlerInterface getPrintHandler(String handlerClassName)
		{
			if (StringUtil.isEmpty(handlerClassName))
			{
				handlerClassName = defaultHandler;
				log.Warn("Invalid handler, using the default handler: " + handlerClassName);
			}

			try
			{
				handler = Activator.CreateInstance(Type.GetType(handlerClassName)) as HandlerInterface;
			}
			catch
			{
				handlerClassName = defaultHandler;
				handler = new SaveToFileHandler();
				log.Warn("Invalid handler, using the default handler: " + handlerClassName);
			}

			return handler;
		}
	}
}