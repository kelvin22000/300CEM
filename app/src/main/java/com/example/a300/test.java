package com.example.a300;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Random;

public class test extends AppCompatActivity {
    private FirebaseFirestore db = null;
    private FirebaseUser user = null;
    private ArrayList<String> array = new ArrayList<>();
    private ArrayList<String> array2 = new ArrayList<>();
    private EditText english = null;
    private EditText chinese = null;
    private EditText answer = null;
    String beforeword = null;
    private String TAG = "test";
    private boolean check = true;
    private int random = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        english = findViewById(R.id.englishword);
        chinese = findViewById(R.id.chineseword);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        answer = findViewById(R.id.answer);
        Intent intent = getIntent();
        array = intent.getStringArrayListExtra("array");
        random = new Random().nextInt(array.size());
        beforeword = array.get(random);
        english.setText(beforeword);
        array2.add(beforeword);
    }

    public void getAnswer(View view) {
        DocumentReference docRef = db.collection(user.getUid()).document(array.get(random));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        answer.setText(document.getString("chinese"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void next(View view) {
        check = true;
        Log.d(TAG,""+array2.size());
        Log.d(TAG,""+array.size());
        if (array2.size() == array.size()) {
            Toast.makeText(test.this, "You finish your review",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            while (check){
                random = new Random().nextInt(array.size());
                check = repeat(array.get(random));
            }
            array2.add(array.get(random));
            Log.d(TAG,""+array2);
            english.setText(array.get(random));
            chinese.setText("");
            answer.setText("");
        }
    }

    public boolean repeat(String beforeword) {
        for (int a = 0; a < array2.size(); a++) {
            if (array2.get(a).equals(beforeword)) {
                return true;
            }
        }
        return false;
    }
}