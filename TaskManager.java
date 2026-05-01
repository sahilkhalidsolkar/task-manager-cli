import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class Task {
    int id;
    String title;
    boolean completed;

    public Task() {
    }

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}

public class TaskManager {

    private List<Task> taskList;
    private final String fileName = "tasks.json";

    public TaskManager() {
        this.taskList = this.loadFileData(fileName);
    }

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        String action = "";
        String value = "";
        if (args.length > 0) {
            action = args[0];
        }
        if (args.length > 1) {
            value = args[1];
        }
        switch (action) {
            case "add":
                taskManager.addTask(value);
                break;
            case "remove":
                taskManager.removeTask(Integer.valueOf(value));
                break;
            case "markAsComplete":
                taskManager.markTaskAsComplete(Integer.valueOf(value));
                break;
            case "markAsInComplete":
                taskManager.markTaskAsInComplete(Integer.valueOf(value));
                break;
            case "printAllTasks":
                taskManager.printAllTasks();
                break;
            case "getCompletedTasks":
                taskManager.getCompletedTasks();
                break;
            case "getPendingTasks":
                taskManager.getPendingTasks();
                break;
            default:
                System.out.println("Invalid action");
        }

    }

    public List<Task> addTask(String taskName) {
        Task task = new Task(this.taskList.size() + 1, taskName);
        this.taskList.add(task);
        this.saveDataToFile(this.taskList);
        return this.taskList;
    }

    public List<Task> removeTask(int id) {
        this.taskList.removeIf(task -> task.id == id);
        this.saveDataToFile(this.taskList);
        return this.taskList;
    }

    private List<Task> loadFileData(String fileName) {
        List<Task> tasksList = new ArrayList<>();
        try {
            File file = new File(fileName);
            file.createNewFile();
            if (file.length() == 0) {
                return tasksList;
            }
            String tasksAsJson = Files.readString(file.toPath());
            tasksAsJson = tasksAsJson.replace("[", "");
            tasksAsJson = tasksAsJson.replace("]", "");
            String[] tasks = tasksAsJson.trim().split("(?<=\\}),\\s*(?=\\{)");
            for (String task : tasks) {
                String singleTask = task.trim().replace("}", "").replace("{", "");
                String[] taskDetails = singleTask.split(",");
                Task taskObj = new Task();
                Integer id = Integer.valueOf(taskDetails[0].split(":")[1].trim());
                String title = taskDetails[1].split(":")[1].trim().replace("\"", "");
                Boolean completed = Boolean.valueOf(taskDetails[2].split(":")[1].trim());
                taskObj.setId(id);
                taskObj.setTitle(title);
                taskObj.setCompleted(completed);
                tasksList.add(taskObj);
            }
            return tasksList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return tasksList;
    }

    public void saveDataToFile(List<Task> tasksList) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (Task task : tasksList) {
            jsonBuilder.append("{");
            jsonBuilder.append("\"id\" : ").append(task.getId()).append(",");
            jsonBuilder.append("\"title\" : \"").append(task.getTitle()).append("\",");
            jsonBuilder.append("\"completed\" : ").append(task.isCompleted());
            jsonBuilder.append("}, ");
        }
        jsonBuilder.append("]");
        try (FileWriter fw = new FileWriter(this.fileName)) {
            fw.write(jsonBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAllTasks() {
        List<Task> tasks = this.loadFileData(this.fileName);
        tasks.stream().map(task -> task.getTitle()).forEach(System.out::println);
    }

    public void getCompletedTasks() {
        List<Task> tasks = this.loadFileData(this.fileName);
        tasks.stream().filter(task -> task.isCompleted()).map(task -> task.getTitle()).forEach(System.out::println);
    }

    public void getPendingTasks() {
        List<Task> tasks = this.loadFileData(this.fileName);
        tasks.stream().filter(task -> !task.isCompleted()).map(task -> task.getTitle()).forEach(System.out::println);
    }

    public void markTaskAsComplete(int id) {
        List<Task> tasks = this.loadFileData(this.fileName);
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(true);
                break;
            }
        }
        this.saveDataToFile(tasks);
    }

    public void markTaskAsInComplete(int id) {
        List<Task> tasks = this.loadFileData(this.fileName);
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(false);
                break;
            }
        }
        this.saveDataToFile(tasks);
    }
}
