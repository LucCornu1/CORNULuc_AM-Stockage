package com.example.cornuluc_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // nom de fichier
    private static String fileName = null;

    final String storageState = Environment.getExternalStorageState();
    private static File file = null;

    private static FileOutputStream fos = null;
    private static FileInputStream fis = null;

    private static StringBuilder content = null;


    // Référence des vues
    private static EditText nameInput = null;
    private static Button saveButton = null;
    private static Button showButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            // file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        fos = new FileOutputStream(new File(file, fileName));
                        fos.write(nameInput.getText().toString().getBytes());
                        fos.close();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });


            showButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Ouverture d'un fichier en lecture
                    try {
                        fis = new FileInputStream(new File(file, fileName));
                        byte[] buffer = new byte[1024];
                        content = new StringBuilder();
                        while ((fis.read(buffer)) != -1) {
                            content.append(new String(buffer));
                        }
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                }
            });


        } else if (storageState.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            // Vous pouvez seulement lire sur le stockage externe

        } else if (storageState.equals(Environment.MEDIA_REMOVED)) {
            // Le stockage externe n'est pas disponible

        } else {
            // Autre cas

        }
    }

    protected void init() {
        nameInput = (EditText) findViewById(R.id.editTextTextPersonName);
        saveButton = (Button) findViewById(R.id.buttonSave);
        showButton = (Button) findViewById(R.id.buttonShow);
        fileName = (String) "test.txt";
    }
}