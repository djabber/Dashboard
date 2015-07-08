import json, os, sys, socket
from bottle import Bottle, route, run, template, get, post, request, static_file, error
from src.sys_info_processor import SysInfoProcessor
from settings.settings_processor import SettingsProcessor
from src.server import Server
from settings.mysql_connector import MySqlConnector
from src.my_ping import MyPing


app = Bottle()

#s = SysInfoProcessor()
#s.decodeJson()
    
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
        return static_file(filename= filepath, root='/home/dd27/Dropbox/Development/Projects/Dashboard/bottle/dash/app/static/css/')

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
	
	db = MySqlConnector()
	result = db.myQuery("localhost", "root", "a", "dashboard", "select * from servers")

	p = MyPing()
	s = SettingsProcessor()
	serverDict = s.getSettings('app/settings/servers.yml')
	ipDict = s.getSettings('app/settings/ips.yml')
		
	servers = serverDict.items()
	ips = ipDict.items()
	
	for ip in ips:	
	
		result = p.ping(ip[1])
		result = 9
		print "Insert into table %s..." % result
		#db.myInsert("localhost", "root", "a", "dashboard", "INSERT INTO servers (status) VALUES (%(result))")
		
		print "Done inserting into table..."

		if result:
			print "Success! %s" % ip[1]
		else:
			print "Failed... %s" % ip[1]
	
		
	output = template('app/static/index', servers=servers)
	
	return output
 

def getInfo(host):
	
	s = SysInfoProcessor()
	
	return s.decodeJson()
	
		
@route("/sys_info/<host>") 
def sysInfo(host = "localhost"):
	
	info = getInfo(host)
	output = template('app/static/sys_info', info=info)
	
	return output
	
#@error(404)
#def error404(error):
#    return template('app/static/index')


run(host='localhost', port=8082, debug=True)

if __name__ == '__main__':
	app.run
