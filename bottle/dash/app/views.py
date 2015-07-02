import json, os, sys, socket
from bottle import Bottle, route, run, template, get, post, request, static_file, error
from src.sys_info_processor import SysInfoProcessor
from settings.settings_processor import SettingsProcessor
from src.server import Server
from settings.mysql_connector import MySqlConnector
from src.my_ping import MyPing
from collections import deque


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
        return static_file(filename= filepath, root='/home/dd27/Dropbox/Development/Projects/Dashboard/bottle/dash/app/static/images/')
        

@route('/')
@route('/index')
@route('/myapp/index')
def home():

	myList = []
	db = MySqlConnector()
	p = MyPing()
	s = SettingsProcessor()
	#serverDict = s.getSettings('app/settings/servers.yml')
	ipDict = s.getSettings('app/settings/ips.yml')

	# Convert dictionaries to list of tuples
	#servers = serverDict.items()
	ips = ipDict.items()
	
	cnt = 1
	while cnt < 8:
		queue = []
		query = ("SELECT name,ip FROM servers WHERE id=%i" % cnt)
		#query = ("SELECT name,ip FROM servers")
		result = db.myQuery("localhost", "root", "a", "dashboard", query)
		#print "%i: %s" % (cnt, name[0])
		#print "%s, %s" % (result[0][0], result[0][1])
		pingResult = p.ping(result[0][1])
		update = ("UPDATE servers SET status=%s WHERE id=%i" % (pingResult, cnt))
		db.myUpdate("localhost", "root", "a", "dashboard", update) 
		queue.append(result[0][0])
		queue.append(result[0][1])
		queue.append(pingResult)
		myList.append(queue)	

		cnt += 1

	for item in myList:
		print "%s" % item[0]
		print "%s" % item[1]
		print "%s" % item[2]
	#for server in servers:
	#	print "Server = %s" % server[0]
	#	queue.append(server[0])
	#	queue.append(server[1])
	
	#	ip = ips.pop()
	#	queue.append(ip[1])
	#	result = p.ping(ip[1])
	#	queue.append(result)

	output = template('app/static/index', servers=myList) #, statusList=statusList)

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
