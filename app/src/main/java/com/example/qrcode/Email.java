package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class Email extends AppCompatActivity {
    private Menu globalMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        globalMenuItem = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        globalMenuItem.findItem(R.id.email).setVisible(false);
        switch (item.getItemId()) {
            case R.id.scan:
                Intent scan = new Intent(this, MainActivity.class);
                startActivity(scan);
                break;
            case R.id.part_inventory:
                Intent stillage = new Intent(this, Stillage.class);
                startActivity(stillage);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}