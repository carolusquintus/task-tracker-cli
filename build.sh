#!/bin/bash

# 1- removes all files from ./out folder
# 2- get all and only .java files existent at ./src folder and print result to source.txt
# 3- compiles all .java files obtained from source.txt and place them at ./out folder
# 4- remove source.txt
# 5- generates a .jar file from compiled files from ./out folder using manifest.txt
# 6- create a native binary executable with task name, from generated jar

rm -vrf ./out
find ./src -type f -name "*.java" > source.txt
javac @source.txt -d ./out
rm ./source.txt
jar cvfm task-tracker-cli.jar ./manifest.txt -C ./out .
native-image -jar ./task-tracker-cli.jar task
