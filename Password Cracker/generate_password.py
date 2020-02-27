# generate_password.py
# The purpose of this program is to encode a password using sha256.  The password will be stored in the local
# file system in pwd.text.
# 
# Riley Hillegas
# 02/26/2020

from Crypto.Hash import SHA256
from Crypto.Random import get_random_bytes
import itertools
import base64
import ast


def main():
	# Colled user's data
	username = input("Username: ")
	password = input("Password: ")
	
	# hash the user's password
	hasher = SHA256.new()
	salt = get_random_bytes(4)
	hasher.update(password.encode() + salt)
	hash = hasher.digest()
	
	# Store the user's data in a text file
	data = "['{}', {}, {}]".format(username, salt, hash)
	
	f = open("pwd.txt", 'w')
	f.write(data)
	f.close()
	
	print("The login data has been saved to 'pwd.txt'.")
	
	
if __name__ == "__main__":
	main()
