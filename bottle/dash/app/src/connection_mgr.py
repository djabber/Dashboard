from sys_info_processor import SysInfoProcessor
from server import Server


class ConnectionMgr:

	def __init__(self):
		pass

	def myConnectionMgr(self):

		s = Server()
		c = s.serverConnection()
		#conn = s.makeConnection(c)
		chk = s.chkConnection(c, "147.26.195.243")
		
		if chk == None:
			print "Couldn't make connection!"
		else:
			print "Made connection to 147.26.195.243..."
			
			conn = chk
			size = s.getTsfSize(conn)
			data = s.getData(conn)

			print "Connection Manager Data: " + data
			conn.close()



		
		

