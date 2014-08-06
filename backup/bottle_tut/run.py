from bottle import Bottle, route, run, template, get, post, request

app = Bottle()

@get('/login')
def login():
	return  '''
        <form action="/login" method="post">
            Username: <input name="username" type="text" />
            Password: <input name="password" type="password" />
            <input value="Login" type="submit" />
        </form>
    '''

@post('/login') # or @route('/login', method='POST')
def do_login():
    username = request.forms.get('username')
    password = request.forms.get('password')
    if check_login(username, password):
        return "<p>Your login information was correct.</p>"
    else:
        return "<p>Login failed.</p>"


'''
@route('/hello')
def hello():
	return "Hello World!"

@route('/')
@route('/hello/<name>')
def hello(name='Stranger'):
    return template('Hello {{name}}, how are you?', name=name)
'''

run(host='localhost', port=8081, debug=True)
