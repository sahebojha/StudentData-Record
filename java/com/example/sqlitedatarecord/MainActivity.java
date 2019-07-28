package com.example.sqlitedatarecord;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editTexID, editName,editEmail,editCc;
    Button buttonAdd,buttonUpdate,buttonGetData,buttonDelete, buttonViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        editTexID = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editEmail = findViewById(R.id.editText_email);
        editCc = findViewById(R.id.editText_CC);

        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        buttonUpdate = findViewById(R.id.button_update);
        buttonGetData = findViewById(R.id.button_view);
        buttonViewAll = findViewById(R.id.button_viewAll);
//        showMessage("test","testing");
        AddData();
        getData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(editName.getText().toString(),
                        editEmail.getText().toString(),editCc.getText().toString());
                if(isInserted == true){
                    Toast.makeText(MainActivity.this,"DATA inserted",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Something went Wrong!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTexID.getText().toString();
                if (id.equals(String.valueOf(""))){
                    editTexID.setError("Enter Id");
                    return;
                }
                Cursor cursor = myDB.getData(id);
                String data = null;
                if (cursor.moveToNext()){
                    data = "ID" + cursor.getString(0) +"\n"+
                            "Name: " +cursor.getString(1) +"\n"+
                            "Email: " +cursor.getString(2) +"\n"+
                            "Course Count: " +cursor.getString(3) +"\n";
                }
                showMessage("Data",data);
            }
        });
    }

    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDB.getAllData();
                if(cursor.getCount()==0){
                    showMessage("Error","Nothing Found in DB");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("ID"+cursor.getString(0)+"\n");
                    buffer.append("Name"+cursor.getString(1)+"\n");
                    buffer.append("Email"+cursor.getString(2)+"\n");
                    buffer.append("CC"+cursor.getString(3)+"\n\n");
                }
                showMessage("All Data", buffer.toString());

            }
        });
    }

    public void UpdateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate =myDB.updateDate(editTexID.getText().toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editCc.getText().toString());

                if (isUpdate == true){
                    Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"something wrong",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public  void DeleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRow = myDB.deleteData(editTexID.getText().toString());
                if (deleteRow > 0){
                    Toast.makeText(MainActivity.this,"Delete Successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"OOpss Wrong Something",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private  void showMessage(String title, String messsage){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(messsage);
        builder.show();

    }
}
