package com.example.furniture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.furniture.data.FurnitureContract.FurnitureEntry;
import com.example.furniture.data.furnituredbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DisplayFurniture extends AppCompatActivity {
    private RecyclerView recyclerView;
    private furnituredbHelper  mDbHelper;
    List<ListItem>listItems;
    private MyAdapter adapter;
    private CoordinatorLayout Relative;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_furniture);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        listItems=new ArrayList<>();
        Relative=findViewById(R.id.Relative);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DisplayFurniture.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new furnituredbHelper(this);
        displayDatabaseInfo();

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(listItems.size() == 0)
            {
          //  Toast.makeText(this, " Empty...Enter Something ", Toast.LENGTH_SHORT).show();
                  showSnackbar();
        }

        displayDatabaseInfo();

    }

    private void showSnackbar() {
        Snackbar snackbar=Snackbar.make(Relative,"Nothing To Show",Snackbar.LENGTH_INDEFINITE)
                .setAction("Add New", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(DisplayFurniture.this,ProfileActivity.class);
                        startActivity(intent);
                    }
                })
                .setActionTextColor(Color.RED);
//        View snackView=snackbar.getView();
//        TextView textView = snackView.findViewById(android.support.v4.graphics.R.id.snackbar_);
        snackbar.show();
    }

    private void displayDatabaseInfo()
    {
       // Log.i("Caleed","ok");
        listItems.clear();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String []projection={
                FurnitureEntry._ID,
                FurnitureEntry.COLUMN_PRODUCT_NAME,
                FurnitureEntry.COLUMN_CATAGORY,
                FurnitureEntry.COLUMN_PRICE,
                FurnitureEntry.DISCOUNT_PRICE,
                FurnitureEntry.KEY_IMG_URL
        };

        Cursor cursor=db.query(

                FurnitureEntry.TABLE_NAME
                ,projection
                ,null,
                null,
                null
                ,null,
                null
        );
        int idColumnIndex =cursor.getColumnIndex(FurnitureEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(FurnitureEntry.COLUMN_PRODUCT_NAME);
        int typeColumnIndex=cursor.getColumnIndex(FurnitureEntry.COLUMN_CATAGORY);
        int priceColumnIndex=cursor.getColumnIndex(FurnitureEntry.COLUMN_PRICE);
        int discountColumnIndex=cursor.getColumnIndex(FurnitureEntry.DISCOUNT_PRICE);
        int imgColumnIndex=cursor.getColumnIndex(FurnitureEntry.KEY_IMG_URL);
        int id=cursor.getColumnIndex(FurnitureEntry._ID);
        while(cursor.moveToNext())
        {
         String name=cursor.getString(nameColumnIndex);
         String typeColumn=cursor.getString(typeColumnIndex);
         String priceColumn=cursor.getString(priceColumnIndex);
         String discountColumn=cursor.getString(discountColumnIndex);
         String idColumn =cursor.getString(id);
            byte[] imgByte=cursor.getBlob(imgColumnIndex);
if (imgByte != null)
{
    bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
}
else
{
    bitmap=BitmapFactory.decodeResource(DisplayFurniture.this.getResources(),
            R.drawable.ic_baseline_photo_camera_24);
}

            ListItem item=new ListItem(
                    name,
                    typeColumn,
                    priceColumn,
                    discountColumn,
                    bitmap,
                    idColumn
            );

      listItems.add(item);
      adapter=new MyAdapter(listItems,getApplicationContext());
      recyclerView.setAdapter(adapter);
        int count= adapter.getItemCount();
        Log.i("Count List", String.valueOf(count));
        }

    }
}