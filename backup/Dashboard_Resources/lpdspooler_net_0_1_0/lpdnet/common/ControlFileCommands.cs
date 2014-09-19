using System;
using System.Collections;
using System.IO;
using System.Text;
using log4net;
using sf.net.lpdnet.exception;
using sf.net.lpdnet.utils;

namespace sf.net.lpdnet.common
{
	/// <summary>
	/// Control file commands
	/// </summary>
	public class ControlFileCommands
	{
		private static ILog log = LogManager.GetLogger(typeof (ControlFileCommands));

		private String classForBannerPage;
		private String host;
		private String indentCount;
		private String jobName;
		private String userName;
		private String email;
		private String fileName;
		private String userId;
		private String symbolicLinkData;
		private String title;
		private String fileToUnlink;
		private String widthCount;
		private String troffRFontFileName;
		private String troffIFontName;
		private String troffBFontName;
		private String troffSFontName;
		private String plotCIFFileName;
		private String printDVIFileName;
		private String fileToPrintAsText;
		private String fileToPlot;
		private String fileToPrintAsTextRaw;
		private String fileToPrintAsDitroff;
		private String fileToPrintAsPostscript;
		private String fileToPrintAsPr;
		private String fileToPrintFortran;
		private String fileToPrintAsTroff;
		private String fileToPrintAsRaster;

