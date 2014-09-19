import socket, sys, time, select


class Server:
	
		
	def __init__(self): pass
	
	
	def startServer(self):
		
		end = ""; data = ""; size = ""
		HOST = ""; PORT = 10000

		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		s.bind((HOST, PORT))
		s.listen(5)
		conn, addr = s.accept()
		print 'Connected by', addr
		
		while True:							
			while True:							# Get transmission size
				s = conn.recv(1024)					# Receive size until ~ is received
				if s == "~": break				
				size += s
			
			s = ""									# Reset s for reuse
			while True:							# Get data
				s = conn.recv(int(size))			
				if s == "~": break					# Receive data until ~
				elif not s: break							# or end of data stream is received
				else: data += s
			
			s = conn.recv(1024)					# Check for end of data stream
			print "s = " + s
			if s == "~":
				sys.exit(0)		
			
		conn.close()							# Close Connection
		return data
