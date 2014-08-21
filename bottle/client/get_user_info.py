import os, getpass


class UserInfo():
	
	myList = []
	
	def __init__(self): pass
		
	def getUserInfo(self):
		myStr = ' User: '
		self.myList.append(myStr)
		self.myList.append(self.getLogin())
		return self.myList

	def getLogin(self):
		myStr = ' Login: '
		myStr += getpass.getuser()
		self.myList.append(myStr)
