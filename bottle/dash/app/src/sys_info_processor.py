from server import Server
from ..settings.mysql_connector import MySqlConnector
import json, re, subprocess, datetime, time

# Decodes json received by server into useable format 
# Wrote my own json processor because the Python and Java json processors 
#		didn't want to work together

class SysInfoProcessor:
	
	data = ""
	os = "Operating_System_Info"
	cpu = "CPU_Info"
	user = "User_Info"
	net = "Network_Info"
	pattern = "(((\d{1,3}\.){3})((\d{1,3})))|(([A-Fa-f0-9]{1,4}::?){1,7}[A-Fa-f0-9]{1,4})|\w"
	
	def __init__(self): pass


	def getData(self, ip):
		
		global data

		s = Server()
		c = s.serverConnection()
		conn = s.chkConnection(c, ip)


		if conn == None:
			print "Couldn't connect..."
		else:
			print "Made connection..."

			size = s.getTsfSize(conn)
			self.data = s.getData(conn)

			#print "SysInfo Data: ", self.data
			conn.close()

		return conn


	def infoToList(self, host):

		m = MySqlConnector()

		q = ('SELECT id FROM servers WHERE ip="%s"' % host)
		#print "q = ", q
		r = m.myQuery("localhost", "root", "a", "dashboard", q)
		#print "r = ", r
		myID = r[0][0]
		#print "myID = ", myID
		query = ('SELECT name,value FROM sys_monitor WHERE servers_id=%i' % myID)
		#print "query = ", query
		result = m.myQuery("localhost", "root", "a", "dashboard", query)

		return result 
		

	def getTS(self, host):

		m = MySqlConnector()

		q = ('SELECT id FROM servers WHERE ip="%s"' % host)
		print "q = ", q
		r = m.myQuery("localhost", "root", "a", "dashboard", q)
		print "r = ", r
		myID = r[0][0]
		print "myID = ", myID
		query = ('SELECT ts FROM sys_monitor WHERE servers_id=%i' % myID)
		print "query = ", query
		result = m.myQuery("localhost", "root", "a", "dashboard", query)
		print "result = ", result 
	
		if result:
			result = result[0]
			print "result = ", result 
			result = result[0]
		else:
			ts = time.time()
			result = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %h:%M:%s') 
			print "current result = ", result

		return result 



	def decodeJson(self):
			
		global os
		global cpu
		global user
		global net
		global pattern
		global data

		lod = []
		osDict = []
		cpuDict = []
		userDict = []
		netDict = []
		
		myStr = re.sub("\[|\]|\{|\}|\"|\"", "", self.data)
		
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
