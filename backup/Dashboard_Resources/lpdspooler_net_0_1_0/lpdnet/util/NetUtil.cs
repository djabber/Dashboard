using System.Collections;
using System.IO;
using log4net;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// 
	/// </summary>
	public class NetUtil
	{
		private static ILog log = LogManager.GetLogger(typeof (NetUtil));

		/// <summary>
		/// Good acknowledge value defined by the RFC1179
		/// </summary>
		public static char GOOD_ACK = '\x0';

		/// <summary>
		/// Bad acknowledge value defined by the RFC1179
		/// </summary>
		public static char BAD_ACK = '\x1';

		/// <summary>
		/// Default constructor
		/// </summary>
		public NetUtil()
		{
		}

		/// <summary>
		/// Reads a command defined by the RFC1179 spec.  
		/// Basically the command is all the data until we see 0x10.
		/// </summary>
		/// <param name="reader">The StreamReader providing us with a command from the client.</param>
		/// <returns></returns>
		public byte[] readCommand(StreamReader reader)
		{
			ArrayList bytes = new ArrayList();
			int data = reader.Read();

			while (-1 != data && 10 != data)
			{
				bytes.Add(data);
				data = reader.Read();
			}

			if (10 == data)
			{
				bytes.Add(data); // keep the LineFeed for now
			}

//         log.Debug("Command = " + StringUtil.parse(bytes));
			return ByteUtil.listToByteArray(bytes);
		}

		/// <summary>
		/// Used by CommandReceiveJob to read the headers of the
		/// ControlFile or the DataFile from the client.
		/// Please see section 6 of the RFC1179 specification for more details.
		/// </summary>
		/// <param name="reader">StreamReader from the client sending us the print job</param>
		/// <param name="writer">StreamWriter to the client we are writing reponses to</param>
		/// <returns></returns>
		public byte[] readNextInput(StreamReader reader, StreamWriter writer)
		{
			ArrayList bytes = new ArrayList();
			int data = reader.Read();

			while (-1 != data && 10 != data)
			{
				bytes.Add(data);
				data = reader.Read();
			}

			if (10 == data)
			{
				bytes.Add(data); // keep the LineFeed for now
			}

//         log.Debug("Command = " + StringUtil.parse(bytes));
			writer.Write(GOOD_ACK);

			return ByteUtil.listToByteArray(bytes);
		}

		/// <summary>
		/// Reads the ControlFile and retunrs it as a byte[]
		/// </summary>
		/// <param name="reader">StreamReader from the client sending us the control file</param>
		/// <param name="writer">StreamReader to the client we are writing reponses to</param>
		/// <returns>A byte[] from the InputStream holding the ControlFile</returns>
		public byte[] readControlFile(StreamReader reader, StreamWriter writer)
		{
			ArrayList bytes = new ArrayList();
			int data = reader.Read();

			while (-1 != data && 0 != data)
			{
				bytes.Add(data);
				data = reader.Read();
			}

//         log.Debug("Command = " + StringUtil.parse(bytes));
			writer.Write(GOOD_ACK);

			return ByteUtil.listToByteArray(bytes);
		}

		/// <summary>
		/// Reads the DataFile and returns it as a byte[]
		/// </summary>
		/// <param name="reader">StreamReader from the client sending us the data file</param>
		/// <param name="writer">StreamReader to the client we are writing reponses to</param>
		/// <returns>A byte[] from the InputStream holding the DataFile</returns>
		public byte[] readPrintFile(StreamReader reader, StreamWriter writer)
		{
			ArrayList bytes = new ArrayList();
			int data = reader.Read();

			while (-1 != data && 0 != data)
			{
				bytes.Add(data);
				data = reader.Read();
			}

//         log.Debug("Command = " + StringUtil.parse(bytes));
			writer.Write(GOOD_ACK);

			return ByteUtil.listToByteArray(bytes);
		}

		/// <summary>
		/// Reads the DataFile and returns it as a byte[]
		/// </summary>
		/// <param name="reader">StreamReader from the client sending us the data file</param>
		/// <param name="writer">StreamWriter to the client we are writing reponses to</param>
		/// <param name="size">Size the number of bytes to read from the stream</param>
		/// <returns>A byte[] from the InputStream holding the DataFile</returns>
		public byte[] readPrintFile(StreamReader reader, StreamWriter writer, int size)
		{
			ArrayList bytes = new ArrayList();
			int data = reader.Read();
			int count = 1;

			while (-1 != data && size >= count)
			{
				bytes.Add(data);
				data = reader.Read();
				count++;
			}

//         log.Debug("Command = " + StringUtil.parse(bytes));
			writer.Write(GOOD_ACK);

			return ByteUtil.listToByteArray(bytes);
		}
	}
}