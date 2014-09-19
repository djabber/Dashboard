using System;
using System.IO;
using System.Runtime.InteropServices;
using log4net;

namespace sf.net.lpdnet.utils
{
	/// <summary>
	/// Prints RAW data directly to a printer
	/// Source taken from http://support.microsoft.com/?kbid=322091#kb1
	/// </summary>
	public class PrintUtil
	{
		/// <summary>
		/// Class holding document information
		/// </summary>
		[StructLayout(LayoutKind.Sequential, CharSet=CharSet.Ansi)]
		public class DocInfo
		{
			[MarshalAs(UnmanagedType.LPStr)] private string name;
			[MarshalAs(UnmanagedType.LPStr)] private string outputFile;
			[MarshalAs(UnmanagedType.LPStr)] private string dataType;

			/// <summary>
			/// Document name
			/// </summary>
			public string Name
			{
				get { return name; }
				set { name = value; }
			}

			/// <summary>
			/// Document output file
			/// </summary>
			public string OutputFile
			{
				get { return outputFile; }
				set { outputFile = value; }
			}

			/// <summary>
			/// Document data type
			/// </summary>
			public string DataType
			{
				get { return dataType; }
				set { dataType = value; }
			}
		}

		/// <summary>
		/// Opens Win32 printer
		/// </summary>
		/// <param name="printerName"></param>
		/// <param name="printer"></param>
		/// <param name="pd"></param>
		/// <returns></returns>
		[
			DllImport("winspool.Drv", EntryPoint="OpenPrinterA", SetLastError=true, CharSet=CharSet.Ansi, ExactSpelling=true,
				CallingConvention=CallingConvention.StdCall)]
		public static extern bool OpenPrinter([MarshalAs(UnmanagedType.LPStr)] string printerName, out IntPtr printer, long pd);

		/// <summary>
		/// Close Win32 printer
		/// </summary>
		/// <param name="printer"></param>
		/// <returns></returns>
		[
			DllImport("winspool.Drv", EntryPoint="ClosePrinter", SetLastError=true, ExactSpelling=true,
				CallingConvention=CallingConvention.StdCall)]
		public static extern bool ClosePrinter(IntPtr printer);

		/// <summary>
		/// Starts a new print document
		/// </summary>
		/// <param name="printer"></param>
		/// <param name="level"></param>
		/// <param name="docInfo"></param>
		/// <returns></returns>
		[
			DllImport("winspool.Drv", EntryPoint="StartDocPrinterA", SetLastError=true, CharSet=CharSet.Ansi, ExactSpelling=true,
				CallingConvention=CallingConvention.StdCall)]
		public static extern bool StartDocPrinter(IntPtr printer, Int32 level,
		                                          [In, MarshalAs(UnmanagedType.LPStruct)] DocInfo docInfo);

		/// <summary>
		/// Ends a print documet
		/// </summary>
		/// <param name="printer"></param>
		/// <returns></returns>
		[
			DllImport("winspool.Drv", EntryPoint="EndDocPrinter", SetLastError=true, ExactSpelling=true,
				CallingConvention=CallingConvention.StdCall)]
		public static extern bool EndDocPrinter(IntPtr printer);

		/// <summary>
		/// Starts a new print page
		/// </summary>
		/// <param name="printer"></param>
		/// <returns></returns>
		[
			DllImport("winspool.Drv", EntryPoint="StartPagePrinter", SetLastError=true, ExactSpelling=true,
				CallingConvention=CallingConvention.StdCall)]
		public static extern bool StartPagePrinter(IntPtr printer);

		/// <summary>
		/// Ends a print page
		/// </summary>
		/// <param name="printer"></param>
		/// <returns></returns>
		[
			DllImport("winspool.Drv", EntryPoint="EndPagePrinter", SetLastError=true, ExactSpelling=true,
				CallingConvention=CallingConvention.StdCall)]
		public static extern bool EndPagePrinter(IntPtr printer);

