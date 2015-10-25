#  By Kunal Kulkarni
#  Graduate Student in Computer Science at The Ohio State University 2014 - 2016
#  kulkarni.120@osu.edu
#  Implemented Feature Extraction of Reuters Data Set

import nltk
import bs4
from bs4 import BeautifulSoup
import re
import glob
import math
import os,sys
import shutil

# Cleares the entire dataset directory if present
shutil.rmtree('mydataset',ignore_errors=True)
os.mkdir("mydataset",0755)
class_list = []
count =0
i=0
j=0
k=0

#read and process all the files in directory
path='/home/0/srini/WWW/674/public/reuters/*.sgm'
files=glob.glob(path)
for af in files:
	f=open(af,'r')
	print af
	soup = BeautifulSoup(open(af))
#soup = BeautifulSoup(open("/home/0/srini/WWW/674/public/reuters/reut2-000.sgm"))
#soup = BeautifulSoup(open("testsample.sgm"))

# Process Every Reuters DataSet
	for ret in soup.find_all('reuters'):

# Consider only Datasets which have Topics
		top = ret.find('topics')
		if (len(top) > 0):
			count+=1
			print top.text

# To Resolve multiple Classes, the most popular Class is considered. For example if a dataset has classes acq,interest,cocoa,copper, acq is chosen to represent as it is# the most popular class among them. The order of elif statements inherently ranks the classes in the order of their popularities
			if "grain" in top.text:
				if "grain" not in class_list:
					class_list.append("grain")
					os.mkdir("mydataset/grain")

				# This checks if the Dataset really has a body content
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					i+=1
					f = open("mydataset/grain/file"+str(i)+".txt",'w')
					bod = ret.dateline.next.next

					# Writes the full body content into the text file stored under the class directory
					f.write(bod)
					f.close()
				
			elif "acq" in top.text:
				if "acq" not in class_list:
					class_list.append("acq")
					os.mkdir("mydataset/acq")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					j+=1
					f = open("mydataset/acq/file"+str(j)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "earn" in top.text:
				if "earn" not in class_list:
					class_list.append("earn")
					os.mkdir("mydataset/earn")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/earn/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "trade" in top.text:
				if "trade" not in class_list:
					class_list.append("trade")
					os.mkdir("mydataset/trade")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					j+=1
					f = open("mydataset/trade/file"+str(j)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "gnp" in top.text:
				if "gnp" not in class_list:
					class_list.append("gnp")
					os.mkdir("mydataset/gnp")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/gnp/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "money" in top.text:
				if "money" not in class_list:
					class_list.append("money")
					os.mkdir("mydataset/money")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/money/file"+str(k)+".txt",'w')

					# Exception handling to catch non-ascii character reads in the body tag
					try:
						bod = ret.dateline.next.next
						f.write(bod)
						f.close()
					except:
						print 'caught you'

			elif "coffee" in top.text:
				if "coffee" not in class_list:
					class_list.append("coffee")
					os.mkdir("mydataset/coffee")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/coffee/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()
	
			elif "copper" in top.text:
				if "copper" not in class_list:
					class_list.append("copper")
					os.mkdir("mydataset/copper")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/copper/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "iron" in top.text:
				if "iron" not in class_list:
					class_list.append("iron")
					os.mkdir("mydataset/iron")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/iron/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "oil" in top.text:
				if "oil" not in class_list:
					class_list.append("oil")
					os.mkdir("mydataset/oil")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					j+=1
					f = open("mydataset/oil/file"+str(j)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "gold" in top.text:
				if "gold" not in class_list:
					class_list.append("gold")
					os.mkdir("mydataset/gold")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/gold/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "crude" in top.text:
				if "crude" not in class_list:
					class_list.append("crude")
					os.mkdir("mydataset/crude")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/crude/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "interest" in top.text:
				if "interest" not in class_list:
					class_list.append("interest")
					os.mkdir("mydataset/interest")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/interest/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			elif "livestock" in top.text:
				if "livestock" not in class_list:
					class_list.append("livestock")
					os.mkdir("mydataset/livestock")
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/livestock/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

			else:
				if top.text not in class_list:
					class_list.append(top.text) 
					os.mkdir("mydataset/"+top.text)
				isdateline= ret.find('dateline')
				if (isdateline is not None):
					k+=1
					f = open("mydataset/"+top.text+"/file"+str(k)+".txt",'w')
					bod = ret.dateline.next.next
					f.write(bod)
					f.close()

print count
print class_list
