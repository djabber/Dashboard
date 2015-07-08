import socket
from ping import Ping

class MyPing:
	
	def __init__(self):
		pass
		
	def ping(self, host):
	
		try:
			p = Ping(host)
			result = p.do()
#			print "Result = %s" % result
		
			if result > 0:
				return 1
			else:
				return 0
				
		except socket.error, e:
			print "Ping Error:", e
