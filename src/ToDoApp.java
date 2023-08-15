import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ToDoApp {
    public static void main(String[] args) throws IOException {
        List<Task> tasks = csvParser.readFile();
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader keyboard = new BufferedReader(in);
        System.out.println("Type <Help> for commands");


        String input;
        outerLoop:
        while ((input = keyboard.readLine()) != null) {
            input = input.toLowerCase();
            switch (input) {
                case "help" ->
                        System.out.println("Add - add a new task \nTasks - show all tasks \nEdit - edit task \nDelete - delete a task \nDelete all - delete all tasks \nTasks due date - show tasks by due date \nCategory - show tasks with certain category \nExit - close program");

                case "category" -> {
                    boolean find = false;
                    System.out.println("Enter category:");
                    int index = 0;
                    String input1 = keyboard.readLine().toLowerCase(Locale.ROOT);
                    for (Task task : tasks) {
                        if (Objects.equals(input1, task.category.toLowerCase())) {
                            System.out.println((index + 1) + ", " + task.title + ", " + task.description + ", " + task.date + ", " + task.status + ", " + task.category);
                            index++;
                            find = true;
                        }
                    }
                    if (!find) {
                        System.out.println("No tasks in this category");
                    }

                }
                case "add" -> {
                    LocalDate date = null;

                    System.out.println("Enter title:");
                    String title = keyboard.readLine();

                    System.out.println("Enter description:");
                    String description = keyboard.readLine();

                    System.out.println("Enter date year-month-day:");
                    while (date == null) {
                        try {
                            date = LocalDate.parse(keyboard.readLine());
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date, try again");
                        }
                    }

                    System.out.println("Enter status:");
                    String status = keyboard.readLine();

                    System.out.println("Enter category:");
                    String category = keyboard.readLine();

                    Task Task = new Task(title, description, date, status, category);
                    tasks.add(Task);
                    for (Task task : tasks) {
                        csvParser.writeFile(tasks);
                    }
                    System.out.println("Task created successfully");
                }
                case "tasks" -> {
                    tasks = csvParser.readFile();
                    int index = 0;
                    if (tasks.size() < 1) {
                        System.out.println("Empty");
                    } else {
                        for (Task task : tasks) {
                            System.out.println((index + 1) + ", " + task.title + ", " + task.description + ", " + task.date + ", " + task.status + ", " + task.category);
                            index++;
                        }
                    }
                }

                case "tasks due date" -> {
                    tasks = csvParser.readFile();
                    if (tasks.size() < 1) {
                        System.out.println("Empty");
                    } else {
                        tasks.sort((task1, task2) -> task1.date.compareTo(task2.date) * -1);

                        for (Task task : tasks) {
                            System.out.println(task.title + ", " + task.description + ", " + task.date + ", " + task.status + ", " + task.category);
                        }
                    }
                }

                case "edit" -> {
                    if (tasks.size() < 1) {
                        System.out.println("Empty");
                    } else {
                        int i;
                        System.out.println("Enter index of task:");
                        try {
                            i = Integer.parseInt(keyboard.readLine()) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid index");
                            continue;
                        }
                        System.out.println("What do you want to change?");
                        String input1 = keyboard.readLine().toLowerCase(Locale.ROOT);
                        switch (input1) {
                            case "title" -> {
                                System.out.println("Enter new title:");
                                tasks.get(i).title = keyboard.readLine();
                                csvParser.writeFile(tasks);
                            }
                            case "description" -> {
                                System.out.println("Enter new description:");
                                tasks.get(i).description = keyboard.readLine();
                                csvParser.writeFile(tasks);
                            }
                            case "date" -> {
                                LocalDate date = null;
                                System.out.println("Enter date year-month-day:");
                                while (date == null) {
                                    try {
                                        date = LocalDate.parse(keyboard.readLine());
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Invalid date, try again");
                                    }
                                }
                                csvParser.writeFile(tasks);
                            }
                            case "status" -> {
                                System.out.println("Enter new status:");
                                tasks.get(i).status = keyboard.readLine();
                                csvParser.writeFile(tasks);
                            }
                            case "category" -> {
                                System.out.println("Enter new category:");
                                tasks.get(i).category = keyboard.readLine();
                                csvParser.writeFile(tasks);
                            }
                        }
                        System.out.println("Task was edited");
                    }
                }

                case "delete" -> {
                    int i;
                    System.out.println("Enter index of task:");
                    try {
                        i = Integer.parseInt(keyboard.readLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid index");
                        continue;
                    }
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
                    System.out.println("Task was deleted");
                }

                case "delete all" -> {
                    if (tasks.size() < 1) {
                        System.out.println("Empty");
                    } else {
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
                        System.out.println("Tasks was deleted");
                    }
                }

                case "exit" -> {
                    break outerLoop;
                }

                default -> {
                    System.out.println("Unknown command, try again, remember the case and the correct spelling of commands");
                }
            }
        }
    }
}