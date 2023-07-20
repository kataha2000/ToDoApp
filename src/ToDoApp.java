import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ToDoApp {
    public static void main(String[] args) throws IOException {
        List<Task> tasks = csvParser.readFile();
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader keyboard = new BufferedReader(in);
        System.out.println("Type <Help> for commands");


        String input;
        while ((input = keyboard.readLine()) != null) {
            switch (input) {
                case "Help" -> System.out.println("Add task, Tasks, Edit task, Delete task, Delete all tasks, Tasks due date");
                case "Add task" -> {
                    System.out.println("Enter title:");
                    String title = keyboard.readLine();
                    System.out.println("Enter description:");
                    String description = keyboard.readLine();
                    System.out.println("Enter date year-month-day:");
                    LocalDate date = LocalDate.parse(keyboard.readLine());
                    System.out.println("Enter status:");
                    String status = keyboard.readLine();
                    System.out.println("Enter category:");
                    String category = keyboard.readLine();
                    Task Task = new Task(title, description, date, status, category);
                    tasks.add(Task);
                    for (Task task : tasks) {
                        csvParser.writeFile(tasks);
                    }
                }
                case "Tasks" -> {
                    tasks = csvParser.readFile();
                    int index = 0;
                    for (Task task : tasks) {
                        System.out.println((index + 1) + ", " + task.title + ", " + task.description + ", " + task.date + ", " + task.status + ", " + task.category);
                        index++;
                    }
                }

                case "Tasks due date" -> {
                    tasks = csvParser.readFile();
                    System.out.println("Newest or oldest on top?");
                    String input1 = keyboard.readLine();
                    int choice = Objects.equals(input1, "Oldest") ? 1 : -1;

                    tasks.sort((task1, task2) -> task1.date.compareTo(task2.date) * choice);

                    for (Task task : tasks) {
                        System.out.println(task.title + ", " + task.description + ", " + task.date + ", " + task.status + ", " + task.category);
                    }
                }

                case "Edit task" -> {
                    System.out.println("Enter index of task");
                    int i = Integer.parseInt(keyboard.readLine()) - 1;
                    System.out.println("What do you want to change?");
                    String input1 = keyboard.readLine();
                    switch (input1) {
                        case "Title" -> {
                            System.out.println("Enter new title");
                            tasks.get(i).title = keyboard.readLine();
                            csvParser.writeFile(tasks);
                        }
                        case "Description" -> {
                            System.out.println("Enter new description");
                            tasks.get(i).description = keyboard.readLine();
                            csvParser.writeFile(tasks);
                        }
                        case "Date" -> {
                            System.out.println("Enter new date year-month-day");
                            tasks.get(i).date = LocalDate.parse(keyboard.readLine());
                            csvParser.writeFile(tasks);
                        }
                        case "Status" -> {
                            System.out.println("Enter new status");
                            tasks.get(i).status = keyboard.readLine();
                            csvParser.writeFile(tasks);
                        }
                        case "Category" -> {
                            System.out.println("Enter new category");
                            tasks.get(i).category = keyboard.readLine();
                            csvParser.writeFile(tasks);
                        }
                    }
                }

                case "Delete task" -> {
                    System.out.println("Enter index of task");
                    int i = Integer.parseInt(keyboard.readLine()) - 1;
                    System.out.println("Are you sure?");
                    String input1 = keyboard.readLine();
                    switch (input1) {
                        case "Yes" -> {
                            tasks.remove(i);
                            csvParser.writeFile(tasks);
                        }
                        case "No" -> {

                        }
                    }
                }
                case "Delete all tasks" -> {
                    System.out.println("Are you sure?");
                    String input1 = keyboard.readLine();
                    switch (input1) {
                        case "Yes" -> {
                            for (int i = 0; i < tasks.size(); ) {
                                tasks.remove(i);
                            }
                            csvParser.writeFile(tasks);
                        }
                        case "No" -> {

                        }
                    }
                }
            }
        }
    }
}