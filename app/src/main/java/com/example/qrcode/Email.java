package com.example.qrcode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class Email extends AppCompatActivity {
    private Menu globalMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);


        Button startBtn = (Button) findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }

            protected void sendEmail() {
                Log.i("Send email", "");
                String[] TO = {""};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
                File root = Environment.getExternalStorageDirectory();
                String pathToMyAttachedFile = "temp/attachement.xml";
                File file = new File(root, pathToMyAttachedFile);
                if (!file.exists() || !file.canRead()) {
                    return;
                }

                Uri uri = Uri.fromFile(file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));


                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Email.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }



            }
        });









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