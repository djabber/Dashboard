from bottle import Bottle, route, run, template, get, post, request
#from server_manager import ServerManager
from server_status import ServerStatus 
from app import app

app = Bottle()

@get("/status")
def status():
	return  '''
		<form action="/status" method="post">
			<h3>Ping Server</h3>
			<input value="Refresh" type="submit" />
		</form>
		'''


@post('/status') 
def get_status():
	
	s = ServerStatus()
	p = s.ping('147.26.195.210')
	
	if p == 0:
		return "<p>ip is up.</p>"
	else:
		return "<p>ip is down.</p>"
		

run(host='localhost', port=8081, debug=True)

if __name__ == '__main__':
	app.run
