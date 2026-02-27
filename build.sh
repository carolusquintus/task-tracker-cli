#!/bin/bash

if type -p javac; then
  echo "Info: javac found in PATH"
elif [ -n "$JAVA_HOME"] && [ -x "$JAVA_HOME/bin/javac"]; then
  echo "Info: javac found in JAVA_HOME"
else
  echo "Error: javac not found"
  exit 1
fi

if type -p java; then
  echo "Info: java found in PATH"
  _java=java
elif [ -n "$JAVA_HOME"] && [ -x "$JAVA_HOME/bin/java"]; then
  echo "Info: java found in JAVA_HOME"
  _java="$JAVA_HOME/bin/java"
else
  echo "Error: java not found"
  exit 1
fi

if [ "$_java" ]; then
  version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
  echo version "$version"
  IFS='.' read -ra version_components <<< "$version"
  if [ ${version_components[0]} -ge 25 ]; then
    echo "Info: java version is greater or equal than 25"
  else
    echo "Error: java version is lower than 25" >&2
    exit 1
  fi
fi

if type -p native-image; then
  echo "Info: native-image found in PATH"
elif [ -n "$JAVA_HOME"] && [ -x "$JAVA_HOME/bin/native-image"]; then
  echo "Info: native-image found in JAVA_HOME"
else
  echo "Error: native-image not found"
  exit 1
fi

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
