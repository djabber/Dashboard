from server_status import ServerStatus


class GetStatus:
	
	def __init__(self): pass

	def pingFileServer(self, ip):
		return ping(ip)
		
	def pingDbServer(self, ip):
		return ping(ip)

	def pingFaxServer(self, ip):
		return ping(ip)

	def pingPrintServer(self, ip):
		return ping(ip)
		
	def pingCloudServer(self, ip):
		return ping(ip)

	def pingPrinter(self, ip):
		return ping(ip)
