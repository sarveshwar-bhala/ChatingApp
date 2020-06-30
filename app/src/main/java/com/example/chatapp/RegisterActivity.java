    package com.example.chatapp;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;

    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.Button;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.textfield.TextInputLayout;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.rengwuxian.materialedittext.MaterialEditText;

    import java.util.HashMap;

    import static android.text.TextUtils.isEmpty;

    public class RegisterActivity extends AppCompatActivity {


        //variables
        TextInputLayout username,email,password;
        Button btn_register;

        //database variable initialisation
        FirebaseAuth auth;
        DatabaseReference reference;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            Toolbar toolbar=findViewById(R.id.toolbar3);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Register");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            //hooks
            username=findViewById(R.id.register_name);
            email=findViewById(R.id.register_email);
            password=findViewById(R.id.register_password);
            btn_register=findViewById(R.id.register_btn);

            auth= FirebaseAuth.getInstance();


            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String txt_username=username.getEditText().getText().toString();
                    String txt_email=email.getEditText().getText().toString();
                    String txt_password=password.getEditText().getText().toString();


                    if (TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){

                        Toast.makeText(RegisterActivity.this,"All Fields Are Required",Toast.LENGTH_SHORT).show();

                    }else if (txt_password.length()<6){

                        Toast.makeText(RegisterActivity.this,"Password Must Be Atleast 6 Character",Toast.LENGTH_SHORT).show();
                    }else {

                        register(txt_username,txt_email,txt_password);
                    }



                }
            });

        }

        private void register(final String username, String email, String password){

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                FirebaseUser firebaseUser=auth.getCurrentUser();
                                assert firebaseUser != null;
                                String userid=firebaseUser.getUid();

                                reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                HashMap<String,String>hashMap=new HashMap<>();
                                hashMap.put("id",userid);
                                hashMap.put("username",username);
                                hashMap.put("imageURL","default");
                                hashMap.put("status","offline");
                                hashMap.put("search",username.toUpperCase());

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }
                                });

                            }else {
                                Toast.makeText(RegisterActivity.this,"YOU CAN NOT REGISTER WITH THIS EMAIL AND PASSWORD",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    }
