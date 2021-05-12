package com.example.lab6_voquockhanh_18058521;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Person> arrayList;
    private DatabaseHandler databaseHandler;
    ListView listView;
    ArrayAdapter<Person> adapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        listView = findViewById(R.id.listview);

//        Person person = new Person("Đỗ Anh Bôn");
//        Person person1 = new Person("Hoàng Quốc Cường");
//        Person person2 = new Person("Phạm Minh Dũng");
//        Person person3 = new Person("Châu Hoàng Duy");
//        Person person4 = new Person("Trần Nhật Duy");
//        Person person5 = new Person("Nguyễn Đình Hảo");
//        Person person6 = new Person("Hà Khã Huê");


//        databaseHandler.addPerson(person);
//        databaseHandler.addPerson(person1);
//        databaseHandler.addPerson(person2);
//        databaseHandler.addPerson(person3);
//        databaseHandler.addPerson(person4);
//        databaseHandler.addPerson(person5);
//        databaseHandler.addPerson(person6);
        arrayList = (ArrayList<Person>) databaseHandler.getAllStudents();
//        arrayList.add(person);
//        arrayList.add(person1);
//        arrayList.add(person2);
//        arrayList.add(person3);
//        arrayList.add(person4);
//        arrayList.add(person5);
//        arrayList.add(person6);
        textView = findViewById(R.id.plaintxt);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(arrayList.get(i).toString());
            }
        });

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textView.getText().toString();
                Person person = new Person(name);
                databaseHandler.addPerson(person);
                arrayList.clear();
                arrayList.addAll(databaseHandler.getAllStudents());
                adapter.notifyDataSetChanged();
            }
        });

        Button btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textView.getText().toString();
                databaseHandler.deletePerson(name);
                arrayList.clear();
                arrayList.addAll(databaseHandler.getAllStudents());
                adapter.notifyDataSetChanged();
                textView.setText("");
            }
        });
    }
}