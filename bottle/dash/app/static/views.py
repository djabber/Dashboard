import json, os, sys, socket
from bottle import Bottle, route, run, template, get, post, request, static_file, error
from src.sys_info_processor import SysInfoProcessor
from settings.settings_processor import SettingsProcessor
from src.server import Server
from settings.mysql_connector import MySqlConnector
from src.my_ping import MyPing
from collections import deque


app = Bottle()

# Serves static css files for python Bottle
@route('/css/<filepath:path>')
def server_static_css(filepath = None):

    css_paths = [
        'bootstrap.css',
        'bootstrap.min.css',
        'sb-admin.css',
        'myStyle.css',
    ]
    #print filepath

    if filepath is not None and filepath in css_paths:
        return static_file(filename= filepath, root='/home/dd27/Dropbox/Development/Projects/Dashboard/bottle/dash/app/static/css/')


# Serves static js files for python Bottle
@route('/js/<filepath:path>')
def server_static_js(filepath = None):

    js_paths = {
        'jquery' : 'jquery-1.10.2.js',
        'bootstrap': 'bootstrap.js',
        'bootstrap.min' : 'bootstrap.min.js',
    }

    if filepath is not None and filepath in js_paths.keys():
        return static_file(filename= js_paths[filepath], root='./js/')


# Serves static image files for python Bottle
@route('/images/<filepath:path>')
def server_static_images(filepath = None):

    if filepath is not None:
        return static_file(filename= filepath, root='/home/dd27/Dropbox/Development/Projects/Dashboard/bottle/dash/app/static/images/')
        
# Routes traffic to index page
@route('/')
@route('/index')
@route('/myapp/index')
def home():

	# Builds a list of servers by calling local function getServerList and passes it an empty list
	myList = []
	getServerList(myList)

	# Builds a list of printers by calling local function indexPrinterHelper 
	aList = indexPrinterHelper()

	# Returns the index template, list of servers, and list of printers to python Bottle
	output = template('app/static/index', servers=myList, printers=aList)

	return output


# Builds the server list
def getServerList(myList):

	db = MySqlConnector()
	p = MyPing()
	s = SettingsProcessor()
	
	cnt = 1
	while cnt < 8:
		queue = []
	
		# Queries mysql for a list of server names and ips
		query = ("SELECT name,ip FROM servers WHERE id=%i" % cnt)
		result = db.myQuery("localhost", "root", "a", "dashboard", query)

		# Pings each server for status
		pingResult = p.ping(result[0][1])

		# Updates mysql servers table with latest status
		update = ("UPDATE servers SET status=%s WHERE id=%i" % (pingResult, cnt))
		db.myUpdate("localhost", "root", "a", "dashboard", update) 

		# queue is a list of the server name, ip, and status
		queue.append(result[0][0])
		queue.append(result[0][1])
		queue.append(pingResult)

		# myList is a list of queue items
		myList.append(queue)	

		cnt += 1

	return myList

# Calls the SysInfoProcessor class to get the latest system information
def getInfo(host):
	
	s = SysInfoProcessor()
	
	return s.decodeJson()
	
# Routes requests to sys_info template and passes current system information
#		from the local function getInfo
@route("/sys_info/<host>") 
def sysInfo(host = "localhost"):
	
	info = getInfo(host)
	output = template('app/static/sys_info', info=info)
	
	return output
	

# Routes requests to prnt_info template and passes it the hostname or ip 
@route("/prnt_info/<host>") 
def sysInfo(host = "localhost"):
	
	output = template('app/static/prnt_info', host=host)
	
	return output


# Routes request to servers template and passes the server list
#		from the local getServerList function
@route("/servers")
def servers():
	
	myList = []
	getServerList(myList)
	
	output = template('app/static/servers', servers=myList)
	
	return output


# Routes request to the printers template and passes the printer list
# 		from the local indexPrinterHelper function
@route("/printers")
def printers():
	
	aList = indexPrinterHelper()

	output = template('app/static/printers', printers=aList)
	
	return output


# Builds the list of printers
def indexPrinterHelper():
	
	myList = []
	db = MySqlConnector()
	p = MyPing()
	s = SettingsProcessor()

	cnt = 1
	while cnt < 6:
		queue = []

		# Queries mysql for the printer name and ip
		query = ("SELECT name,ip FROM printers WHERE id=%i" % cnt)
		result = db.myQuery("localhost", "root", "a", "dashboard", query)

		# Pings each printer for status
		pingResult = p.ping(result[0][1])

		# Updates mysql with current status
		update = ("UPDATE printers SET status=%s WHERE id=%i" % (pingResult, cnt))
		db.myUpdate("localhost", "root", "a", "dashboard", update)

		# queue contains the name, ip, and current status of the printer
		queue.append(result[0][0])
		queue.append(result[0][1])
		queue.append(pingResult)

		# myList is a list of queue items
		myList.append(queue)

		cnt += 1
	
	return myList


#@error(404)
#def error404(error):
#    return template('app/static/index')


run(host='localhost', port=8082, debug=True)

if __name__ == '__main__':
	app.run
