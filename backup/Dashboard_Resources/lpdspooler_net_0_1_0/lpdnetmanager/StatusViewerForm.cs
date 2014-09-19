using System;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using sf.net.lpdnet.handler;
using sf.net.lpdnet.manager.ui;
using sf.net.lpdnet.queue;
using sf.net.lpdnet.thread;

namespace sf.net.lpdnet.manager
{
	/// <summary>
	/// Summary description for Form1.
	/// </summary>
	public class StatusViewerForm : Form
	{
		private Hashtable printJobsList = new Hashtable();
		private LPD lpdDeamon = null;
		private PrintQueue fileQueue = null;
		private PrintQueue redirectQueue = null;
		private LPDThread lpdLpdThread = null;
		private LPDThread redirectMonitorLpdThread = null;
		private LPDThread fileMonitorLpdThread = null;

		private MenuItem menuItem1;
		private MenuItem menuItem2;
		private MenuItem menuItem5;
		private MainMenu mainMenu;
		private MenuItem mnuStartLPD;
		private MenuItem mnuStopLPD;
		private MenuItem mnuExit;
		private System.Windows.Forms.ColumnHeader jobIdColumn;
		private System.Windows.Forms.ColumnHeader jobNameColumn;
		private System.Windows.Forms.ColumnHeader sizeColumn;
		private System.Windows.Forms.ColumnHeader dateColumn;
		private System.Windows.Forms.ColumnHeader ownerColumn;
		private System.Windows.Forms.ColumnHeader statusColumn;
		private System.Windows.Forms.ListView printJobsListView;
		private System.Windows.Forms.Splitter splitter1;
		private System.Windows.Forms.Panel panel1;
		private System.Windows.Forms.Label lblLog;

		/// <summary>
		/// Required designer variable.
		/// </summary>
		private Container components = null;

