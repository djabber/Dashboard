import os


class ServerStatus:
	
	def __init__(self): pass
		
	def ping(self, host):
		response = os.system("ping -c 1 " + host)
		print "response = " + str(response) + "\n"
		return response
