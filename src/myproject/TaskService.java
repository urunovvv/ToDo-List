package myproject;

import java.util.ArrayList;

public class TaskService
{
    private TaskRepository repository;
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }
    public Task addTask(String title, String description, Task.Priority priority) {
        // Проверка на пустой заголовок
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Error: Task title cannot be empty!");
            return null;
        }
        // Создаём новую задачу
        Task task = new Task(title, description, priority);
        // Сохраняем в репозиторий (там присвоится id)
        repository.save(task);
        System.out.println("✓ Task added successfully!");
        System.out.println("  ID: " + task.getId());
        System.out.println("  Title: " + task.getTitle());
        System.out.println("  Priority: " + task.getPriority());
        return task;
    }
    public void completeTask(int id){
        if (repository.isCorrectId(id)) {
            repository.findById(id).setStatus(true);
        }
    }
    public void deleteTask(int id){
        repository.deleteById(id);
    }
    public Task getTaskById(int id){
        return repository.findById(id);
    }
    public ArrayList getAllTasks(){
        return repository.getAllTasks();
    }
    public ArrayList<Task> getIncompleteTasks(){
        ArrayList<Task> incompleted = new ArrayList<>();
        for (Task t : repository.getAllTasks()){
            if (!t.getStatus()){
                incompleted.add(t);
            }
        }
        return incompleted;
    }
    public ArrayList<Task> getTasksByPriority(Task.Priority priority){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task t : repository.getAllTasks()){
            if (t.getPriority() == priority){
                tasks.add(t);
            }
        }
        return tasks;
    }
}
