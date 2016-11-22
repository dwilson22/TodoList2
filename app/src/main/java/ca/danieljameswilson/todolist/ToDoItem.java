package ca.danieljameswilson.todolist;

/**
 * Created by Daniel on 11/3/2016.
 */

public class ToDoItem {
    private String description;
    private boolean finished;

    public ToDoItem(String d, boolean b){
        this.description = d;
        this.finished = b;
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

}
