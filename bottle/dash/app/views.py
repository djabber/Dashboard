from bottle import Bottle, route, run, template, get, post, request, static_file, error
from server import Server
from sys_info_processor import SysInfoProcessor
import json

app = Bottle()

s = SysInfoProcessor()
s.decodeJson()
    
@route('/css/<filepath:path>')
def server_static_css(filepath = None):

    css_paths = [
        'bootstrap.css',
        'bootstrap.min.css',
        'sb-admin.css',
        'myStyle.css',
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
def home():
	
    return template('app/static/index')

def getInfo(host):
	
	s = SysInfoProcessor()
	
	return s.decodeJson()
	
@route('/hello/<name>')
def greet(name='Stranger'):
    return template('Hello {{name}}, how are you?', name=name)	
		
@route("/sys_info/<host>") 
def sysInfo(host = "localhost"):
	
	info = getInfo(host)
	output = template('app/static/sys_info', info=info)
	
	return output
	

	
@error(404)
def error404(error):
    return template('app/static/index')


run(host='localhost', port=8082, debug=True)

if __name__ == '__main__':
	app.run
