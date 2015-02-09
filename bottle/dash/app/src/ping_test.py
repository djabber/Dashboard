import urllib, urllib2


class Ping:
	
		
	def __init__(self): pass
	
	
	def ping(self, reference):
		req = urllib2.Request(reference)
		try: urllib2.urlopen(req)
		return True
		except URLError as e:
			print e.reason  
			return False
