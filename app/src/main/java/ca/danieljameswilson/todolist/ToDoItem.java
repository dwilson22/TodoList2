package ca.danieljameswilson.todolist;

/**
 * Created by Daniel on 11/3/2016.
 */

public class ToDoItem {
    private String description;
    private boolean finished;
    private long id;

    public ToDoItem(String d, boolean b){
        this(d,b,-1);
    }

    public ToDoItem(String d, boolean b, long id){
        this.description = d;
        this.finished = b;
        this.id = id;
    }

    public String getDescription (){
        return this.description;
    }

    public boolean isComplete(){
        return finished;
    }

    public void toggleComplete(){
        this.finished = !finished;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public long getId() {
        return id;
    }
}
