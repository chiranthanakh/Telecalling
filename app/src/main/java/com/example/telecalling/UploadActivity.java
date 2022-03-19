package com.example.telecalling;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {
    ImageView mToolbar;
    private final int CHOOSE_PDF_FROM_DEVICE = 1001;
    Button itemslist,racklist,userlist, btn_clear;
    Button btn_addfile;
    SqlDB sqlDB = new SqlDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mToolbar = findViewById(R.id.back_toolbar);
        mToolbar.setOnClickListener(view -> onBackPressed());

        btn_addfile = findViewById(R.id.btn_upload_file);

        btn_addfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getfilepath();
            }
        });
    }

    private void getfilepath() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,CHOOSE_PDF_FROM_DEVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Uri uri = data.getData();
            File originalFile = new File(FileUtilitys.getRealPath(this,uri));
            System.out.println("path======"+originalFile);
            String path1 = data.getData().getPath();
            String path2 = path1.replace("/document/raw:", "");
            Log.e("path", path2);
            System.out.println("path" + path2);

            if (data.getData().getPath().endsWith(".csv")) {

                if (data == null) {
                    Toast.makeText(getApplicationContext(), "no data in file", Toast.LENGTH_LONG).show();
                }
                if (requestCode == CHOOSE_PDF_FROM_DEVICE) {
                    try {
                        CSVReader reader = new CSVReader(new FileReader(originalFile));
                        String[] nextLine;

                        int dat = reader.readNext().length;


                            while ((nextLine = reader.readNext()) != null) {
                                // nextLine[] is an array of values from the line

                                Boolean isIncerted = sqlDB.insertData(nextLine[0].toLowerCase(), nextLine[1].toLowerCase(), "0","0");
                                if (isIncerted) {
                                    // Toast.makeText(UploadListActivity.this,"Imported successfully",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(UploadActivity.this, "User list not imported", Toast.LENGTH_LONG).show();
                                }

                                System.out.println(nextLine[0] + "," + nextLine[1]);
                            }

                        //Toast.makeText(UploadActivity.this, "user list readed successfully", Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        Toast.makeText(UploadActivity.this, "not uploaded :  " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            } else {

            }
        }
    }

}