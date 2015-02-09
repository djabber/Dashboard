import socket, re


class Client:
	
	def __init__(self): pass


	def startClient(self, h, p):
		
		global s
		global host
		global port
				
		s= socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		
		port = 0
		
		if h == "" or h == None or h == "localhost":
			host = socket.gethostname()
		elif self.isHostname(h):
			host = h
		else:
			host = self.ipToHost(h)
			if host == None:
				raise Exception("Couldn't verify host format!")		
		if p > 0 and p < 65535:
			port = p
				
		print "Connecting to " + str(host) + " on port " + str(port)
		s.connect((host, port))
	
	
	def sendData(self, data):
		
		global s
				
		print "Sending data"
		s.sendall(data)

	
	def readData(self):
	
		global s
		
		data = s.recv(2048).strip()
		print "RECEIVED = ", repr(data)
	
		return data

		
	def closeConnection(self):
		
		print "Closing connection"
		s.close()
		
		
	def isIPv4(self, myStr):
		
		if re.search("(((\d{1,3}\.){3})((\d{1,3})))", myStr) == None:
			return False
		return True
		
	def isIPv6(self, myStr):
		
		if re.search("([A-Fa-f0-9]{1,4}::?){1,7}[A-Fa-f0-9]{1,4}", myStr) == None:
			return False
		return True
		
	def isHostname(self, myStr):
		
		if re.search("(((\d{1,3}\.){3})((\d{1,3})))|([A-Fa-f0-9]{1,4}::?){1,7}[A-Fa-f0-9]{1,4}", myStr) == None:
			return True
		else:
			return False
			
			
	def ipToHost(self, ip):
				
		if self.isIPv4(ip) or self.isIPv6(ip):	
			trip = socket.gethostbyaddr(ip)
			return trip[0]
		else:
			print "Could'nt verify format of " + ip
			return None
