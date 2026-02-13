#!/bin/bash

# removes all files from ./out folder
rm -vrf ./out
# get all and only .java files existent at ./src folder and print result to source.txt
find ./src -type f -name "*.java" > source.txt
# compiles all .java files obtained from source.txt and place them at ./out folder
javac @source.txt -d ./out
# remove source.txt
rm ./source.txt
# generates a .jar file from compiled files from ./out folder using manifest.txt
jar cvfm task-tracker-cli.jar ./manifest.txt -C ./out .
# create a native binary executable with task name, from generated jar
native-image -jar ./task-tracker-cli.jar task
