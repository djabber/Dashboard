from server import Server
import json, re


class SysInfoProcessor:
	
	data = ""
	os = "Operating_System_Info"
	cpu = "CPU_Info"
	user = "User_Info"
	net = "Network_Info"
	pattern = "(((\d{1,3}\.){3})((\d{1,3})))|(([A-Fa-f0-9]{1,4}::?){1,7}[A-Fa-f0-9]{1,4})|\w"
	
	def __init__(self): 
		s = Server()
		global data
		data = s.startServer()

	def decodeJson(self):
			
		global os
		global cpu
		global user
		global net
		global pattern

		lol = []
		
		myStr = re.sub("\[|\]|\{|\}|\"|\"|\s", "", data)
		
		cpuLoc = myStr.find(self.cpu)
		userLoc = myStr.find(self.user)
		netLoc = myStr.find(self.net)
		
		osStr = myStr[ (len(self.os) + 1) : (cpuLoc - 1) ]
		cpuStr = myStr[ (cpuLoc + len(self.cpu) + 1) : (userLoc - 1)]
		userStr = myStr[ (userLoc + len(self.user) + 1) : (netLoc - 1) ]
		netStr = myStr[ (netLoc + len(self.net) + 1) : ]

		lol.append(osStr.split(","))
		lol.append(cpuStr.split(","))
		lol.append(userStr.split(","))
		lol.append(netStr.split(","))	
					
		return lol
		
	def getHeadings(self):
			
		aList = []	
		aList.append(re.sub("\_", " ", self.os))
		aList.append(re.sub("\_", " ", self.cpu))	
		aList.append(re.sub("\_", " ", self.user))	
		aList.append(re.sub("\_", " ", self.net))	
		
		return aList
