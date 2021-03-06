package com.example.qrcode;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Email extends AppCompatActivity {
    private Menu globalMenuItem;
    TextView text;


    String pokemon_str;


    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        text = findViewById(R.id.editText_data);


        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("/data/data/com.example.qrcode/files/22-01-22.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> pokemon = new ArrayList<>();
        while(scanner.hasNextLine()) {
            pokemon.add(scanner.nextLine().split(",")[1]);
        }
        scanner.close();

        for (int counter = 0; counter < pokemon.size(); counter++) {
            pokemon_str = pokemon_str + pokemon.get(counter) + "\n";
        }

        text.setText(pokemon_str);








    }




    public void createfile(View view) {
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CODE);












        String editTextData = "editText.ujhgkjhgkjhgfString()";

        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Storing the data in file with name as geeksData.txt
        File file = new File(folder, "geeksData.txt");
        writeTextData(file, editTextData);
    }
    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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