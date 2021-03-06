package org.meicode.finalprojek2.Admin;

import static org.meicode.finalprojek2.Database.Preference.DATABASE_REFERENCE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.meicode.finalprojek2.databinding.ActivityAdminLoginPagesBinding;

public class AdminLoginPagesActivity extends AppCompatActivity {

    ActivityAdminLoginPagesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginPagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameLogin = binding.etAdminUserName.getText().toString();
                String passwordLogin = binding.etAdminPassword.getText().toString();

                if (usernameLogin.isEmpty() || passwordLogin.isEmpty()) {
                    Toast.makeText(AdminLoginPagesActivity.this, "Please enter your username or password", Toast.LENGTH_SHORT).show();
                } else {
                    DATABASE_REFERENCE.child("Admins").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if username/password is exist in firebase db
                            if (snapshot.hasChild(usernameLogin)) {
                                String getPasswordLogin = snapshot.child(usernameLogin).child("password").getValue(String.class);
                                if (getPasswordLogin.equals(passwordLogin)) {
                                    Toast.makeText(AdminLoginPagesActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AdminLoginPagesActivity.this, AdminActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(AdminLoginPagesActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminLoginPagesActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}