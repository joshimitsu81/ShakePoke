# Shakespoke
This package includes two main versions, and a test runner.
 - Shakespokev2 is run by supplying command line arguments at runtime.
 - Shakespokev3 is run with no arguments, and provides step by step prompts to read in the parameters.
 - Shakespokev2Test is run with no arguments, and takes in a batch of parameters from a JSON file.

## Installation
 - Extract the .class files and test_poke.json file to a working directory.
 - Add the external library to your java runtime classpath: gson-2.8.6.jar.

## Building
 - Use OpenJDK 14 or newer.
 - Add the external library to your java build classpath: gson-2.8.6.jar.
 - Compile the java source files in order:
  - Shakespokev2.java
  - Shakespokev2Test.java
  - Shakespokev3.java

## Running
 - Use OpenJDK JRE 14 or newer.
 - Ensure the JRE PATH is correctly set up.
 - Navigate to the working directory.
  - To run Shakespokev2:
    `java Shakespokev2 <pokemon_name> <version_name> <language_name>`
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
  
## Fair Use
The PokeAPI resource declares a fair use policy: Free access is intended for personal and development use.
It is not recommended to make multiple requests of large datasets.

The Shakespeare Translation resource offers a rate limited, free access tier.
When the "shake" language parameter is used, it is recommended to limit the request to 5 per hour, 60 per day.