		/// <summary>
		/// Writes to the printer
		/// </summary>
		/// <param name="printer"></param>
		/// <param name="bytes"></param>
		/// <param name="count"></param>
		/// <param name="written"></param>
		/// <returns></returns>
		[
			DllImport("winspool.Drv", EntryPoint="WritePrinter", SetLastError=true, ExactSpelling=true,
				CallingConvention=CallingConvention.StdCall)]
		public static extern bool WritePrinter(IntPtr printer, IntPtr bytes, Int32 count, out Int32 written);

		private static ILog log = LogManager.GetLogger(typeof (PrintUtil));

		private static bool sendBytesToPrinter(string printerName, IntPtr bytes, Int32 count)
		{
			Int32 error = 0, written = 0;
			IntPtr printer = new IntPtr(0);
			DocInfo di = new DocInfo();
			bool success = false; // Assume failure unless you specifically succeed.

			di.Name = "sf.net.lpdnet raw document";
			di.DataType = "RAW";

			// Open the printer.
			log.Debug("Opening the printer.");
			if (OpenPrinter(printerName, out printer, 0))
			{
				// Start a document.
				log.Debug("Starting printer document.");
				if (StartDocPrinter(printer, 1, di))
				{
					log.Debug("Starting document page.");
					if (StartPagePrinter(printer))
					{
						log.Debug("Writting btyes.");
						success = WritePrinter(printer, bytes, count, out written);
						EndPagePrinter(printer);
					}
					log.Debug("Closing printer document.");
					EndDocPrinter(printer);
				}
				log.Debug("Closing printer.");
				ClosePrinter(printer);
			}
			// If you did not succeed, GetLastError may give more information
			// about why not.
			if (success == false)
			{
				error = Marshal.GetLastWin32Error();
			}
			return success;
		}

		/// <summary>
		/// Sends a file directly to the printer
		/// </summary>
		/// <param name="printerName"></param>
		/// <param name="fileName"></param>
		/// <returns></returns>
		public static bool sendFileToPrinter(string printerName, string fileName)
		{
			// Open the file.
			FileStream fs = new FileStream(fileName, FileMode.Open);
			// Create a BinaryReader on the file.
			BinaryReader br = new BinaryReader(fs);
			// Dim an array of bytes big enough to hold the file's contents.
			Byte[] bytes = new Byte[fs.Length];
			bool success = false;
			// Your unmanaged pointer.
			IntPtr unmanagedBytes = new IntPtr(0);
			int len;

			len = Convert.ToInt32(fs.Length);
			// Read the contents of the file into the array.
			bytes = br.ReadBytes(len);
			// Allocate some unmanaged memory for those bytes.
			unmanagedBytes = Marshal.AllocCoTaskMem(len);
			// Copy the managed byte array into the unmanaged array.
			Marshal.Copy(bytes, 0, unmanagedBytes, len);
			// Send the unmanaged bytes to the printer.
			success = sendBytesToPrinter(printerName, unmanagedBytes, len);
			// Free the unmanaged memory that you allocated earlier.
			Marshal.FreeCoTaskMem(unmanagedBytes);
			return success;
		}

		/// <summary>
		/// Sends a string directly to a printer
		/// </summary>
		/// <param name="printerName"></param>
		/// <param name="stringToSend"></param>
		/// <returns></returns>
		public static bool sendStringToPrinter(string printerName, string stringToSend)
		{
			IntPtr bytes;
			Int32 count;
			// How many characters are in the string?
			count = stringToSend.Length;
			// Assume that the printer is expecting ANSI text, and then convert
			// the string to ANSI text.
			bytes = Marshal.StringToCoTaskMemAnsi(stringToSend);
			// Send the converted ANSI string to the printer.
			sendBytesToPrinter(printerName, bytes, count);
			Marshal.FreeCoTaskMem(bytes);
			return true;
		}
	}
}