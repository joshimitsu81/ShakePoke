# Shakepoke
A program to lookup a Pokemon and describe it in Shakespearey type language.
This package includes the source, library, and JAR executable.
 - shakespoke.jar is run by supplying the designated port number at runtime.

## Installation
 1. Copy the file shakespoke.jar to your working directory.

## Building
 1. **Prerequisite:** *Use OpenJDK 14 or newer.*
 2. Compile the java source files using your IDE of choice.

## Running
 1. **Prerequisite:** *Use OpenJDK JRE 14 or newer.*
 2. Navigate to the working directory.
  - To run shakespoke:
    `java -jar shakespoke.jar <port_number>`
  - Example:
    `java -jar shakespoke 5099`
    
 ## Using
 - To access the http micro servers:
   `http://localhost:<port>/<endpoint>/<path>`
 - The available endpoints are:
	/international
	/shakespearean
 - Example:
   `http://localhost:5099/international/ditto`
   `http://localhost:5099/shakespearean/ditto`
   
 - The International version offers some URL parameter options:
 `/international/<path>?lang=<lang>&ver=<version>`
 - Example:
 `/international/ditto?lang=fr&ver=blue`
  
  
## Fair Use
The PokeAPI resource declares a fair use policy: Free access is intended for personal and development use.
It is not recommended to make multiple requests of large datasets.

The Shakespeare Translation resource offers a rate limited, free access tier.
When the /shakespearean endpoint is used, it is recommended to limit the request to 5 per hour, 60 per day.
