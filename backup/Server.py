import socket, sys, time, select


class Server:
	
		
	def __init__(self): pass
	
	
	def startServer(self):
	
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
		s.bind((socket.gethostname(), 10000))
		s.listen(5)
		print "Waiting for connections..."
		c, a = s.accept()  
			
		while True: #(data == '') or (data == None) or (data != "DONE"):
						
			print "Waiting for data..."
			data = c.recv(1024)
			print "RECEIVED: " + data
				
			while(data == "") or (data == None):
				print "Waiting for data..."
				data = c.recv(1024)
				print "RECEIVED: " + data
			
			tokens = data.split(' ',2)            	# Split by space at most once
			size = int(tokens[0])
			cmd = "DONE" #tokens[1]
			
			print "SIZE: " + size + " CMD: " + cmd
			if size > 1024:
				c.send("RESEND")
				c.recv(size)	
			elif (cmd == "QUIT") or (cmd == "DONE"):
				c.send("QUIT")
				break         
			else:
				reply = 'Unknown command'
				print "CMD = " + cmd

			c.send(reply)
			
			#print "Getting data..."
			#data = c.recv(64).strip()
			#if not data: break
			
			#print "DATA: " + data
			#print "Client is ready..."
			#print "Sending request..."
			#c.send("REQUEST\n")
			#print "Request sent..."
			
			#time.sleep(3)
			#c.send("DONE\n")
			#sz = None
			#while sz == "" or sz == None:
			# sz = c.recv(1024)
			# size = int(sz, base=10)
			# data = c.recv(2048).strip()
			# print "DATA = " + data	
		
		print "Closing connections..."
		s.close()
		c.close()
		
		return data

