package com.example.javiosyc.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.javiosyc.todoapp.model.ToDoItem;
import com.example.javiosyc.todoapp.model.enums.ToDoItemAction;
import com.example.javiosyc.todoapp.utils.DBUtils;

import java.util.ArrayList;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {
    private static Logger _log = Logger.getLogger(MainActivity.class.getName());
    private final int REQUEST_CODE = 200;
    ArrayList<ToDoItem> items;
    ArrayAdapter<ToDoItem> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);

        DBUtils utils = new DBUtils();

        items = utils.readItems(getFilesDir());

        _log.info(String.valueOf(items.size()));


        itemsAdapter = new ArrayAdapter<ToDoItem>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewEditorListener();
    }

    private void setupListViewEditorListener() {

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);

                intent.putExtra("action", ToDoItemAction.EDIT);
                intent.putExtra("items", items);
                intent.putExtra("saveFileDir", getFilesDir().getAbsolutePath());

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
        intent.putExtra("items", items);
        intent.putExtra("saveFileDir", getFilesDir().getAbsolutePath());

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
                        itemsAdapter.add(item);
                        itemsAdapter.notifyDataSetChanged();
                        break;
                    case EDIT:
                        ToDoItem updateItem = (ToDoItem) data.getSerializableExtra("updateItem");
                        ToDoItem oldItem = itemsAdapter.getItem(position);
                        oldItem.setName(updateItem.getName());
                        itemsAdapter.notifyDataSetChanged();

                        break;
                    case DELETE:
                        ToDoItem deletedItem = itemsAdapter.getItem(position);
                        itemsAdapter.remove(deletedItem);
                        itemsAdapter.notifyDataSetChanged();

                        break;
                }
            }
        }
    }
}
