from flask import Flask, render_template, request, jsonify 
from server_status import ServerStatus 
from app import app

@app.route("/")
@app.route("/index")
def index():
	p = ServerStatus
	res = p.ping(request.args['input'])
	return render_templat("index.html", result=res)

if __name__ == '__main__':
	app.run()
