from bottle import route, run,template
 
@route('/')
@route('/myapp')
@route('/myapp/')
def myApp():
    return '<b style="color:green">Hello Sandeep .Bottle Framework demonstration route.</b>'
 
 
@route('/myapp/<name>')
def myName(name):
    return template('<b style="color:green">Hello <b style="color:red"> {{name}}</b>. Bottle Framework demonstration route with template string.', name=name)
 
 
@route('/myapp/favorite/')
@route('/myapp/favorite/<item>')
def favorite(item):
    return template('favorite_template', item=item)
 
run(host='localhost', port=9093)
