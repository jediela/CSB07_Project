package com.b07group4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginShopper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_shopper);
    }

    public void clickHome(View view){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

}