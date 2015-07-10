import socket
from ping import Ping

# Uses ping class to make ping requests

class MyPing:
	
	def __init__(self):
		pass
		
	def ping(self, host):
	
		try:
			p = Ping(host)
			result = p.do()
	
			# Inverts results so that they make more sense
			if result > 0:
				return 1
			else:
				return 0
				
		except socket.error, e:
			print "Ping Error:", e
