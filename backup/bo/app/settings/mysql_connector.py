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

	def myInsert(self, hst, usr, passwd, db, insert):
		
		print "Connecting to database..."
		myDB = mysql.connector.connect(host=hst, user=usr, password=passwd, database=db)
		cur = myDB.cursor()
		print "Connected to database..."
		
		print "Executing insert..."
		cur.execute(insert)
		print "Committing changes..."
		myDB.commit()
		print "Done in insert..."

		cur.close()
		myDB.close()
