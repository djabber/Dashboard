#!/usr/bin/python
import mysql.connector


class MySqlConnector:
	
	
	def __init__(self):
		pass
                      
	def myQuery(self, hst, usr, passwd, db, stmt):
		
		myDB = mysql.connector.connect(host=hst, user=usr, password=passwd, database=db)
		cur = myDB.cursor()
		cur.execute(stmt)
		result = cur.fetchall()
		#print "Result = %s" % result
		
		cur.close()
		myDB.close()
		
		return result

	def myInsert(self, hst, usr, passwd, db, result, insert):
		
		myDB = mysql.connector.connect(host=hst, user=usr, password=passwd, database=db)
		cur = myDB.cursor()
		cur.execute(insert)
		myDB.commit()

		cur.close()
		myDB.close()
	
	def myUpdate(self, hst, usr, passwd, db, insert):
		
		myDB = mysql.connector.connect(host=hst, user=usr, password=passwd, database=db)
		cur = myDB.cursor()
		cur.execute(insert)
		myDB.commit()

		cur.close()
		myDB.close()
