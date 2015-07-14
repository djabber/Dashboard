from server import Server
from sys_info_processor import SysInfoProcessor
from mysql_connector import MySqlConnector


class ConnectionMgr:

	conn = None
	data = None
	ip = None

	def __init__(self):
	
		while True:
			self.makeConnection()
			self.dataToDB()

	def makeConnection(self):

		s = Server()
		c = s.serverConnection()
		mc = s.makeConnection(c)
		self.conn = mc[0]
		self.ip = mc[1]
		self.ip = self.ip[0]
		size = s.getTsfSize(self.conn)
		self.data = s.getData(self.conn)


	def dataToDB(self):
	
		m = MySqlConnector()
		s = SysInfoProcessor()
		d = s.decodeJsonData(self.data)
	
		print "d = ", d
		print ""

		idQuery = ('SELECT id FROM servers WHERE ip="%s"' % self.ip)
		print "idQuery = ", idQuery
		myID = m.myQuery("localhost", "root", "a", "dashboard", idQuery) 
		myID = myID[0][0]
		print "myID = ", myID 

		delete = ('DELETE FROM sys_monitor WHERE servers_id=%i' % myID)
		m.myDelete("localhost", "root", "a", "dashboard", delete)

		for alist in d:

			print "alist = ", alist
			
			for items in alist:
					
#				print "   items = ", items

#				if items[1] != '':
					print "      items[0] = ", items[0]
					print "      items[1] = ", items[1]

					insert = ('INSERT INTO sys_monitor (name, value, servers_id) VALUES ("%s", "%s", %i)' % (items[0], items[1], myID))
					print "insert = ", insert
					insertResult = m.myInsert("localhost", "root", "a", "dashboard", insert)
					#print "insertResult = ", insertResult


	def closeConnection(self):

		conn.close()
		

