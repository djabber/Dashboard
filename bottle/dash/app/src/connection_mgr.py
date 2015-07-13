from sys_info_processor import SysInfoProcessor
from server import Server
from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler


class ConnectionMgr:

	conn = None

	def __init__(self):
	
		while True:
			self.makeConnection()


	def makeConnection(self):

		s = Server()
		c = s.serverConnection()
		self.conn = s.makeConnection(c)
		size = s.getTsfSize(self.conn)
		data = s.getData(self.conn)

		print "Data = ", data


	def closeConnection(self):

		conn.close()
		

