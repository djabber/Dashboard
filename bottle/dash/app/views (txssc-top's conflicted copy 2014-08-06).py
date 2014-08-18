from bottle import Bottle, route, run, template, get, post, request, static_file
from server_status import ServerStatus 
from get_sys_info import SysInfo
import json

app = Bottle()
    

@route('/css/<filepath:path>')
def server_static_css(filepath = None):

    css_paths = [
        'bootstrap.css',
        'bootstrap.min.css',
        'sb-admin.css',
    ]
    print filepath

    if filepath is not None and filepath in css_paths:
        return static_file(filename= filepath, root='/home/likewise-open/TXSTATE/dd27/Dropbox/Development/Projects/Dashboard/bottle/dash/app/static/css/')

@route('/js/<filepath:path>')
def server_static_js(filepath = None):

    js_paths = {
        'jquery' : 'jquery-1.10.2.js',
        'bootstrap': 'bootstrap.js',
        'bootstrap.min' : 'bootstrap.min.js',
    }

    if filepath is not None and filepath in js_paths.keys():
        return static_file(filename= js_paths[filepath], root='./js/')

@route('/images/<filepath:path>')
def server_static_images(filepath = None):

    if filepath is not None:
        return static_file( filename = filepath, root='./views/images/')
        

@route('/')
@route('/index/')
@route('/myapp/index/')
def favorite():
    return template('app/static/index')


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
def getInfo():
	
	s = SysInfo()
	i = s.getSysInfo()
	return template(info=i)
	

run(host='localhost', port=8082, debug=True)

if __name__ == '__main__':
	app.run

