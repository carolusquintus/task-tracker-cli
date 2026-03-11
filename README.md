# task-tracker-cli
[Task Tracker](https://roadmap.sh/projects/task-tracker) is a CLI app challenge project from [roadmap.sh](https://roadmap.sh), used to manage your tasks.

<img alt="Task Tracker CLI" src="https://carolusquintus.dev/img/uploads/projects/task-tracker-cli-full.gif"/>

### This challenge was developed using:

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
3. Give execute permissions for your current user to `install.sh` script: 
```shell
chmod u+x install.sh
```
4. Before running the script, make sure you have GraalVM CE for jdk 25 installed.\
   See: [GraalVM SDKMan Installation](https://www.graalvm.org/latest/getting-started/linux/#sdkman)
5. Run the installation script:\
   This script will compile and package the project in jar file.\
   Then it will generate a native binary based on built jar.\
   It also creates a symbolic link at `/usr/local/bin`
```shell
./install.sh
```

## Usage

All task are stored at current directory where `task` command is executed in `tasks-stored.json

### Help - It shows all available options of `task`.
```shell
task help
```
or
```shell
task -h
```
output:
```text
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

### Add - Creates a new task with default status `TODO`.
```shell
task add "Finish diagram"
```
or
```shell
task -a "Finish diagram"
```
output:
```text
Task added successfully (ID: <id>)
```

### Update - Modifies existing task by given id and a new description.
```shell
task update <id> "Finish diagram"
```
or you can also update task with a long description by pressing Enter key, which will add a new line until double quote `"` is closed.
```shell
task -u <id> "Finish diagram
dquote> in order to be ready
dquote> for release Q1"
```
output:
```text
Task updated successfully (ID: <id>)
```

### Delete - Removes existing task by given id.
```shell
task delete <id>
```
or
```shell
task -d <id>
```
output:
```text
Task deleted successfully (ID: <id>)
```

### Mark In Progress - Changes the status of a task as `IN_PROGRESS`.
```shell
task mark-in-progress <id>
```
or
```shell
task -mip <id>
```
output:
```text
Task marked as in-progress successfully (ID: <id>)
```

### Mark Done - Changes the status of a task as `DONE`.

Once a task is mark as `DONE` it can not be modified.
```shell
task mark-done <id>
```
or
```shell
task -md <id>
```
output:
```text
Task marked as done successfully (ID: <id>)
```

### List - Shows a table with all tasks.
```shell
task list
```
or
```shell
task -l
```
output:
```text
+-----+-------------------+--------+---------------------+------------+
| ID  | DESCRIPTION       | STATUS | CREATED AT          | UPDATED AT |
+-----+-------------------+--------+---------------------+------------+
|   1 | Finish diagram... | TODO   | 2026-03-11 16:29:14 | null       |
| ... | ...               | ...    | ...                 | ...        |
| ... | ...               | ...    | ...                 | ...        |
|   N | ...               | ...    | ...                 | ...        |
+-----+-------------------+--------+---------------------+------------+
```

### Filter - Shows a table with all tasks by given status.
```shell
task list <status>
```
or
```shell
task -l <status>
```
output:
```text
+-----+-------------------+----------+---------------------+------------+
| ID  | DESCRIPTION       | STATUS   | CREATED AT          | UPDATED AT |
+-----+-------------------+----------+---------------------+------------+
|   1 | Finish diagram... | <status> | 2026-03-11 16:29:14 | null       |
| ... | ...               | <status> | ...                 | ...        |
| ... | ...               | <status> | ...                 | ...        |
|   N | ...               | <status> | ...                 | ...        |
+-----+-------------------+----------+---------------------+------------+
```

### Detail - Shows the detail of a task by given id.
```shell
task show <id>
```
or
```shell
task -s <id>
```
output:
```text
+-------------+----------------------+
| ID          |                    1 |
+-------------+----------------------+
| DESCRIPTION | Finish diagram       |
|             | in order to be ready |
|             | for release of Q1    |
+-------------+----------------------+
| STATUS      | TODO                 |
+-------------+----------------------+
| CREATED AT  | 2026-03-11 16:29:14  |
+-------------+----------------------+
| UPDATED AT  | null                 |
+-------------+----------------------+
```

### Version - Shows current version.
```shell
task version
```
or
```shell
task -v
```
output:
```text
Task Tracker CLI version n.n.n by carolusquintus
```

