package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button login,register;

    FirebaseUser firebaseUser;



    //to directly login into the last account we used
    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){

            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        login=findViewById(R.id.start_login);
        register=findViewById(R.id.start_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

//                Toast.makeText(getApplicationContext(), "Worked", Toast.LENGTH_SHORT).show();
                //Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                //startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(StartActivity.this,RegisterActivity.class);
                //startActivity(intent);
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}
