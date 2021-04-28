# Shakespoke
This package includes three main versions, and a test runner.
 - Shakespokev2 is run by supplying command line arguments at runtime.
 - Shakespokev3 is run with no arguments, and provides step by step prompts to read in the parameters.
 - Shakespokev2Test is run with no arguments, and takes in a batch of parameters from a JSON file.
 - Shakespokehttp starts an HTTP listener on a given port, and accepts GET requests of the form http://<host_name>:<port>/pokemon/<pokemon_name>?lang=<lang>.

## Installation
 1. Extract the .class files and test_poke.json file to a working directory.
 2. Add the external library to your java runtime CLASSPATH: gson-2.8.6.jar.
 3. If using the .class files, the Building step below is not needed.

## Building
 1. **Prerequisite:** *Use OpenJDK 14 or newer.*
 2. Add the external library to your java build CLASSPATH: gson-2.8.6.jar.
 3. Compile the java source files in order:
  - Shakespokev2.java
  - Shakespokev2Test.java
  - Shakespokev3.java
  - Shakespokehttp.java

## Running
 1. **Prerequisite:** *Use OpenJDK JRE 14 or newer.*
 2. Ensure the JRE PATH is correctly set up.
 3. Ensure the library gson-2.8.6.jar is included in the Java runtime CLASSPATH.
 4. Navigate to the working directory.
  - To run Shakespokev2:
    `java Shakespokev2 <pokemon_name> <version_name> <language_name>`
  - Alternative example with explicit classpath:
  - `java -cp .:<path_to_gson-2.8.6.jar> Shakespokev3` (applies to running Shakespokev2, Shakespokev2Test, Shakespokev3
  - Example:
    `java Shakespokev2 charizard ruby shake`
    `java Shakespokev2 ditto silver en`
  - To run Shakespokehttp:
    `java Shakespokehttp <port>`
  - Example:
    `java Shakespokehttp 5050`
   - Use a web browser or command line tool to send a GET request to http://<host_name>:<port>/pokemon/<pokemon_name>
   - Example: 
    `http://<host_name>:5050/pokemon/ditto?lang=en`
  - To run Shakespokev3:
    `java Shakespokev3`
  - Any arguments will be ignored.
  - To run Shakespokev2Test:
  1. Place a populated test_poke.json file in the working directory.
  2. Example:
````
{
  "tests": [
	{
	  "name": "wartortle",
	  "version": "sapphire",
	  "language": "en"
    },
    {
	  "name": "blastoise",
	  "version": "silver",
	  "language": "en"
	},
	{
	  "name": "pidgeot",
	  "version": "emerald",
	  "language": "en"
	},
	{
	  "name": "ditto"
	},
	{
	  "name": ""
	}
  ]
}
````
  3. The file MUST be named test_poke.json and follow the valid JSON format, or the test will fail with errors.
  4. Then run the command:
    `java Shakespokev2Test`
  5. The output will be written to test_output.txt in the working directory.
  
## Docker
 - A Dockerfile is available to create a ready to run image.
 - This process does not require the "Installation" and "Building" tasks.
 1. **Prerequisite:** *Have a Docker host ready to use.*
 2. **Prerequisite:** *Pull the openjdk image to the host.*
 3. Extract the file shakespoke_docker.zip to the Docker working directory.
 4. The current working directory should show a folder named shakespoke_docker (containing the class files) and the Dockerfile.
 5. Use the suggested commands to build the image, start and run the container:
  6. Build the image
  `docker build -t shakespoke .`
  7. Start the container
  `docker run -t -d --name pokeshake shakespoke`
  `docker container start pokeshake`
  8. Run commands on the container as per the section "Running" above. Example:
  `docker container exec -it pokeshake java Shakespokev2 charizard`
  - To run Shakespokehttp:
  1. `docker container exec -it pokeshake java Shakespokehttp 5050`
  2. Open another command line
  3. On the new command line:
  `docker container exec -it pokeshake curl http://localhost:5050/pokemon/charizard?lang=en`
  - Alternative with port mapping:
  `docker run -t -d -p 5050:6060 --name pokeshakehttp shakespoke`
  - Now use the command line or web browser to access http://localhost:6060/pokemon/charizard?lang=en
  
## Fair Use
The PokeAPI resource declares a fair use policy: Free access is intended for personal and development use.
It is not recommended to make multiple requests of large datasets.

The Shakespeare Translation resource offers a rate limited, free access tier.
When the "shake" language parameter is used, it is recommended to limit the request to 5 per hour, 60 per day.
