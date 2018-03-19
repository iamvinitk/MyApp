package com.example.admin.myapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapp.R;
import com.example.admin.myapp.networking.DataFetch;

import static com.example.admin.myapp.utils.Constants.PICKFILE_RESULT_CODE;

public class PrintOrderActivity extends AppCompatActivity {
    String path = null;
    Button selectFile, submit;
    EditText noOfCopies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_order);
        submit = findViewById(R.id.apo_submit);
        selectFile = findViewById(R.id.apo_select_file);
        noOfCopies = findViewById(R.id.apo_no_of_copies);
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PrintOrderActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED & ContextCompat.checkSelfPermission(PrintOrderActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {


                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(PrintOrderActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseFile.setType("*/*");
                    chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                    startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataFetch uploadAsyncTask = new DataFetch();
                uploadAsyncTask.uploadFile(path, noOfCopies.getText().toString(), PrintOrderActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
//            Toast.makeText(PrintOrderActivity.this, String.valueOf(fullPhotoUri), Toast.LENGTH_LONG).show();
            assert fullPhotoUri != null;
//            String path = fullPhotoUri.getPath();
            path = getPath(fullPhotoUri);
//            Toast.makeText(PrintOrderActivity.this, path, Toast.LENGTH_LONG).show();
            selectFile.setText(path);

        }
    }

    public String getPath(Uri uri) {

        String path = null;
        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) {
            path = uri.getPath();
        } else {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }
}
