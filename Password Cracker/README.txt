Instructions for using this application:

	1. In the terminal, run "python generate_password.py"
	2. Use any username and password of your choice up to 6 characters
	3. In the terminal, run "python crack_password.py"
	4. Enter the character set used for the password and wait for the output

The program relies on write and read access to the local file system to pass the hashed
password from "generate_password" to "crack_password".