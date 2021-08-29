package com.example.a300;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private FirebaseFirestore db = null;
    private FirebaseUser user = null;
    private String TAG = "Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void function1(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
    public void function2(View view){
        startActivity(new Intent(this, ReviewList.class));
    }
    public void function3(View view) {
        ArrayList<String> array = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String a = document.getId();
                                array.add(a);
                            }
                            if (array.isEmpty()){
                                Toast.makeText(Home.this, "List is Empty",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(Home.this, test.class);
                                intent.putExtra("array", array);
                                startActivity(intent);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void function4(View view){
        startActivity(new Intent(this, MainActivity2.class));
    }
}