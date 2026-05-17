package myproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class TaskRepository {
    private Map<Integer, Task> tasks = new HashMap<>();
    private int nextId = 1;
    public void save(Task task){
        if (task.getId() == 0){
            task.setId(nextId++);
        }
        tasks.put(task.getId(), task);
    }
    public ArrayList<Task> getAllTasks(){
        return new ArrayList<Task>(tasks.values());
    }
    public boolean isCorrectId(int id){
        return (id > 0 || tasks.containsKey(id));
    }
    public Task findById(int id){
        if (isCorrectId(id)){
            return tasks.get(id);
        }
        return null;
    }
     public void clearTasks(){
        tasks.clear();
        nextId = 1;
     }
     public boolean deleteById(int id){
        if (isCorrectId(id)){
            tasks.remove(id);
            return true;
        }
        return false;
     }
}
