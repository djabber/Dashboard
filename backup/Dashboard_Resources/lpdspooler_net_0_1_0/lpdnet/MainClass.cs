using System;
using System.Collections;
using System.ServiceProcess;
using log4net;
using sf.net.lpdnet.handler;
using sf.net.lpdnet.queue;
using sf.net.lpdnet.thread;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet
{
	/// <summary>
	/// When sf.net.lpdnet is compiled as an executable(exe) this class will be the main application entry point.
	/// It will start a new lpd deamon, creates and monitors a new queue instance called "TEXT".
	/// The created queue will use SaveToFile handler to handle all its jobs saving each job as a 
	/// plaint text file on the same directory where the application is running.
	/// </summary>
	public class MainClass : ServiceBase
	{
		private static ILog log = LogManager.GetLogger(typeof (LPD));

		private IList threads = new ArrayList();

		/// <summary>
		/// Default constructor
		/// </summary>
		public MainClass()
		{
			CanShutdown = true;
			ServiceName = "lpdnet";

			try
			{
				ConfigUtil config = new ConfigUtil();
				Queues queues = Queues.getInstance();

				// create the SaveToFile PrintQueue
				SaveToFileHandler saveToFileHandler = new SaveToFileHandler();
				saveToFileHandler.OutputDirectory = config.getString("lpd.queue.file.outputDirectory");
				saveToFileHandler.Extension = config.getString("lpd.queue.file.extension");
				PrintQueue saveToFileQueue = queues.createQueue("FILE", saveToFileHandler);
				QueueMonitor fileQueueMonitor = new QueueMonitor(saveToFileQueue);

				// create the PrintRedirect PrintQueue
				PrintRedirectHandler printRedirectHandler = new PrintRedirectHandler();
				printRedirectHandler.setRedirectionPrinter(config.getString("lpd.queue.raw.printer"));
				PrintQueue printRedirectQueue = queues.createQueue("RAW", printRedirectHandler);
				QueueMonitor redirectQueueMonitor = new QueueMonitor(printRedirectQueue);

				LPDThread lpdThread = new LPDThread(LPD.getInstance());
				LPDThread fileMonitorThread = new LPDThread(fileQueueMonitor);
				LPDThread redirectMonitorThread = new LPDThread(redirectQueueMonitor);

				threads.Add(lpdThread);
				threads.Add(fileMonitorThread);
				threads.Add(redirectMonitorThread);
			}
			catch (Exception e)
			{
				log.Fatal(e.Message, e);
			}
		}

		private static void Main()
		{
			Run(new MainClass());
		}

		/// <summary>
		/// Excecutes when the service receives a shutdown message
		/// </summary>
		protected override void OnShutdown()
		{
			OnStop();
		}

		/// <summary>
		/// Excecutes when the service receives a start message
		/// </summary>
		/// <param name="args"></param>
		protected override void OnStart(string[] args)
		{
			for (int i = 0; i < threads.Count; i++)
			{
				LPDThread thread = threads[i] as LPDThread;
				thread.start();
			}
		}

		/// <summary>
		/// Excecutes when the service receives a stop message
		/// </summary>
		protected override void OnStop()
		{
			for (int i = 0; i < threads.Count; i++)
			{
				LPDThread thread = threads[i] as LPDThread;
				thread.stop();
			}
		}
	}
}