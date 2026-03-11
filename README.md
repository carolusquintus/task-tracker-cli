# task-tracker-cli
[Task Tracker](https://roadmap.sh/projects/task-tracker) is a CLI app challenge project from [roadmap.sh](https://roadmap.sh), used to manage your tasks.

<img alt="Task Tracker CLI" src="https://carolusquintus.dev/img/uploads/projects/task-tracker-cli-full.gif"/>

### Challenge was developed using:

- Java 25
- GraalVM
- Bash

## Features

* Add a new task
* Update an existing task
* Delete a task
* Mark a task as in progress
* Mark a task as done
* List all tasks
* Filter task by status
* Show the detail of a task - New Feature!

## Installation

1. Clone repository:
```shell
git clone https://github.com/carolusquintus/task-tracker-cli.git
```
2. Move to project directory:
```shell
cd task-tracker-cli
```
3. Give execute permissions for your current user: 
```shell
chmod u+x install.sh
```
4. Before running the script, make sure you have GraalVM CE for jdk 25 installed. See: [GraalVM SDKMan Installation](https://www.graalvm.org/latest/getting-started/linux/#sdkman)
5. Run the installation script:\
   This script will compile and package the project in jar file.\
   Then it will generate a native binary based on built jar.\
   It also creates a symbolic link at `/usr/local/bin`
```shell
./install.sh
```

## Usage

### Help | It shows all available options of `task`
```shell
task help
```
or
```shell
task -h
```
output:
```shell
Usage: task [OPTION] [params]...
  or   task [OPTION]

where option is one of:
  -a, add <description>
                Add a new task with default status: todo
  -u, update <id> <description>
                Update the description of a task by given id
  -mip, mark-in-progress <id>
                Mark a task as in-progress by given id
  -md, mark-done <id>
                Mark a task as done by given id
  -s, show <id>
                Show details of a task by given id
  -d, delete <id>
                Delete a task by given id
  -l, list <status> | none
                List task by given status or list all tasks
  -h, help
                Print this help message
  -v, version
                Print version information
```
