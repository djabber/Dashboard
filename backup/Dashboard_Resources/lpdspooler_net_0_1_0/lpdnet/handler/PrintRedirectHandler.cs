using System;
using System.Drawing.Printing;
using sf.net.lpdnet.common;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.handler
{
	/// <summary>
	/// Handler that redirect print job data to a system printer
	/// </summary>
	public class PrintRedirectHandler : HandlerInterface
	{
		private PrinterSettings printerSettings = null;

		/// <summary>
		/// Default constructor
		/// </summary>
		public PrintRedirectHandler()
		{
		}

		/// <summary>
		/// Sets the printer to use
		/// </summary>
		/// <param name="printerSettings">Printer settings</param>
		public void setRedirectionPrinter(PrinterSettings printerSettings)
		{
			this.printerSettings = printerSettings;
		}

		/// <summary>
		/// Sets the printer to use
		/// </summary>
		/// <param name="printerName">Printer name</param>
		public void setRedirectionPrinter(String printerName)
		{
			printerSettings = new PrinterSettings();
			printerSettings.PrinterName = printerName;
		}

		/// <summary>
		/// Gets the redirection printer name
		/// </summary>
		/// <returns></returns>
		public String getRedirectinPrinter()
		{
			if (printerSettings != null)
			{
				return printerSettings.PrinterName;
			}
			else
			{
				return "";
			}
		}

		/// <summary>
		/// Redirect the print job to a specified printer
		/// </summary>
		/// <param name="printJob"></param>
		/// <returns></returns>
		public bool process(PrintJob printJob)
		{
			String tmp = StringUtil.parse(printJob.DataFile.Contents);
			PrintUtil.sendStringToPrinter(printerSettings.PrinterName, tmp);
			return true;
		}
	}
}