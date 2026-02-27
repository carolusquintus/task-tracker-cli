#!/bin/bash

echo "Running build.sh"
./build.sh
echo "Finished build.sh"

TASK_APP="task"
SOURCE_FILE="$(pwd)/$TASK_APP"
LINK_PATH="/usr/local/bin/$TASK_APP"

if [ ! -f "$SOURCE_FILE" ]; then
  echo "Error: Source program not found at $SOURCE_FILE"
  exit 1
fi

if [ ! -d "/usr/local/bin/" ]; then
  echo "Info: /usr/local/bin not found, creating it"
  sudo mkdir -p "/usr/local/bin"
fi

if [ -f "$LINK_PATH" ]; then
  echo "Info: $LINK_PATH already exists"
  exit 1
fi

sudo "Creating symbolic link: $LINK_PATH -> $SOURCE_FILE"
sudo ln -sf $SOURCE_FILE $LINK_PATH

if [ $? -eq 0 ]; then
  echo "Info: Link created. You can now run the program"
else
  echo "Error: Failed to create symbolic link"
  exit 1
fi
