package ca.danieljameswilson.todolist;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Daniel on 11/3/2016.
 */

public class ToDoItem {
    private String description;
    private boolean finished;
    private long id;
    private String timeStamp;

    public ToDoItem(String d, boolean b){
        this(d,b,-1,null);
    }

    public ToDoItem(String d, boolean b, long id,String ts){
        this.description = d;
        this.finished = b;
        this.id = id;
        this.timeStamp = ts;
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

    public String getTimeStamp() {
        return timeStamp;
    }
}
