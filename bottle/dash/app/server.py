import socket


class Server:
	
	def __init__(self): pass
   
	def startServer(self):
		
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

		host = socket.gethostname()
		port = 10000               
		s.bind((host, port))       

		print 'Waiting for client connection...'
		s.listen(5)
		
		c, addr = s.accept()   
		print 'Connection established...'
		data = c.recv(2048).strip()
		c.close()
		return data

	
