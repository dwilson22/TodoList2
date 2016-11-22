package ca.danieljameswilson.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ToDoListManager {

    private static final String APP_PREFERENCES = "todoapp";
    private static final String TODO_ITEMS_KEY = "itemslist";
    private List<ToDoItem> list;
    private SharedPreferences data;

    public ToDoListManager(Context context){
        data = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String json = data.getString(TODO_ITEMS_KEY, null);

        if(json == null){
            list = new ArrayList<>();
        }else {

            Type type = new TypeToken<List<ToDoItem>>() {
            }.getType();
            list = new Gson().fromJson(json, type);
        }

    }

    public List<ToDoItem> getList(){
        return list;
    }

    public void add(ToDoItem item){

        list.add(item);
        saveList();
    }

    public void removeItem(ToDoItem item){
        list.remove(item);
      //  Log.d("deleting", item.getDescription().toString());
        saveList();
    }

    public void saveList(){
        SharedPreferences.Editor edit = data.edit();
        edit.clear();
        String json = new Gson().toJson(list);
        edit.putString(TODO_ITEMS_KEY, json);
        edit.apply();
    }
}
