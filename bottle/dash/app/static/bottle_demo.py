import bottle
from bottle import route
from bottle import run
from bottle import template
from bottle import request
from bottle import hook
from bottle import static_file
from bottle import redirect
 
app = bottle.app()
 
@route('/css/<filepath:path>')
def server_static_js(filepath = None):

    css_paths = [
        'bootstrap.css',
        'bootstrap.min.css',
        'sb-admin.css',
    ]

    if filepath is not None and filepath in css_paths:
        return static_file(filename= filepath, root='./css/')

@route('/js/<filepath:path>')
def server_static_js(filepath = None):

    js_paths = {
        'jquery' : 'jquery-1.10.2.js',
        'bootstrap': 'bootstrap.js',
        'bootstrap.min' : 'bootstrap.min.js',
    }

    if filepath is not None and filepath in js_paths.keys():
        return static_file(filename= js_paths[filepath], root='./js/')

@route('/font-awesome/<filepath:path>')
def server_static_js(filepath = None):

    js_paths = {
        'font-awesome' : 'css/font-awesome.css',
		'font-awesome.min' : 'css/font-awesome.min.css',
    }

    if filepath is not None and filepath in js_paths.keys():
        return static_file(filename= js_paths[filepath], root='./js/')
        
@route('/images/<filepath:path>')
def server_static_images(filepath = None):

    if filepath is not None:
        return static_file( filename = filepath, root='./views/images/')


@route('/icons/<filepath:path>')
def server_static_images(filepath = None):

    if filepath is not None:
        return static_file( filename = filepath, root='./views/icons/')       
        
        
@route('/')
@route('/myapp')
@route('/myapp/')
def index():
    return template('index')
 
run(host='localhost', port=9093)
