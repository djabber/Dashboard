import os, platform, socket, commands, sys
from IPy import IP
from pif import get_public_ip
from pif.base import BaseIPChecker, registry


class NetInfo():
	
	myList = []
	
	def __init__(self): pass
		
	def getNetInfo(self):
		myStr = ' Network: '
		self.myList.append(myStr)
		self.getFQDN()
		self.getIP()
		return self.myList

	def getFQDN(self):
		myStr = ' FQDN: '
		myStr += socket.getfqdn()
		self.myList.append(myStr)
	
	def getIP(self):
		myStr = ' IP: '
		ip = get_public_ip(socket.getfqdn())
		myStr += str(ip)
		self.myList.append(myStr)
