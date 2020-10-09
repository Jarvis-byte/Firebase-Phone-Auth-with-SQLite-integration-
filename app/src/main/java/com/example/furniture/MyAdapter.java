package com.example.furniture;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniture.data.FurnitureContract.FurnitureEntry;
import com.example.furniture.data.furnituredbHelper;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
    private static final int VIEW_TYPE_OBJECT_VIEW = 1;
    private List<ListItem>listItems;
    private Context context;
    private furnituredbHelper mDbHelper;
    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ListItem listitem=listItems.get(position);
        holder.textName.setText(listitem.getName());
        holder.type.setText(listitem.getType());
        holder.price.setText("Rs\t"+listitem.getPrice());
        holder.discount.setText("Rs\t"+listitem.getDiscount());
holder.imageView.setImageBitmap(listitem.getImg());
        holder.textViewid.setText(listitem.getId());
holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int  position = holder.getLayoutPosition();
        Log.i("Position", String.valueOf(position));
        String name = holder.textName.getText().toString();

        Integer deletedRows = mDbHelper.deleteData(holder.textViewid.getText().toString());
    if (deletedRows>0) {

       }
        int newPosition = holder.getAdapterPosition();
        //SQLiteDatabase db=mDbHelper.getWritableDatabase();
//db.delete(FurnitureEntry.TABLE_NAME,FurnitureEntry._ID + "=" +holder.getAdapterPosition(),null);
      //  db.execSQL("DELETE * FROM "+ FurnitureEntry.TABLE_NAME +"\tWHERE\t"+FurnitureEntry._ID+"\t=\t"+ holder.getLayoutPosition() + ";");
   // db.execSQL(" Update "+  FurnitureEntry.TABLE_NAME + " Set " + FurnitureEntry._ID + "=" + FurnitureEntry._ID + "-1" + " Where " + FurnitureEntry._ID + ">" +holder.getAdapterPosition() + ";"  );
        Log.i("POSITION", String.valueOf(newPosition));
    // db.close();
        listItems.remove(newPosition);
        notifyItemRemoved(newPosition);
        notifyItemRangeChanged(newPosition, listItems.size());

    }
});

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (listItems.isEmpty()) {
            return VIEW_TYPE_EMPTY_LIST_PLACEHOLDER;
        } else {
            return VIEW_TYPE_OBJECT_VIEW;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView type;
        public TextView price;
        public TextView discount;
public ImageView imageView;
public ImageView delete;
public TextView textViewid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName=itemView.findViewById(R.id.Product_Name);
            type=itemView.findViewById(R.id.Variety);
            price=itemView.findViewById(R.id.Price);
            discount=itemView.findViewById(R.id.Discount);
            imageView=itemView.findViewById(R.id.imageView6);
            textViewid=itemView.findViewById(R.id.textViewid);
            Log.i("View Holder","View Holder Called");
            discount.setPaintFlags(discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            delete=itemView.findViewById(R.id.delete);
            mDbHelper = new furnituredbHelper(itemView.getContext());
        }
    }
}
