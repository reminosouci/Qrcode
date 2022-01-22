package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// implements onClickListener for the onclick behaviour of button
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    Button scanBtn, addBtn;
    EditText PO, WO, concScanBar, note;
    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
    String fileDate = sdf.format(new Date()) + ".csv";
    String[] kit_type = { "kit n/a", "kit 1", "kit 2", "kit 3", "kit 4", "kit 5", "kit 6", "kit 7", "kit 8", "kit 9", "kit 10", "kit 11", "kit 12", "kit 13", "kit 14", "kit 15" };
    String kit_type_st,topbottom;
    CheckBox CBquanrantine, CBopened;
    private Menu globalMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PO = findViewById(R.id.PO_text);
        WO = findViewById(R.id.WO_text);
        note = findViewById(R.id.note_text);
        concScanBar = findViewById(R.id.multiLine);
        CBquanrantine = findViewById(R.id.cbquarantine);
        CBopened = findViewById(R.id.cbopened);


        // referencing and initializing
        // the button and textviews
        scanBtn = findViewById(R.id.scanBtn);
        addBtn = findViewById(R.id.addBtn);
        // adding listener to the button
        scanBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

        Spinner spin = findViewById(R.id.sp_kit);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kit_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);




        // This will get the radiogroup
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.radGroupTopBottom);
        // This will get the radiobutton in the radiogroup that is checked
        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        // This overrides the radiogroup onCheckListener
        rGroup.setOnCheckedChangeListener(this);




        File file = new File(fileDate);
        if (!file.exists()) {
            try {
                FileOutputStream fileout = openFileOutput(fileDate, MODE_APPEND);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write("");
                outputWriter.close();
                Toast.makeText(getBaseContext(), fileDate + " file created successfully!",Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanBtn:
                // we need to create the object
                // of IntentIntegrator class
                // which is the class of QR library
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
                break;
            case R.id.addBtn:
                // append csv file
                try {
                    FileOutputStream fileout = openFileOutput(fileDate, MODE_APPEND);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    char Q;
                    boolean quarantineischecked = ((CheckBox) findViewById(R.id.cbquarantine)).isChecked();
                    if (quarantineischecked) {
                        Q = 'Q';
                        Toast.makeText(getBaseContext(), "Q applied",Toast.LENGTH_SHORT).show();
                    } else {
                        Q = '-';
                        Toast.makeText(getBaseContext(), "Q not applied",Toast.LENGTH_SHORT).show();
                    }
                    char O;
                    boolean openedischecked = ((CheckBox) findViewById(R.id.cbopened)).isChecked();
                    if (openedischecked) {
                        O = 'O';
                        Toast.makeText(getBaseContext(), "O applied",Toast.LENGTH_SHORT).show();
                    } else {
                        O = '-';
                        Toast.makeText(getBaseContext(), "O not applied",Toast.LENGTH_SHORT).show();
                    }


                    String text;
                    text = kit_type_st + "," + note.getText().toString() + "," + WO.getText().toString() + "," + PO.getText().toString() + "," + topbottom + "," + Q + "," + O + "\n";



                    outputWriter.write(text);
                    outputWriter.close();
                    //Toast.makeText(getBaseContext(), "File saved successfully!",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

        }





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                String codescanned = intentResult.getContents().toString();
                String[] codescannedlist = codescanned.split("\\$");
                try {
                    WO.setText(codescannedlist[0]);
                    PO.setText(codescannedlist[1]);
                    note.setText(codescannedlist[2]);
                } catch (Exception e) {
                    System.out.println("Something went wrong.");
                }
                concScanBar.setText(concScanBar.getText().toString() + "\n" + intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);
        if (item != null) {
            kit_type_st = item.toString();
            Toast.makeText(MainActivity.this, item.toString(),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Selected",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(MainActivity.this, "NOT Selected",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton button = radioGroup.findViewById(i);
        if(button != null && i != -1)
        {
            topbottom = (String) button.getText();
            Toast.makeText(MainActivity.this, button.getText()+"\t is selected", Toast.LENGTH_SHORT).show();
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
        globalMenuItem.findItem(R.id.scan).setVisible(false);
        switch (item.getItemId()) {
            case R.id.email:
                Intent email = new Intent(this, Email.class);
                startActivity(email);
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