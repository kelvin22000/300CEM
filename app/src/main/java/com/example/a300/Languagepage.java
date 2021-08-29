package com.example.a300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Languagepage extends AppCompatActivity {
    private static boolean setLanguage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languagepage);

        if (!setLanguage) {
            setLanguage = true;
            showSaveLanguage(SpUserUtils.getString(this, "language"));
        }
    }

    public void nextPage(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
    public void nextPage2(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void changeToEnglish(View view) {
        showSaveLanguage(LanguageUtil.ENGLISH);
    }

    public void changeToChinese(View view) {
        showSaveLanguage(LanguageUtil.TRADITIONAL_CHINESE);
    }

    private void showSaveLanguage(String language){
        LanguageUtil.changeAppLanguage(this, language, Languagepage.class);
        SpUserUtils.putString(this, "language", language);
    }
}