#!/usr/bin/python
import mysql.connector

# Class facilitates communication with mysql database
# Note: mysql commands that work on the command line should work here

class MySqlConnector:
	
	
	def __init__(self):
		pass

	# Queries mysql table 
	# 		Parameters: hst = hostname, usr = username, passwd = password, 
	#						db = name of database, stmt = mysql query command
	def myQuery(self, hst, usr, passwd, db, stmt):
		
		myDB = mysql.connector.connect(host=hst, user=usr, password=passwd, database=db)
		cur = myDB.cursor()
		cur.execute(stmt)
		result = cur.fetchall()
		
		cur.close()
		myDB.close()
		
		return result

	# Insert into mysql table 
	# 		Parameters: hst = hostname, usr = username, passwd = password, 
	#						db = name of database, insert = mysql insert command
	def myInsert(self, hst, usr, passwd, db, insert):
		
		myDB = mysql.connector.connect(host=hst, user=usr, password=passwd, database=db)
		cur = myDB.cursor()
		cur.execute(insert)
		myDB.commit()

		cur.close()
		myDB.close()
	
	# Update mysql table 
	# 		Parameters: hst = hostname, usr = username, passwd = password, 
	#						db = name of database, insert = mysql update command
	def myUpdate(self, hst, usr, passwd, db, insert):
		
		myDB = mysql.connector.connect(host=hst, user=usr, password=passwd, database=db)
		cur = myDB.cursor()
		cur.execute(insert)
		myDB.commit()

		cur.close()
		myDB.close()
