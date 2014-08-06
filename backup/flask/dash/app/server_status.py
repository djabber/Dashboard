import os


class ServerStatus:
	
	#def __init__():
		
		
	def ping(host):
		response = os.system("ping -c 1 " + host)
		print("response =", response)
		return response
