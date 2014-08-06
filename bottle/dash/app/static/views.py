from bottle import Bottle, route, run, template, get, post, request
from server_status import ServerStatus 
from get_sys_info import SysInfo
from app import app

app = Bottle()

@route('/')
@route('/index')
@route('/index/') 
def index():
    return template('index')


@get("/status")
def status():
	return  '''
		<form action="/status" method="post">
			<h3>Ping Server</h3>
			<input value="Refresh" type="submit" />
		</form>
		'''


@post("/status") 
def get_status():
	
	s = ServerStatus()
	p = s.ping('147.26.195.210')
	
	if p == 0:
		return "<p>ip is up.</p>"
	else:
		return "<p>ip is down.</p>"
		
		
@get("/sys_info")
def info():
	return  '''
		<form action="/status" method="post">
			<h30>Get System Info</h3>
			<input value="Get" type="submit" />
		</form>
		'''


@post("/sys_info") 
def getInfo():
	
	s = SysInfo()
	i = s.getSysInfo()
	
	return i
	if p == 0:
		return "<p>ip is up.</p>"
	else:
		return "<p>ip is down.</p>"


run(host='localhost', port=8081, debug=True)

if __name__ == '__main__':
	app.run

