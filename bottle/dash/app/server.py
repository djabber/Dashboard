#!/usr/bin/python           # This is server.py file

import socket               # Import socket module

s = socket.socket()         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 10000                # Reserve a port for your service.
s.bind((host, port))        # Bind to the port

print 'Waiting for client connection...'
s.listen(5)                 # Now wait for client connection.
while True:
	c, addr = s.accept()     # Establish connection with client.
	print 'Connection established...'
	data = c.recv(1024).strip()
	print  "Client message: ", data
	c.sendall(data.upper())
	c.close()                # Close the connection