		/// <summary>
		/// 
		/// </summary>
		public string ClassForBannerPage
		{
			get { return classForBannerPage; }
			set { classForBannerPage = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string Host
		{
			get { return host; }
			set { host = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string IndentCount
		{
			get { return indentCount; }
			set { indentCount = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string JobName
		{
			get { return jobName; }
			set { jobName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string UserName
		{
			get { return userName; }
			set { userName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string Email
		{
			get { return email; }
			set { email = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileName
		{
			get { return fileName; }
			set { fileName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string UserId
		{
			get { return userId; }
			set { userId = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string SymbolicLinkData
		{
			get { return symbolicLinkData; }
			set { symbolicLinkData = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string Title
		{
			get { return title; }
			set { title = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToUnlink
		{
			get { return fileToUnlink; }
			set { fileToUnlink = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string WidthCount
		{
			get { return widthCount; }
			set { widthCount = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string TroffRFontFileName
		{
			get { return troffRFontFileName; }
			set { troffRFontFileName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string TroffIFontName
		{
			get { return troffIFontName; }
			set { troffIFontName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string TroffBFontName
		{
			get { return troffBFontName; }
			set { troffBFontName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string TroffSFontName
		{
			get { return troffSFontName; }
			set { troffSFontName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string PlotCifFileName
		{
			get { return plotCIFFileName; }
			set { plotCIFFileName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string PrintDviFileName
		{
			get { return printDVIFileName; }
			set { printDVIFileName = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintAsText
		{
			get { return fileToPrintAsText; }
			set { fileToPrintAsText = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPlot
		{
			get { return fileToPlot; }
			set { fileToPlot = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintAsTextRaw
		{
			get { return fileToPrintAsTextRaw; }
			set { fileToPrintAsTextRaw = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintAsDitroff
		{
			get { return fileToPrintAsDitroff; }
			set { fileToPrintAsDitroff = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintAsPostscript
		{
			get { return fileToPrintAsPostscript; }
			set { fileToPrintAsPostscript = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintAsPr
		{
			get { return fileToPrintAsPr; }
			set { fileToPrintAsPr = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintFortran
		{
			get { return fileToPrintFortran; }
			set { fileToPrintFortran = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintAsTroff
		{
			get { return fileToPrintAsTroff; }
			set { fileToPrintAsTroff = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		public string FileToPrintAsRaster
		{
			get { return fileToPrintAsRaster; }
			set { fileToPrintAsRaster = value; }
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="bytes"></param>
		public ControlFileCommands(byte[] bytes)
		{
			try
			{
				IList buffer = new ArrayList();

				for (int i = 0; i < bytes.Length; i++)
				{
					byte b = bytes[i];

					if (b != '\n')
					{
						buffer.Add(b);
					}
					else
					{
						buffer.Add(b);
						processCommand(buffer);
						buffer.Clear();
					}
				}
			}
			catch (IOException e)
			{
				log.Error(e.Message);
				throw new LPDException(e.Message, e);
			}
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="command"></param>
		protected void processCommand(IList command)
		{
			IList bytes = ByteUtil.parseCommand(ByteUtil.listToByteArray(command));
			String commandcode = StringUtil.parse((byte[]) bytes[0]);
			String operand = null;

			//this handles job names with spaces. This is a BUG in java lpdspooler
			if (bytes.Count > 2)
			{
				StringBuilder sb = new StringBuilder(bytes.Count - 1);
				for (int i = 1; i < bytes.Count; i++)
				{
					sb.Append(StringUtil.parse((byte[]) bytes[i])).Append(" ");
				}
				operand = sb.ToString().Trim();
			}
			else
			{
				operand = StringUtil.parse((byte[]) bytes[1]);
			}

			setAttribute(commandcode, operand);
		}

		/// <summary>
		/// Switches on the command code and sets the appropriate attribute.
		/// </summary>
		/// <param name="commandcode">The flag describing what the attribute is</param>
		/// <param name="operand">The attribute</param>
		protected void setAttribute(String commandcode, String operand)
		{
			if (commandcode == null)
			{
				// do nothing
			}
			else if (commandcode.Equals("C"))
			{
				ClassForBannerPage = operand;
			}
			else if (commandcode.Equals("H"))
			{
				Host = operand;
			}
			else if (commandcode.Equals("I"))
			{
				IndentCount = operand;
			}
			else if (commandcode.Equals("J"))
			{
				JobName = operand;
			}
			else if (commandcode.Equals("L"))
			{
				UserName = operand;
			}
			else if (commandcode.Equals("M"))
			{
				Email = operand;
			}
			else if (commandcode.Equals("N"))
			{
				FileName = operand;
			}
			else if (commandcode.Equals("P"))
			{
				UserId = operand;
			}
			else if (commandcode.Equals("S"))
			{
				SymbolicLinkData = operand;
			}
			else if (commandcode.Equals("T"))
			{
				Title = operand;
			}
			else if (commandcode.Equals("U"))
			{
				FileToUnlink = operand;
			}
			else if (commandcode.Equals("W"))
			{
				WidthCount = operand;
			}
			else if (commandcode.Equals("1"))
			{
				TroffRFontFileName = operand;
			}
			else if (commandcode.Equals("2"))
			{
				TroffIFontName = operand;
			}
			else if (commandcode.Equals("3"))
			{
				TroffBFontName = operand;
			}
			else if (commandcode.Equals("4"))
			{
				TroffSFontName = operand;
			}
			else if (commandcode.Equals("c"))
			{
				PlotCifFileName = operand;
			}
			else if (commandcode.Equals("d"))
			{
				PrintDviFileName = operand;
			}
			else if (commandcode.Equals("f"))
			{
				FileToPrintAsText = operand;
			}
			else if (commandcode.Equals("g"))
			{
				FileToPlot = operand;
			}
			else if (commandcode.Equals("l"))
			{
				FileToPrintAsTextRaw = operand;
			}
			else if (commandcode.Equals("n"))
			{
				FileToPrintAsDitroff = operand;
			}
			else if (commandcode.Equals("o"))
			{
				FileToPrintAsPostscript = operand;
			}
			else if (commandcode.Equals("p"))
			{
				FileToPrintAsPr = operand;
			}
			else if (commandcode.Equals("r"))
			{
				FileToPrintFortran = operand;
			}
			else if (commandcode.Equals("t"))
			{
				FileToPrintAsTroff = operand;
			}
			else if (commandcode.Equals("v"))
			{
				FileToPrintAsRaster = operand;
			}
		}
	}
}