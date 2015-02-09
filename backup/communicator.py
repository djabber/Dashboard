import socket, re


class Communicator:

	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	host = socket.gethostname()
	port = 0
			
			
	def __init__(self): pass
	
		
	def startCommunicator(self, h, p):
		
		if self.isHostname(h):
			host = h
		else:
			host = self.ipToHost(h)
			if host == None:
				raise Exception("Couldn't verify host format!")		
		if p > 0 and p < 65535:
			port = p
			
		s.bind((host, port))       
		s.listen(5)
		c, addr = s.accept()   
	
		print "Connecting to host"
		s.connect((host, port))
		
		
		
	def sendData(self, data):
		
		print "Sending data"
		s.sendall('Hello, world')

	
	def readData(self):
		
		return c.recv(2048).strip()

		
	def closeConnection(self):
		
		print "Closing connection"
		s.close()
		c.close()


	def isIPv4(self, myStr):
		
		if re.search("(((\d{1,3}\.){3})((\d{1,3})))", myStr) == None:
			return False
		return True
		
		
	def isIPv6(self, myStr):
		
		if re.search("([A-Fa-f0-9]{1,4}::?){1,7}[A-Fa-f0-9]{1,4}", myStr) == None:
			return False
		return True
		
		
	def isHostname(self, myStr):
		
		if myStr == "localhost":
			return False
		elif re.search("(((\d{1,3}\.){3})((\d{1,3})))|([A-Fa-f0-9]{1,4}::?){1,7}[A-Fa-f0-9]{1,4}", myStr) == None:
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
