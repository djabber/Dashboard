from server import Server
from client import Client
import json, re, subprocess


class SysInfoProcessor:
	
	data = ""
	os = "Operating_System_Info"
	cpu = "CPU_Info"
	user = "User_Info"
	net = "Network_Info"
	pattern = "(((\d{1,3}\.){3})((\d{1,3})))|(([A-Fa-f0-9]{1,4}::?){1,7}[A-Fa-f0-9]{1,4})|\w"
	
	def __init__(self): 
		
		global data

		s = Server()
		data = s.startServer()
		#c = Client()
		#c.startClient("localhost", 10000)

	def decodeJson(self):
			
		global os
		global cpu
		global user
		global net
		global pattern

		lod = []
		osDict = []
		cpuDict = []
		userDict = []
		netDict = []
		
		myStr = re.sub("\[|\]|\{|\}|\"|\"", "", data)
		
		cpuLoc = myStr.find(self.cpu)
		userLoc = myStr.find(self.user)
		netLoc = myStr.find(self.net)
		
		osStr = myStr[ (len(self.os) + 1) : (cpuLoc - 1) ]
		cpuStr = myStr[ (cpuLoc + len(self.cpu) + 1) : (userLoc - 1)]
		userStr = myStr[ (userLoc + len(self.user) + 1) : (netLoc - 1) ]
		netStr = myStr[ (netLoc + len(self.net) + 1) : ]
		
		osList = osStr.split(",")
		cpuList = cpuStr.split(",")
		userList = userStr.split(",")
		netList = netStr.split(",")
		
		osDict.append( (re.sub("\_", " ", self.os), "") )
		cpuDict.append( (re.sub("\_", " ", self.cpu), "") )
		userDict.append( (re.sub("\_", " ", self.user), "") )
		netDict.append( (re.sub("\_", " ", self.net), "") )
		
		for item in osList:
			tmpList = item.split(":")
			tmpList[0] = re.sub("\_", " ", tmpList[0])
			tmpList[1] = re.sub("\_", " ", tmpList[1])
			osDict.append( (tmpList[0], tmpList[1]) )
		
		for item in cpuList:
			tmpList = item.split(":")
			tmpList[0] = re.sub("\_", " ", tmpList[0])
			tmpList[1] = re.sub("\_", " ", tmpList[1])
			cpuDict.append( (tmpList[0], tmpList[1]) )
		
		for item in userList:
			tmpList = item.split(":")
			tmpList[0] = re.sub("\_", " ", tmpList[0])
			tmpList[1] = re.sub("\_", " ", tmpList[1])
			userDict.append( (tmpList[0], tmpList[1]) )
			
		for item in netList:
			tmpList = item.split(":")
			tmpList[0] = re.sub("\_", " ", tmpList[0])
			tmpList[1] = re.sub("\_", " ", tmpList[1])
			netDict.append( (tmpList[0], tmpList[1]) )
			
		lod.append(osDict)
		lod.append(cpuDict)
		lod.append(userDict)
		lod.append(netDict)	
		
		return lod
