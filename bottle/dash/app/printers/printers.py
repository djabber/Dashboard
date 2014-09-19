import cups

class Printers:
	
	def __init__(self):  pass

	def getPrinterInfo(server, self):
		
		cups.setServer(server)
		#cups.setUser(user)
		conn = cups.Connection()
		printers = conn.getPrinters()
		for printer in printers:
			print printer, printers[printer]["device-uri"]
			
	def gethost (host=None, depth=0):
		if host:
			cups.setServer (host)
		else:
			host = "localhost"
		c = cups.Connection ()
		printers = c.getPrinters ()
		classes = c.getClasses ()
		indent = do_indent(depth)
		
		for name, queue in printers.items ():
			getqueue (name, queue, host, depth, printers, classes)
