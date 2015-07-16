import socket, sys, time, select


class Server:
	
	HOST = ""; PORT = 10001; 
	CNT = 0; BRK = False; DATA = ""; SIZE = ""; TMP = ""
		
	def __init__(self): pass
	

	# Checks that the connection is to the requested ip.
	# The ip is passed in chk
	def chkConnection(self, s, chk):

		print "Checking connection..."
		print "Accepting connections..."
		conn, addr = s.accept()
		print 'Connected by', addr

		print "addr = ", addr[0]
		print "chk = ", chk

		if addr[0] == chk:
			print "Returned connection..."
			return conn
		else:
			print "Returned none..."
			return None

	
	# makeConnection is passed the socket, 's' as a parameter.
	# It creates and returns the connection
	def makeConnection(self, s):

		print "Accepting connections..."
		conn, addr = s.accept()
		print 'Connected by', addr

		return (conn, addr)


	# Gets the data transfer size in bytes, of the data that is expected from the client
	def getTsfSize(self, conn):

		# Get transmission self.SIZE							
		while True:							
			self.TMP = conn.recv(10)
				
			for c in self.TMP:				
				if c == "~": 
					self.BRK = True
					break				
				self.SIZE += c
				self.CNT += 1
				
			if self.BRK: break
			
		print "SIZE: " + self.SIZE
		self.DATA = self.TMP[self.CNT + 1 : ]

		return self.SIZE

	
	# Gets the data from the client
	def getData(self, conn):

		# Get self.DATA
		while True:
			self.DATA += conn.recv(int(self.SIZE))
			if len(self.DATA) == int(self.SIZE): 
				self.BRK = True
				break								
			elif not self.TMP:	
				self.BRK = True
				break

		return self.DATA
					
	# Creates and opens the network socket
	def serverConnection(self):

		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		s.bind((self.HOST, self.PORT))
		s.listen(5)

		return s


	# Does everything for you...
	# Creates socket and connection. Gets size and data.
	def startServer(self):
		
		
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		s.bind((self.HOST, self.PORT))
		s.listen(5)
		
		while True:

			conn = self.makeConnection(s)

			self.getTsfSize(conn)
		
			# Reset loop break value
			self.BRK = False
			
			self.getData(conn)

			if self.BRK: break
			
		print "DATA: " + self.DATA
		conn.close()						
		return self.DATA
