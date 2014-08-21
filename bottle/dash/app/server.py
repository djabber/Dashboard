#!/usr/bin/python           # This is server.py file

import socket               # Import socket module

print 'SERVER: Creating socket...'
s = socket.socket()         # Create a socket object

print 'SERVER: Get hostname...'
host = socket.gethostname() # Get local machine name
print 'host = ', host
port = 10000                # Reserve a port for your service.

print 'SERVER: Binding to port...'
s.bind((host, port))        # Bind to the port

print 'SERVER: Waiting for client connection...'
s.listen(5)                 # Now wait for client connection.
while True:
	print 'SERVER: Establishing connection...'
	c, addr = s.accept()     # Establish connection with client.
	
	data = c.recv(1024).strip()
	print  "Client message: ", data
	c.sendall(data.upper())
	
	print 'SERVER: Got connection from', addr
	#c.send('SERVER: Thank you for connecting')
	c.close()                # Close the connection
	print 'SERVER: Closing server connection...'
