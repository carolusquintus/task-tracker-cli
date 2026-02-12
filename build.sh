#!/bin/bash

find ./src -type f -name "*.java" | xargs javac -cp ./src/ -d ./out/
jar cvfm task-tracker-cli.jar ./src/manifest.txt -C ./out .
java -jar task-tracker-cli.jar
