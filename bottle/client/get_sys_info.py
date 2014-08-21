import os, platform


class SysInfo():
	
	myList = []
	
	def __init__(self): pass
		
	def getSysInfo(self):
		myStr = ' System: '
		self.myList.append(myStr)
		platform = self.getPlatform()
		self.getRelease()
		self.getVersion()
		self.getMachine()
		self.getCPU()
		self.getAlias()
		#self.getMoreInfo(platform)
		return self.myList

	def getPlatform(self):
		myStr = ' Platform: '
		myStr += platform.system()
		self.myList.append(myStr)
		
	def getRelease(self):
		myStr = ' Release: '
		myStr += platform.release()
		self.myList.append(myStr)
		
	def getVersion(self):
		myStr = ' Version: '
		myStr += platform.release()
		self.myList.append(myStr)

	def getMachine(self):
		myStr = ' Architecture: '
		myStr += platform.machine()
		self.myList.append(myStr)
		
	def getCPU(self):
		myStr = ' CPU: '
		myStr += platform.processor()
		self.myList.append(myStr)
		
	def getAlias(self):
		myStr = ' Version: '
		myStr += str(platform.system_alias(platform.system(), platform.release(), platform.version()))
		self.myList.append(myStr)
		
	def getMoreInfo(platform, self):
		test = str(platform).lower()
		if test == 'windows':
			myStr = ' Windows Info: '
			myStr += str(platform.win32_ver())
			self.myList.append(myStr)
		elif test == 'mac' or test == 'apple':
			myStr = ' Mac Info: '
			myStr += str(platform.mac_ver())
			self.myList.append(myStr)
		else:
			myStr = ' Linux/Unix Info: '
			#myStr += str(platform.linux_distribution())
			self.myList.append(myStr)
