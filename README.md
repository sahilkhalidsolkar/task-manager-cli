# CLI Task Manager

A simple, lightweight Command Line Interface (CLI) Task Manager built with Java. This application allows you to manage your daily tasks directly from the terminal, with data persisted in a JSON file.

## 🚀 Features

- **Add Tasks**: Quickly add new tasks to your list.
- **Remove Tasks**: Delete tasks using their unique ID.
- **Mark Status**: Mark tasks as complete or incomplete.
- **View Tasks**:
    - List all tasks.
    - View only completed tasks.
    - View only pending tasks.
- **Persistence**: All tasks are saved in `tasks.json`, ensuring your data is safe between sessions.

## 🛠️ Prerequisites

- **Java JDK 11 or higher** installed on your system.

## 📦 Getting Started

### 1. Compile the Project

Navigate to the project directory and compile the Java file:

```bash
javac TaskManager.java
```

### 2. Usage

Run the application using the following commands:

| Action | Command |
| :--- | :--- |
| **Add a Task** | `java TaskManager add "Your task title"` |
| **Remove a Task** | `java TaskManager remove <id>` |
| **Mark Complete** | `java TaskManager markAsComplete <id>` |
| **Mark Incomplete** | `java TaskManager markAsInComplete <id>` |
| **List All Tasks** | `java TaskManager printAllTasks` |
| **List Completed** | `java TaskManager getCompletedTasks` |
| **List Pending** | `java TaskManager getPendingTasks` |

## 📂 Project Structure

- `TaskManager.java`: The main source code containing the logic for task management and JSON handling.
- `tasks.json`: The data file where tasks are stored (automatically created if it doesn't exist).

## 📝 Example

```bash
# Add a task
java TaskManager add "Buy groceries"

# List all tasks
java TaskManager printAllTasks

# Mark task 1 as complete
java TaskManager markAsComplete 1
```

---
*Created as part of the Java Roadmap Projects.*
