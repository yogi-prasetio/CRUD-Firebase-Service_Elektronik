package com.service.elektronik.sarana;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.service.elektronik.sarana.database.DBAddService;
import com.service.elektronik.sarana.database.DBReadService;
import com.service.elektronik.sarana.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add_customer){
            startActivity(new Intent(this, DBAddService.class));
        } else if (item.getItemId()==R.id.view_data){
            startActivity(new Intent(this, DBReadService.class));
        } else if (item.getItemId()==R.id.logout){
            startActivity(new Intent(this, LoginActivity.class));
        }
        return true;
    }
}