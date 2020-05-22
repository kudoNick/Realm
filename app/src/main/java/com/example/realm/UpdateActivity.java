package com.example.realm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

public class UpdateActivity extends AppCompatActivity {

    Realm realm;
    EditText edtName;
    Button btnUpdate,btnDelete;
    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtName = findViewById(R.id.edtName);
        realm = Realm.getDefaultInstance();
        Intent getItent = getIntent();
       int position = getItent.getIntExtra("numPosition",0);
       user = realm.where(User.class).equalTo("user_id",position ).findFirst();


        edtName.setText(user.getName());

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                finish();
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        user.deleteFromRealm();
                        Toast.makeText(UpdateActivity.this, "Thanh cong",Toast.LENGTH_SHORT).show();
                    }
                });
                onBackPressed();
            }
        });
    }
    private void update(){
        realm.beginTransaction();
        user.setName(edtName.getText().toString().trim());
        realm.commitTransaction();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
