package myproject;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI {
    private TaskService taskService; // field for service
    private Scanner sc; // field for input data from console
    private boolean running; // field about state of working

    // constructor to get TaskService
    public ConsoleUI(TaskService taskService) {
        this.taskService = taskService;
        this.sc = new Scanner(System.in);
        this.running = true;
    }

    // method to show Main Menu to User
    public void showMenu() {
        System.out.println("\n=== TO DO LIST ===");
        System.out.println("add     - add a task (example: add Купить молоко)");
        System.out.println("list    - show all tasks");
        System.out.println("done    - mark task as done (example: done 3)");
        System.out.println("delete  - delete task (example: delete 2)");
        System.out.println("edit    - edit task title (example: edit 4 New title)");
        System.out.println("incomplete - show only incomplete tasks");
        System.out.println("filter  - show tasks by priority (example: filter HIGH)");
        System.out.println("clear   - clear console");
        System.out.println("help    - show this help");
        System.out.println("exit    - exit program");
        System.out.println("================================");
    }

    // The main function
    public void start() {
        System.out.println("WELCOME TO TO DO LIST!");
        showMenu();

        while (running) {
            System.out.print("\n> ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            processCommand(input);
        }

        System.out.println("Goodbye! Have a productive day!");
        sc.close();
    }

    // method for request processing
    private void processCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "add":
                handleAddCommand(args);
                break;
            case "list":
                handleListCommand();
                break;
            case "done":
                handleDoneCommand(args);
                break;
            case "delete":
                handleDeleteCommand(args);
                break;
            case "edit":
                handleEditCommand(args);
                break;
            case "incomplete":
                handleIncompleteCommand();
                break;
            case "filter":
                handleFilterCommand(args);
                break;
            case "clear":
                clearConsole();
                break;
            case "help":
                showMenu();
                break;
            case "exit":
                running = false;
                break;
            default:
                System.out.println("Unknown command. Type 'help' to see available commands.");
        }
    }

    // Handle add command
    private void handleAddCommand(String args) {
        if (args.isEmpty()) {
            System.out.println("Usage: add <title> [description] [HIGH/MIDDLE/LOW]");
            System.out.println("Example: add Buy milk 'Go to store' HIGH");
            return;
        }

        // Parse arguments: title, description, priority
        String title = args;
        String description = "";
        Task.Priority priority = Task.Priority.MIDDLE; // default

        // Check if there are quotes for description
        if (args.contains("\'")) {
            // Complex parsing with quotes
            int firstQuote = args.indexOf("\'");
            int secondQuote = args.indexOf("\'", firstQuote + 1);
            if (firstQuote != -1 && secondQuote != -1) {
                title = args.substring(0, firstQuote).trim();
                description = args.substring(firstQuote + 1, secondQuote);
                String remaining = args.substring(secondQuote + 1).trim();
                if (!remaining.isEmpty()) {
                    try {
                        priority = Task.Priority.valueOf(remaining.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid priority. Using MIDDLE. Available: HIGH, MIDDLE, LOW");
                    }
                }
            }
        } else {
            // Simple parsing: split by spaces
            String[] simpleParts = args.split(" ", 3);
            title = simpleParts[0];
            if (simpleParts.length >= 2) {
                description = simpleParts[1];
            }
            if (simpleParts.length >= 3) {
                try {
                    priority = Task.Priority.valueOf(simpleParts[2].toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid priority. Using MIDDLE. Available: HIGH, MIDDLE, LOW");
                }
            }
        }

        taskService.addTask(title, description, priority);
    }

    // Handle list command
    private void handleListCommand() {
        ArrayList<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("Your ToDo list is empty! Use 'add' to create tasks.");
            return;
        }

        System.out.println("\n=== ALL TASKS ===");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("Total: " + tasks.size() + " task(s)");
    }

    // Handle done command
    private void handleDoneCommand(String args) {
        if (args.isEmpty()) {
            System.out.println("Usage: done <id>");
            System.out.println("Example: done 3");
            return;
        }

        try {
            int id = Integer.parseInt(args);
            taskService.completeTask(id);
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    // Handle delete command
    private void handleDeleteCommand(String args) {
        if (args.isEmpty()) {
            System.out.println("Usage: delete <id>");
            System.out.println("Example: delete 2");
            return;
        }

        try {
            int id = Integer.parseInt(args);
            taskService.deleteTask(id);
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    // Handle edit command
    private void handleEditCommand(String args) {
        if (args.isEmpty()) {
            System.out.println("Usage: edit <id> <new title>");
            System.out.println("Example: edit 4 New task title");
            return;
        }

        String[] parts = args.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Error: Please provide both id and new title");
            return;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String newTitle = parts[1];

            Task task = taskService.getTaskById(id);
            if (task != null) {
                task.setTitle(newTitle);
                System.out.println("✓ Task " + id + " updated to: " + newTitle);
            } else {
                System.out.println("Error: Task with id " + id + " not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number for id!");
        }
    }

    // Handle incomplete command
    private void handleIncompleteCommand() {
        ArrayList<Task> tasks = taskService.getIncompleteTasks();

        if (tasks.isEmpty()) {
            System.out.println("All tasks are completed! Great job!");
            return;
        }

        System.out.println("\n=== INCOMPLETE TASKS ===");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("Remaining: " + tasks.size() + " task(s)");
    }

    // Handle filter command
    private void handleFilterCommand(String args) {
        if (args.isEmpty()) {
            System.out.println("Usage: filter <HIGH/MIDDLE/LOW>");
            System.out.println("Example: filter HIGH");
            return;
        }

        try {
            Task.Priority priority = Task.Priority.valueOf(args.toUpperCase());
            ArrayList<Task> tasks = taskService.getTasksByPriority(priority);

            System.out.println("\n=== TASKS WITH PRIORITY " + priority + " ===");
            if (tasks.isEmpty()) {
                System.out.println("No tasks found with priority " + priority);
            } else {
                for (Task task : tasks) {
                    System.out.println(task);
                }
                System.out.println("Total: " + tasks.size() + " task(s)");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid priority! Use: HIGH, MIDDLE, or LOW");
        }
    }

    // Clear console
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