		public StatusViewerForm()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			//
			// TODO: Add any constructor code after InitializeComponent call
			//
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose(bool disposing)
		{
			if (disposing)
			{
				if (components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.mainMenu = new System.Windows.Forms.MainMenu();
			this.menuItem1 = new System.Windows.Forms.MenuItem();
			this.menuItem2 = new System.Windows.Forms.MenuItem();
			this.mnuStartLPD = new System.Windows.Forms.MenuItem();
			this.mnuStopLPD = new System.Windows.Forms.MenuItem();
			this.menuItem5 = new System.Windows.Forms.MenuItem();
			this.mnuExit = new System.Windows.Forms.MenuItem();
			this.jobIdColumn = new System.Windows.Forms.ColumnHeader();
			this.jobNameColumn = new System.Windows.Forms.ColumnHeader();
			this.sizeColumn = new System.Windows.Forms.ColumnHeader();
			this.dateColumn = new System.Windows.Forms.ColumnHeader();
			this.ownerColumn = new System.Windows.Forms.ColumnHeader();
			this.statusColumn = new System.Windows.Forms.ColumnHeader();
			this.printJobsListView = new System.Windows.Forms.ListView();
			this.splitter1 = new System.Windows.Forms.Splitter();
			this.panel1 = new System.Windows.Forms.Panel();
			this.lblLog = new System.Windows.Forms.Label();
			this.panel1.SuspendLayout();
			this.SuspendLayout();
			// 
			// mainMenu
			// 
			this.mainMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					 this.menuItem1});
			// 
			// menuItem1
			// 
			this.menuItem1.Index = 0;
			this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.menuItem2,
																					  this.menuItem5,
																					  this.mnuExit});
			this.menuItem1.Text = "File";
			// 
			// menuItem2
			// 
			this.menuItem2.Index = 0;
			this.menuItem2.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.mnuStartLPD,
																					  this.mnuStopLPD});
			this.menuItem2.Text = "LPD Server";
			// 
			// mnuStartLPD
			// 
			this.mnuStartLPD.Index = 0;
			this.mnuStartLPD.Text = "Start";
			this.mnuStartLPD.Click += new System.EventHandler(this.mnuStartLPD_Click);
			// 
			// mnuStopLPD
			// 
			this.mnuStopLPD.Enabled = false;
			this.mnuStopLPD.Index = 1;
			this.mnuStopLPD.Text = "Stop";
			this.mnuStopLPD.Click += new System.EventHandler(this.mnuStopLPD_Click);
			// 
			// menuItem5
			// 
			this.menuItem5.Index = 1;
			this.menuItem5.Text = "-";
			// 
			// mnuExit
			// 
			this.mnuExit.Index = 2;
			this.mnuExit.Text = "Exit";
			this.mnuExit.Click += new System.EventHandler(this.mnuExit_Click);
			// 
			// jobIdColumn
			// 
			this.jobIdColumn.Text = "Id";
			this.jobIdColumn.Width = 80;
			// 
			// jobNameColumn
			// 
			this.jobNameColumn.Text = "Name";
			this.jobNameColumn.Width = 180;
			// 
			// sizeColumn
			// 
			this.sizeColumn.Text = "Size";
			this.sizeColumn.Width = 70;
			// 
			// dateColumn
			// 
			this.dateColumn.Text = "Date";
			this.dateColumn.Width = 80;
			// 
			// ownerColumn
			// 
			this.ownerColumn.Text = "Owner";
			this.ownerColumn.Width = 180;
			// 
			// statusColumn
			// 
			this.statusColumn.Text = "Status";
			// 
			// printJobsListView
			// 
			this.printJobsListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																								this.jobIdColumn,
																								this.jobNameColumn,
																								this.sizeColumn,
																								this.dateColumn,
																								this.ownerColumn,
																								this.statusColumn});
			this.printJobsListView.Dock = System.Windows.Forms.DockStyle.Top;
			this.printJobsListView.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
			this.printJobsListView.Location = new System.Drawing.Point(0, 0);
			this.printJobsListView.MultiSelect = false;
			this.printJobsListView.Name = "printJobsListView";
			this.printJobsListView.Size = new System.Drawing.Size(668, 335);
			this.printJobsListView.TabIndex = 6;
			this.printJobsListView.View = System.Windows.Forms.View.Details;
			// 
			// splitter1
			// 
			this.splitter1.Dock = System.Windows.Forms.DockStyle.Top;
			this.splitter1.Location = new System.Drawing.Point(0, 335);
			this.splitter1.Name = "splitter1";
			this.splitter1.Size = new System.Drawing.Size(668, 3);
			this.splitter1.TabIndex = 7;
			this.splitter1.TabStop = false;
			// 
			// panel1
			// 
			this.panel1.Controls.Add(this.lblLog);
			this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.panel1.Location = new System.Drawing.Point(0, 338);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(668, 66);
			this.panel1.TabIndex = 8;
			// 
			// lblLog
			// 
			this.lblLog.Dock = System.Windows.Forms.DockStyle.Fill;
			this.lblLog.Location = new System.Drawing.Point(0, 0);
			this.lblLog.Name = "lblLog";
			this.lblLog.Size = new System.Drawing.Size(668, 66);
			this.lblLog.TabIndex = 0;
			// 
			// StatusViewerForm
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(668, 404);
			this.Controls.Add(this.panel1);
			this.Controls.Add(this.splitter1);
			this.Controls.Add(this.printJobsListView);
			this.Menu = this.mainMenu;
			this.Name = "StatusViewerForm";
			this.Text = "LPD Manager";
			this.Closing += new System.ComponentModel.CancelEventHandler(this.StatusViewerForm_Closing);
			this.Load += new System.EventHandler(this.MainForm_Load);
			this.panel1.ResumeLayout(false);
			this.ResumeLayout(false);

		}

		#endregion

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		private static void Main()
		{
			Application.Run(new StatusViewerForm());
		}

		private void updateListView()
		{
			printJobsListView.Items.Clear();

			for (IEnumerator enu = printJobsList.Keys.GetEnumerator(); enu.MoveNext();)
			{
				String jobId = enu.Current as string;
				QueuedPrintJobModel job = printJobsList[jobId] as QueuedPrintJobModel;
				ListViewItem item = new ListViewItem(jobId);
				item.SubItems.Add(job.JobInfo.Name);
				item.SubItems.Add(job.JobInfo.Size.ToString());
				item.SubItems.Add(job.JobInfo.TimeStamp.ToString());
				item.SubItems.Add(job.JobInfo.Owner);
				item.SubItems.Add(job.Status);

				printJobsListView.Items.Add(item);
			}
		}

		private void onAddJob(Object sender, String queue, QueuedPrintJobInfo job)
		{
			//When a new job is added to a queue add it to the ListView with status as "In process"
			String jobId = QueuedPrintJobModel.makeId(queue, job.Id);
			printJobsList.Add(jobId, new QueuedPrintJobModel(job, queue, QueuedPrintJobModel.STATUS_IN_PROGRESS));
			updateListView();
			doLog("New print job added to queue.");
		}

		private void onRemoveJob(Object sender, String queue, long jobId)
		{
			//When a job is removed from a queue add it to the ListView with status as "Completed"
			String job = QueuedPrintJobModel.makeId(queue, jobId);
			if (printJobsList.Contains(job))
			{
				QueuedPrintJobModel printJob = printJobsList[job] as QueuedPrintJobModel;
				printJob.Status = QueuedPrintJobModel.STATUS_COMPLETE;
			}

			updateListView();
		}

		private void onRemoveAllJobs(Object sender, String queue)
		{
//         printJobsList.Clear();
//         updateListView();
		}

		private void onLPDStart(Object sender)
		{
			mnuStartLPD.Enabled = false;
			mnuStopLPD.Enabled = true;
			doLog("Server started.");
		}

		private void onLPDStop(Object sender)
		{
			mnuStopLPD.Enabled = false;
			mnuStartLPD.Enabled = true;
			doLog("Server stopped.");
		}

		private void MainForm_Load(object sender, EventArgs e)
		{
			//gets the instance of LPD server
			lpdDeamon = LPD.getInstance();

			//gets the instance of Queues
			Queues queues = Queues.getInstance();

			//Start receiving events when a new job is created
			queues.addJobEvent += new Queues.addJobDelegate(onAddJob);

			//Start receiving events when a job is removed
			queues.removeJobEvent += new Queues.removeJobDelegate(onRemoveJob);

			//Start receiving events when a all jobs are removed
			queues.removeAllJobsEvent += new Queues.removeAllJobsDelegate(onRemoveAllJobs);

			//Start receiving events when the LPD starts
			lpdDeamon.startServerEvent += new LPD.startServerDelegate(onLPDStart);

			//Start receiving events when a LPD stops
			lpdDeamon.stopServerEvent += new LPD.stopServerDelegate(onLPDStop);

			//Create a new SaveToFile handler
			//Set the file extension to use for the print jobs to be saved
			//Set the output directory where the print job are going to be saved
			//Create a queue named "FILE" that uses the SaveToFile handler for handling its jobs
			SaveToFileHandler saveToFileHandler = new SaveToFileHandler();
			saveToFileHandler.Extension = ".pjb";
			saveToFileHandler.OutputDirectory = "c:\\";
			fileQueue = queues.createQueue("FILE", saveToFileHandler);

			//Create a new PrintRedirect handler
			//Set the redirection printer name
			//Create a new queue named "RAW" that uses the PrintRedirect handler for handling all its jobs
			PrintRedirectHandler printRedirectHandler = new PrintRedirectHandler();

			//You can get all system installed printers by calling PrinterSettings.InstalledPrinters
			printRedirectHandler.setRedirectionPrinter("LPD_QUEUE");
			redirectQueue = queues.createQueue("RAW", printRedirectHandler);

			//Create a new queue monitor for the queue named "FILE"
			QueueMonitor fileQueueMonitor = new QueueMonitor(fileQueue);

			//Create a new queue monitor for the queue named "RAW"
			QueueMonitor redirectQueueMonitor = new QueueMonitor(redirectQueue);

			//Create threads for running LPD and queue monitors
			lpdLpdThread = new LPDThread(lpdDeamon);
			fileMonitorLpdThread = new LPDThread(fileQueueMonitor);
			redirectMonitorLpdThread = new LPDThread(redirectQueueMonitor);

			//Start queue monitors
			fileMonitorLpdThread.start();
			redirectMonitorLpdThread.start();
		}

		private void mnuStartLPD_Click(object sender, EventArgs e)
		{
			//Start LPD thread
			lpdLpdThread.start();
		}

		private void mnuStopLPD_Click(object sender, EventArgs e)
		{
			//Stop LPD thread
			lpdLpdThread.stop();
		}

		private void StatusViewerForm_Closing(object sender, CancelEventArgs e)
		{
			//Before closing the application remember to stop all threads
			lpdLpdThread.stop();
			fileMonitorLpdThread.stop();
			redirectMonitorLpdThread.stop();
		}

		private void mnuExit_Click(object sender, EventArgs e)
		{
			Close();
		}

		private void doLog(string logMsg)
		{
			lblLog.Text = lblLog.Text + DateTime.Now.ToString() + " - " + logMsg + "\n";
		}
	}
}