from get_sys_info import SysInfo
from get_user_info import UserInfo
from get_net_info import NetInfo
import os, platform


class GetAllInfo():
	
	myList = []
	
	def __init__(self): pass
		
	def getAllInfo(self):
		sys = SysInfo()
		user = UserInfo()
		net = NetInfo()
		self.myList = (sys.getSysInfo()) + (user.getUserInfo()) + (net.getNetInfo())
		return self.myList
