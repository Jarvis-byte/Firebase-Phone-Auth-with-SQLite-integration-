package com.example.furniture.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.furniture.data.FurnitureContract.FurnitureEntry;
public class furnituredbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "furn1.db";

   public furnituredbHelper(Context context)
   {
       super(context,DATABASE_NAME,null,DATABASE_VERSION);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + FurnitureEntry.TABLE_NAME + " ("
                + FurnitureEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FurnitureEntry.COLUMN_PRODUCT_NAME+ " TEXT NOT NULL, "
                + FurnitureEntry.COLUMN_CATAGORY+ " TEXT NOT NULL, "
                + FurnitureEntry.COLUMN_PRICE + " TEXT NOT NULL, "
                + FurnitureEntry.KEY_IMG_URL + " BLOB,"
                + FurnitureEntry.DISCOUNT_PRICE + " Text NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Integer deleteData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.delete(FurnitureEntry.TABLE_NAME,FurnitureEntry._ID+" = ?", new String[]{id});
    }
}
