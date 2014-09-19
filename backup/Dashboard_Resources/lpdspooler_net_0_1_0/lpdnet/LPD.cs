using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using log4net;
using sf.net.lpdnet.thread;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet
{
	/// <summary>
	/// The Line Printer Deamon (LPD). Sets up all of the network communication to run our LPD.
	/// Multithreaded class that handles each client connection on a different thread.
	/// <p>
	/// Two different threads are initiated internaly during execution:
	/// <list type="bullet">
	///   <item>
	///      <term>New connections thread</term>
	///      <description>
	///      This thread will open a new server socket connection on the localhost using the port specified in the 
	///      configuration file <b>(sf.net.lpdnet.exe.config</b> or <b>sf.net.lpdnet.manager.exe.config)</b>. It will listen for new client connections
	///      until lpd is stopped. 
	///      Once a new connection is accepted a new thread is started to handle the connection requests.
	///      </description>
	///   </item>
	///   <item>
	///      <term>Request handling thread</term>
	///      <description>
	///      This thread will handle all requests made by a single connection. If an exception occurrs during the request 
	///      handling the thread will be terminated safely.
	///      </description>
	///   </item>
	/// </list>
	/// </p>
	/// </summary>
	public class LPD : Runnable
	{
		private static ILog log = LogManager.GetLogger(typeof (LPD));
		private static LPD INSTANCE = new LPD(); //LPD instance

		private int port; //lpd port number
		private TcpListener serverSocket = null; //server socket
		private Thread serverThread = null; //server thread
		private bool running; //indicates that the server is running
		private LPDThreadPool threadPool = null;

		/// <summary>
		/// Use this delegate if you want to receive OnStartServer events
		/// </summary>
		public delegate void startServerDelegate(Object sender);

		/// <summary>
		/// Use this delegate if you want to receive OnStopServer events
		/// </summary>
		public delegate void stopServerDelegate(Object sender);

		/// <summary>
		/// Event that is fired when the lpd server is started
		/// </summary>
		public event startServerDelegate startServerEvent;

		/// <summary>
		/// Event that is fired when the lpd server is stopped
		/// </summary>
		public event stopServerDelegate stopServerEvent;

		/// <summary>
		/// Constructor for LPD
		/// </summary>
		private LPD()
		{
		}

		/// <summary>
		/// This class is a singleton, meaning that only one instance of LPD can be created.
		/// </summary>
		/// <returns>The only instance of LPD</returns>
		public static LPD getInstance()
		{
			return INSTANCE;
		}

		/// <summary>
		/// Starts LPD server
		/// </summary>
		public void run()
		{
			try
			{
				ConfigUtil config = new ConfigUtil();
				port = config.getInteger("lpd.port");

				if (port.Equals(Int32.MinValue))
				{
					log.Warn("The LPD port is not defined or is invalid, using default port 515.");
					port = 515;
					//throw new LPDException("Port number not defined");
				}

				//new thread pool of 10 threads
				threadPool = new LPDThreadPool(10);

				serverSocket = new TcpListener(IPAddress.Parse("127.0.0.1"), port);
				serverSocket.Start(); //Start listening on localhost

				//Starts a thread for accepting connections
				running = true;
				serverThread = new Thread(new ThreadStart(acceptConnectionsThread));
				serverThread.Start();
				onServerStart(this);
				log.Info("LPD Server started on port " + port);
			}
			catch (Exception e)
			{
				log.Error(e.Message, e);
			}
		}

		/// <summary>
		/// Stops LPD server
		/// </summary>
		public void stop()
		{
			if (null != serverSocket)
			{
				try
				{
					log.Debug("Shutting down LPD server.");
					running = false;
					//Stop socket, this will raise an exception on serverSocket.AcceptSocket()
					serverSocket.Stop();

					// Wait for one second for the the thread to stop.
					serverThread.Join(2000);

					// If still alive; Get rid of the thread.
					if (serverThread.IsAlive)
					{
						serverThread.Abort();
					}

					//shutdown pool
					threadPool.close();

					//Release objects
					serverThread = null;
					serverSocket = null;
					onServerStop(this);
					log.Debug("LPD server stopped gracefully.");
				}
				catch
				{
					//do noting...
				}
			}
		}

		/// <summary>
		/// Indicates if the server is running
		/// </summary>
		/// <returns>True if the LPD server is running, False if not</returns>
		public bool isRunning()
		{
			return running;
		}

		/// <summary>
		/// Accept new client connections
		/// </summary>
		private void acceptConnectionsThread()
		{
			if (serverSocket == null)
			{
				return;
			}

			while (running)
			{
				try
				{
					log.Debug("Trying to accept new socket connection.");

					//Blocks execution until a connection is accepted
					//The safest way to unblock the serverSocket is by calling LPD.stop()
					Socket connection = serverSocket.AcceptSocket();

					//Start a new thread for handling connection requests
					ConnectionHandler handler = new ConnectionHandler(connection);
					threadPool.add(handler);
					log.Debug("Connection accepted.");
				}
				catch (Exception e)
				{
					log.Warn(e.Message, e);
				}
			}
		}

		/// <summary>
		/// Fires when the LPD server is started
		/// </summary>
		/// <param name="sender"></param>
		protected virtual void onServerStart(Object sender)
		{
			if (startServerEvent != null)
			{
				log.Debug("Invoking OnStartServer event delegates.");
				startServerEvent(this);
			}
		}

		/// <summary>
		/// Fires when the LPD server is stopped
		/// </summary>
		/// <param name="sender"></param>
		protected virtual void onServerStop(Object sender)
		{
			if (stopServerEvent != null)
			{
				log.Debug("Invoking OnStopServer event delegates.");
				stopServerEvent(this);
			}
		}
	}
}