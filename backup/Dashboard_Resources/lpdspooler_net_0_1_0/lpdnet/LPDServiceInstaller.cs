using System.ComponentModel;
using System.Configuration.Install;
using System.ServiceProcess;

namespace sf.net.lpdnet
{
	/// <summary>
	/// Installs LPD as a service. Call "installUtil.exe lpdnet.exe" to install the service.
	/// InstallUtill.exe is provided with the .Net Framework.
	/// 
	/// </summary>
	[RunInstaller(true)]
	public class LPDServiceInstaller : Installer
	{
		private ServiceInstaller serviceInstaller;
		private ServiceProcessInstaller serviceProcessInstaller;

		/// <summary>
		/// Default constructor
		/// </summary>
		public LPDServiceInstaller()
		{
			serviceInstaller = new ServiceInstaller();
			serviceProcessInstaller = new ServiceProcessInstaller();
			serviceInstaller.ServiceName = "sf.net.lpdnet";
			serviceInstaller.DisplayName = "LPD.Net server";
			serviceInstaller.StartType = ServiceStartMode.Automatic;
			serviceProcessInstaller.Account = ServiceAccount.LocalSystem;
			Installers.Add(serviceProcessInstaller);
			Installers.Add(serviceInstaller);
		}
	}
}