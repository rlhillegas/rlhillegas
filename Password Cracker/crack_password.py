# crack_password.py
# The purpose of this program is to brute force a sha256 password.  It allows the user to specify
# the character set they want to use to improve performance.  Passwords will only be tried up to
# 6 characters in length.
# 
# Riley Hillegas
# 02/26/2020

from Crypto.Hash import SHA256
from Crypto.Random import get_random_bytes
import itertools
import base64
import ast
import time


def main():
	# Generate the character sets used for password cracking
	set_1 = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
	set_2 = set_1 + [x.upper() for x in set_1]
	set_3 = set_2 + ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0']
	set_4 = set_3 + ['$', '#', '%', '&', '*', '(', ')']
	hash_sets = {'1': set_1, '2': set_2, '3': set_3, '4': set_4}

	# Get character set desired by user
	print("1) Lowercase only.")
	print("2) Lowercase and uppercase.")
	print("3) Alphanumeric.")
	print("4) Alphanumeric plus symbols.")
	choice = input("Password cracker: ")
	
	while choice not in ('1', '2', '3', '4'):
		choice = input("Invalid choice, try again: ")
		
	# Read the user's data from the text file
	f = open("pwd.txt", 'r')
	data = f.read()
	data = ast.literal_eval(data)
	f.close()
	
	# Crack the password based on the specified character set
	trial_count = 0
	start_time = time.clock()
	for i in range(0, 6):
		for s in itertools.permutations(hash_sets[choice], i):
			trial_count += 1
			test_pwd = ''.join(s)
			salt = data[1]
			test_key = test_pwd.encode() + salt
			hash = SHA256.new()
			hash.update(test_key)
			
			if hash.digest() == data[2]:
				stop_time = time.clock()
				seconds = stop_time - start_time
				print("\nThe password is: {}.".format(test_pwd))
				print("Found in {} trials and {} seconds.".format(trial_count, seconds))
				return
				
	print("No password could be found.")
	
	
if __name__ == "__main__":
	main()
