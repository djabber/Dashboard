from app.src.connection_mgr import ConnectionMgr


mgr = ConnectionMgr()

while True:

	myInput = raw_input("Press 'q' to quit.")

	if myInput == 'q':
		mgr.closeConnection()
		break
