# Shakespoke
This package includes two main versions, and a test runner.
 - Shakespokev2 is run by supplying command line arguments at runtime.
 - Shakespokev3 is run with no arguments, and provides step by step prompts to read in the parameters.
 - Shakespokev2Test is run with no arguments, and takes in a batch of parameters from a JSON file.

## Installation
 - Extract the .class files and test_poke.json file to a working directory.
 - Add the external library to your java runtime classpath: gson-2.8.6.jar.
 - If using the class files, the Building step below is not needed.

## Building
 - **Prerequisite:** *Use OpenJDK 14 or newer.*
 - Add the external library to your java build classpath: gson-2.8.6.jar.
 - Compile the java source files in order:
  - Shakespokev2.java
  - Shakespokev2Test.java
  - Shakespokev3.java

## Running
 - **Prerequisite:** *Use OpenJDK JRE 14 or newer.*
 - Ensure the JRE PATH is correctly set up.
 - Ensure the library gson-2.8.6.jar is included in the Java runtime classpath.
 - Navigate to the working directory.
  - To run Shakespokev2:
    `java Shakespokev2 <pokemon_name> <version_name> <language_name>`
  - Alternative example with explicit classpath:
  - `java -cp .:<path_to_gson-2.8.6.jar> Shakespokev3` (applies to running Shakespokev2, Shakespokev2Test, Shakespokev3
  - Example:
    `java Shakespokev2 charizard ruby shake`
    `java Shakespokev2 ditto silver en`
  - To run Shakespokev3:
    `java Shakespokev3`
  - Any arguments will be ignored.
  - To run Shakespokev2Test:
  - Place a populated test_poke.json file in the working directory.
  - Example:
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
  - The file MUST be named test_poke.json and follow the valid JSON format, or the test will fail with errors.
  - Then run the command:
    `java Shakespokev2Test`
  - The output will be written to test_output.txt in the working directory.
  
## Docker
 - A Dockerfile is available to create a ready to run image.
 - This process does not require the "Installation" and "Building" tasks.
 - **Prerequisite:** *Have a Docker host ready to use.*
 - **Prerequisite:** *Pull the openjdk image to the host.*
 - Extract the file shakespoke_docker.zip to the Docker working directory.
 - The current working directory should show a folder named shakespoke_docker (containing the class files) and the Dockerfile.
 - Use the suggested commands to build the image, start and run the container:
  - Build the image
  `docker build -t shakespoke .`
  - Start the container
  `docker run -t -d --name pokeshake shakespoke`
  - Run commands on the container as per the section "Running" above. Example:
  `docker container exec -it pokeshake java Shakespokev2 charizard`
  
## Fair Use
The PokeAPI resource declares a fair use policy: Free access is intended for personal and development use.
It is not recommended to make multiple requests of large datasets.

The Shakespeare Translation resource offers a rate limited, free access tier.
When the "shake" language parameter is used, it is recommended to limit the request to 5 per hour, 60 per day.
