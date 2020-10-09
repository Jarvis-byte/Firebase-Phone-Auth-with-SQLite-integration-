package com.example.furniture.data;

import android.provider.BaseColumns;

public class FurnitureContract {
    public FurnitureContract() {}
    public static final class FurnitureEntry implements BaseColumns{
        public final static String TABLE_NAME="furn";

        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_PRODUCT_NAME="name";

        public final static String COLUMN_CATAGORY="catagories";

        public final static String COLUMN_PRICE = "price";

        public final static String DISCOUNT_PRICE="discount_price";

        public final static String KEY_IMG_URL = "ImgFavourite";

    }

}
