using System;
using System.IO;
using System.Net.Sockets;
using log4net;
using sf.net.lpdnet.command;
using sf.net.lpdnet.thread;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet
{
	/// <summary>
	/// Handles all requests received by a single connection
	/// </summary>
	/// 
	public class ConnectionHandler : Runnable
	{
		private static ILog log = LogManager.GetLogger(typeof (ConnectionHandler));

		private bool running = false;
		private NetworkStream stream = null;
		private StreamWriter writer = null;
		private StreamReader reader = null;
		private Socket connection = null;

		/// <summary>
		/// Default constructor. It will start a new thread to handle connection requests.
		/// </summary>
		/// <param name="connection">
		/// The client connection
		/// </param>
		public ConnectionHandler(Socket connection)
		{
			this.connection = connection;
			stream = new NetworkStream(connection);
			writer = new StreamWriter(stream);
			reader = new StreamReader(stream);
			writer.AutoFlush = true;
		}

		/// <summary>
		/// Reads and handles all commands from a connection.
		/// </summary>
		public void run()
		{
			try
			{
				running = true;
				LPDCommands lpdCommands = new LPDCommands();
				NetUtil netUtil = new NetUtil();

				//Gets the command from client connection
				byte[] command = netUtil.readCommand(reader);

				log.Debug(StringUtil.parseHumanRead(command));

				//Processes the command.
				lpdCommands.handleCommand(command, reader, writer);
			}
			catch (Exception e)
			{
				log.Error(e.Message, e);
			}
			finally //close and destroy all open streams
			{
				if (null != connection)
				{
					log.Debug("Closing connection.");
					try
					{
						connection.Shutdown(SocketShutdown.Both);
						connection.Close();
						connection = null;
					}
					catch (Exception e)
					{
						log.Error(e.Message, e);
					}
				}

				if (null != reader)
				{
					log.Debug("Closing reader.");
					try
					{
						reader.Close();
						reader = null;
					}
					catch (Exception e)
					{
						log.Error(e.Message, e);
					}
				}

				if (null != writer)
				{
					log.Debug("Closing writer.");
					try
					{
						writer.Close();
						writer = null;
					}
					catch (Exception e)
					{
						log.Error(e.Message, e);
					}
				}

				if (null != stream)
				{
					log.Debug("Closing stream.");
					try
					{
						stream.Close();
						stream = null;
					}
					catch (Exception e)
					{
						log.Error(e.Message, e);
					}
				}
				running = false;
			}
		}

		/// <summary>
		/// Stops the connection handling process
		/// </summary>
		public void stop()
		{
		}

		/// <summary>
		/// Indicates if the connection handling process is running
		/// </summary>
		/// <returns></returns>
		public bool isRunning()
		{
			return running;
		}
	}
}