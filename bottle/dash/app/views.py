from bottle import Bottle, route, run, template, get, post, request, static_file
from server import Server
from sys_info_processor import SysInfoProcessor
import json

app = Bottle()

    

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
def favorite():
    return template('app/static/index')

def getInfo():
	
	s = SysInfoProcessor()
	
	return s.decodeJson()
	
@get("/sys_info") 
def sysInfo():
	
	info = getInfo()
	output = template('app/static/sys_info', info=info)
	
	return output
	

run(host='localhost', port=8082, debug=True)

if __name__ == '__main__':
	app.run
