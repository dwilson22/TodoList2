package ca.danieljameswilson.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.*;


public class MainActivity extends AppCompatActivity {
    private ToDoListManager listManager;
    private ToDoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView Todo = (ListView) findViewById(R.id.todo_list);
        listManager = new ToDoListManager(getApplicationContext());

        adapter = new ToDoItemAdapter(this, listManager.getList());

        Todo.setAdapter(adapter);

        ImageButton addItem = (ImageButton) findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onAddButtonClick();
            }
        });


    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    private void onAddButtonClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_item);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder. setView(input);

        builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listManager.add(new ToDoItem(input.getText().toString(), false));
                adapter.swapList(listManager.getList());
            }
        });

        builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void onRemoveButtonClick(final ToDoItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.remove_item);
        TextView text = new TextView(this);
        text.setText(R.string.are_you_sure);
        text.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setGravity(Gravity.CENTER);

        builder.setView(text);

        builder.setPositiveButton(R.string.remove_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listManager.removeItem(item);
                adapter.swapList(listManager.getList());
            }
        });

        builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    protected class ToDoItemAdapter extends ArrayAdapter<ToDoItem>{
        private Context context;
        private List<ToDoItem> items;
        private LayoutInflater inflater;
        public ToDoItemAdapter(Context context, List<ToDoItem> items){
            super(context, -1, items);
            this.context = context;
            this.items = items;
            this.inflater = LayoutInflater.from(context);
        }

        public void swapList(List<ToDoItem> items){
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount(){
            return items.size();
        }

        @Override
        public View getView(int position, View  convertView, ViewGroup parent){
            final ItemViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.to_do_item_layout, parent, false);
                holder = new ItemViewHolder();
                holder.itemDescription = (TextView) convertView.findViewById(R.id.item);
                holder.itemCompleted = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(holder);
            }else{
                holder = (ItemViewHolder) convertView.getTag();
            }

            ImageButton removeItem = (ImageButton) convertView.findViewById(R.id.remove_item);
            holder.itemDescription.setText(items.get(position).getDescription());
            holder.itemCompleted.setChecked(items.get(position).isComplete());

            holder.itemCompleted.setTag(items.get(position));
            removeItem.setTag(items.get(position));

            removeItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ToDoItem item = (ToDoItem) v.getTag();
                    onRemoveButtonClick(item);
                    notifyDataSetChanged();
                }
            });

            convertView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ToDoItem item = (ToDoItem) holder.itemCompleted.getTag();
                    item.toggleComplete();
                    listManager.updateItem(item);
                    notifyDataSetChanged();
                }
            });
            holder.itemCompleted.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ToDoItem item = (ToDoItem) holder.itemCompleted.getTag();
                    item.toggleComplete();
                    listManager.updateItem(item);
                    notifyDataSetChanged();
                }
            });


            return convertView;
        }
    }
    public static class ItemViewHolder{
        public TextView itemDescription;
        public CheckBox itemCompleted;
    }
}
