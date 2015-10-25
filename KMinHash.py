#  By Kunal Kulkarni
#  Graduate Student in Computer Science at The Ohio State University 2014 - 2016
#  kulkarni.120@osu.edu
#  Implemented K-Min Hashing using Universal Hashing Technique

import re
import glob
import math
import os,sys
import shutil
import random


N =10133 # is a large prime number used in universal hashing
K =32    # K value in kminHash
no_of_docs = 10377
no_of_attr = 10129  # no. of features
fv = open("feature_vectors.txt", "r")
minHashes=[[0 for x in range(K)] for x in range(no_of_docs)]
alist = []
blist = []
k1=0
while k1 < K:

	# generate unique a, b random ints to be used in universal hashing
	a=random.randint(5,900)
	while a in alist:
		a=random.randint(5,900)
	alist.append(a)
	b=random.randint(5,900)
	while b in alist:
		b=random.randint(5,900)
	blist.append(b)
	fv.seek(0,0)
	i1=0
	while i1 < no_of_docs:
		doc_fv = fv.readline().split(",")
		min = 99999
		j1 = 1
		while j1 < no_of_attr+1:
			hash_value = (a*j1+b)%N
		#	print hash_value
			if( hash_value <= no_of_attr and hash_value < min and (int(doc_fv[j1-1]) == 1)):
				min = hash_value	
			j1 = j1+1	

	#	print min

		# found the min hash value, store it in short signature matrix
		minHashes[i1][k1]=min
		i1 = i1+1
	k1 = k1+1

fv.close()
i1=0

# write out the Jaccard similarity values for every pair of dataset reprersented as short signatures to text output file minHash_Jacc_Sim.txt
mh= open("minHash_Jacc_Sim.txt", "w")
while i1 < no_of_docs:
	i2 = i1+1
	while i2 < no_of_docs:
		j2 = 0
		match = 0
		while j2 < K:
			if( minHashes[i1][j2] == minHashes[i2][j2]):
				match = match+1
			j2 = j2+1
		res1 = match/float(K)
	#	print res1
		s3 = str(res1)
		mh.write(s3+',')
		i2 = i2+1
	i1 = i1+1
	mh.write('\n')
mh.close()

# calculate the sum of squared error, read both the true and short signature based similarity matrix stored in text files
sum_of_squared_error = 0.0
ori= open("original_Jacc_Sim.txt", "r")
mh1= open("minHash_Jacc_Sim.txt", "r")
i5=1
while i5 <= no_of_docs:

	doc_ori = ori.readline().split(",")
	doc_mh1 = mh1.readline().split(",")
	i6 = 0
	while i6 < (no_of_docs - i5):
		diff = float(doc_ori[i6]) - float(doc_mh1[i6])
	#	print diff
		sum_of_squared_error = sum_of_squared_error + (diff*diff)
		i6 = i6+1
	i5 = i5+1
print sum_of_squared_error
ori.close()
mh1.close()
