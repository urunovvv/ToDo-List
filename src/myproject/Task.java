package myproject;

public class Task {
	// Enumeration For Priority
	// --------------------------
	enum Priority{
		LOW,
		MIDDLE,
		HIGH
	}
	// --------------------------
	private int id;
	private String title, description;
	boolean Status;
	private Priority priority;
	// ------------------------ CONSTRUCTORS -------------------------
	public Task(int id, String title) {
		this.id = id;
		this.title = title;
		this.Status = false;
	}
	public Task(int id, String title, boolean Status) {
		this.id = id;
		this.title = title;
		this.Status = Status;
	}
	public Task(String title, String description, Priority priority){
		this.description = description;
		this.title = title;
		this.priority = priority;
	}
	// ----------------------------------------------------------------


	// getters
	public int getId() {return id;}
	public String getTitle() {return this.title;}
	public String getDescription() {return this.description;}
	public boolean getStatus() {return Status;}
	public Priority getPriority(){return this.priority;}
	// Setters
	public void setId(int id){this.id = id;}
	public void setTitle(String title){this.title = title;}
	public void setDescription(String description){this.description = description;}
	public void setStatus(boolean Status){this.Status = Status;}
	public void setPriority(Priority priority){this.priority = priority;}


	// returning to user an information about the task
	@Override
	public String toString() {
		String status = (Status ? "[✔] " : "[ ] ");
		String ID = id + ". ";
		String title = "Title: " + this.title;
		String description = (this.description.equals("") ? "" : ("Description: " + this.description));
		String priority = " | Priority: " + this.priority;
		String task_info = status + ID + description + priority;
		return task_info;
	}
}
