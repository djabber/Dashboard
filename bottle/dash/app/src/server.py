import socket, sys, time, select

# Class creates a server that listens and accepts connections from clients

class Server:
	
		
	def __init__(self): pass
	
	
	def startServer(self):
		
		HOST = ""; PORT = 10000; 
		cnt = 0; brk = False; data = ""; size = ""; tmp = ""
		
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		s.bind((HOST, PORT))
		s.listen(5)
		
		while True:
			print "Accepting connections..."
			conn, addr = s.accept()
			print 'Connected by', addr
			
			# Get transmission size							
			while True:							
				tmp = conn.recv(10)
				
				for c in tmp:				
					if c == "~": 
						brk = True
						break				
					size += c
					cnt += 1
				
				if brk: break
			
			print "SIZE: " + size
			data = tmp[cnt + 1 : ]
			print "data = " + data
			
			brk = False
			
			# Get data
			while True:
				data += conn.recv(int(size))
				if len(data) == int(size): 
					brk = True
					break								
				elif not tmp:	
					brk = True
					break
					
			if brk: break
			
		print "DATA: " + data
		conn.close()						
		return data
