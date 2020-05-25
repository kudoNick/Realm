package com.example.realm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ListView lvData;
    private EditText editName,edtId;
    private Button btnSave;
    Realm realm;

    MyHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        editName = findViewById(R.id.edtName);
        edtId = findViewById(R.id.edtId);

        lvData = findViewById(R.id.lvData);


        myHelper = new MyHelper(realm);
        myHelper.selectFromDB();

        UserAdapter adapter = new UserAdapter(this,myHelper.userArrayList());
        lvData.setAdapter(adapter);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
             fillData();
            }
        });

    }

    private void saveData(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                Number number = bgRealm.where(User.class).max("user_id");
                int newKey = (number == null) ?1 : number.intValue() +1;

                User user = bgRealm.createObject(User.class,newKey);
                user.setName(editName.getText().toString().trim());
//                user.setAge(Integer.parseInt(edtId.getText().toString().trim()));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"Thanh cong",Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(MainActivity.this,"Ko thanh cong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fillData() {

        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                RealmResults<User> user = realm.where(User.class).findAll();
                UserAdapter adapter = new UserAdapter(MainActivity.this,myHelper.userArrayList());
                lvData.setAdapter(adapter);
            }
        };
        realm.addChangeListener(changeListener);
    }

}
