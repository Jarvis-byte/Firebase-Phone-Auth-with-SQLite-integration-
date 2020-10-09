package com.example.furniture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.furniture.data.FurnitureContract.FurnitureEntry;
import com.example.furniture.data.furnituredbHelper;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    private Spinner spinner;
    Button btn_Save;
    EditText editTextTextProductName;
    EditText editTextTextPrice;
    EditText editTextTextDiscount;
    private String catagorySpinner=null;
    private furnituredbHelper mDbHelper;
ImageView imageView2;
    byte[] byteArray;
    Bitmap captureImage;
    private CoordinatorLayout Relative;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextTextProductName=findViewById(R.id.editTextTextProductName);
        editTextTextPrice=findViewById(R.id.editTextTextPrice);
        editTextTextDiscount=findViewById(R.id.editTextDiscount);
        imageView2=findViewById(R.id.imageView2);
          spinner=findViewById(R.id.spinner);
           spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,TypePicker.Type));
        ImageLoader();
       // Log.i("Going to","GOOOOOOOOOOOOO");
        displayDatabaseInfo();
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]
                    {
                            Manifest.permission.CAMERA

                    }, 100);

        }

    }

    private void ImageLoader()
    {
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
if (data !=null) {
    captureImage = (Bitmap) data.getExtras().get("data");
    imageView2.setImageBitmap(captureImage);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    captureImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byteArray = stream.toByteArray();
}
        }

    }

    private void displayDatabaseInfo() {
        mDbHelper=new furnituredbHelper(this);
        SQLiteDatabase db =mDbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+FurnitureEntry.TABLE_NAME,null);
        try {
//            Toast.makeText(this, c.getCount(), Toast.LENGTH_SHORT).show();
            Log.i("DATABASE CREATED", String.valueOf(c.getCount()));
        }
        finally {
            c.close();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet || TextUtils.isEmpty(editTextTextPrice.getText()) || TextUtils.isEmpty(editTextTextDiscount.getText())
                if (TextUtils.isEmpty(editTextTextProductName.getText()))
                {
                    editTextTextProductName.setError("Enter Product Name...");
                    editTextTextProductName.requestFocus();
                }
                else if (TextUtils.isEmpty(editTextTextPrice.getText()))
                {
                    editTextTextPrice.setError("Enter Discount Price...");
                    editTextTextPrice.requestFocus();
                }
                else if (TextUtils.isEmpty(editTextTextDiscount.getText()))
                {
                    editTextTextDiscount.setError("Enter Original...");
                    editTextTextDiscount.requestFocus();
                }
                else if (Integer.parseInt(editTextTextDiscount.getText().toString())<Integer.parseInt(editTextTextPrice.getText().toString()))
                {
                    editTextTextDiscount.setError("Original Price is More Than The Selling Price");
                    editTextTextDiscount.requestFocus();

                }
                else
                {
                    insertData();
                }



                return true;
                // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertData() {
        String nameString=editTextTextProductName.getText().toString().trim();
        catagorySpinner=spinner.getSelectedItem().toString();
            String Price=editTextTextPrice.getText().toString().trim();


        String Discount=editTextTextDiscount.getText().toString().trim();
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FurnitureEntry.COLUMN_PRODUCT_NAME,nameString);
    Log.i("CATAGORY",catagorySpinner);
        values.put(FurnitureEntry.COLUMN_CATAGORY,catagorySpinner);
        values.put(FurnitureEntry.COLUMN_PRICE,Price);
        values.put(FurnitureEntry.DISCOUNT_PRICE,Discount);
        values.put(FurnitureEntry.KEY_IMG_URL, byteArray);
        long newRowId =db.insert(FurnitureEntry.TABLE_NAME,null,values);
        if (newRowId==-1)
        {
            Toast.makeText(this, "Error While Saving", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(ProfileActivity.this,DisplayFurniture.class);
            startActivity(intent);

        }
    }
}