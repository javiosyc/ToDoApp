package com.example.javiosyc.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.javiosyc.todoapp.dao.ToDoDAO;
import com.example.javiosyc.todoapp.model.ToDoItem;
import com.example.javiosyc.todoapp.model.adapters.ToDoItemsViewHolderAdapter;
import com.example.javiosyc.todoapp.model.enums.ToDoItemAction;
import com.example.javiosyc.todoapp.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {
    private static Logger _log = Logger.getLogger(MainActivity.class.getName());
    private final int REQUEST_CODE = 200;
    List<ToDoItem> items;
    ArrayAdapter<ToDoItem> itemsAdapter;
    ListView lvItems;

    private ToDoDAO toDoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        DBUtils utils = new DBUtils();

        items = utils.readItems(getFilesDir());
        */

        toDoDAO = new ToDoDAO(getApplicationContext());

        items = toDoDAO.getAll();

        _log.info(String.valueOf(items.size()));


        //itemsAdapter = new ArrayAdapter<ToDoItem>(this, android.R.layout.simple_list_item_1, items);


        //itemsAdapter = new ToDoItemsNoCachingAdapter(this,items);

        itemsAdapter = new ToDoItemsViewHolderAdapter(this,items);

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        setupListViewEditorListener();
    }

    private void setupListViewEditorListener() {

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);

                intent.putExtra("action", ToDoItemAction.EDIT);
                intent.putExtra("items", (ArrayList<ToDoItem>) items);

                intent.putExtra("selectedItemPosition", position);

                _log.info("selectedItemPosition:" + position);
                _log.info("selectedItemName:" + items.get(position).getName());

                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void onAddItem(View v) {
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);

        intent.putExtra("action", ToDoItemAction.ADD);
        intent.putExtra("items", (ArrayList<ToDoItem>) items);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int position = data.getIntExtra("updateItemPosition", -1);
            ToDoItemAction action = (ToDoItemAction) data.getSerializableExtra("action");

            if (position >= 0) {
                switch (action) {
                    case ADD:
                        ToDoItem item = (ToDoItem) data.getSerializableExtra("updateItem");

                        /*items.add(item);*/

                        item = toDoDAO.insert(item);

                        items.add(item);
                        itemsAdapter.notifyDataSetChanged();

                        break;
                    case EDIT:
                        ToDoItem updateItem = (ToDoItem) data.getSerializableExtra("updateItem");

                        /*ToDoItem oldItem = itemsAdapter.getItem(position);*/

                        toDoDAO.update(updateItem);

                        items.set(position, updateItem);
                        itemsAdapter.notifyDataSetChanged();

                        break;
                    case DELETE:
                        ToDoItem deletedItem = itemsAdapter.getItem(position);

                        toDoDAO.delete(deletedItem.getId());

                        items.remove(deletedItem);
                        itemsAdapter.notifyDataSetChanged();

                        break;
                }

                //updateDB();
            }
        }
    }

    private void updateDB() {
        DBUtils utils = new DBUtils();
        utils.writeItems(getFilesDir(), items);
    }
}
