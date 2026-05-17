package myproject;

public class Main {

	public static void main(String[] args) {
		TaskRepository taskRepository = new TaskRepository();
		TaskService taskService = new TaskService(taskRepository);
		ConsoleUI ui = new ConsoleUI(taskService);
		ui.start();
	}
}
