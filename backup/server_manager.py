from get_status import GetStatus


class ServerManager():
	
	def __init__(self): pass
	
	def getFileServerInfo(self):
		p = pingFileServer('147.26.195.210')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"
	
	def getDBServerInfo():
		p = pingDbServer('147.26.195.135')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"
	
	def getFaxServerInfo():
		p = pingFaxServer('147.26.195.129')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"
	
	def getPrintServerInfo():
		p = pingPrintServer('147.26.195.134')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"
	
	def getCloud1Info():
		p = pingCloudServer('147.26.195.139')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"
	
	def getCloud2Info():
		p = pingCloudServer('147.26.195.139')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"
		
	def pingPrinterUp1():
		p = pingPrinter('147.26.195.144')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"
		
	def pingPrinterUp2():
		p = pingPrinter('147.26.195.132')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"

	def pingPrinterUp3():
		p = pingPrinter('147.26.195.89')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"

	def pingPrinterUp4():
		p = pingPrinter('147.26.195.160')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"

	def pingPrinterDn1():
		p = pingPrinter('147.26.195.131')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"

	def pingPrinterDn2():
		p = pingPrinter('147.26.195.119')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"

	def pingPrinterDn3():
		p = pingPrinter('147.26.195.152')
		if p == 0:
			return "<p>ip is up.</p>"
		else:
			return "<p>ip is down.</p>"

if __name__ == '__main__':
    getFileServerInfo()
