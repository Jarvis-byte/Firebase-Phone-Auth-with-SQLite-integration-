package com.example.furniture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class EmptyRecylerView extends RecyclerView {
public View emptyView;
    private AdapterDataObserver emptyObserver=new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?>adapter=getAdapter();
            if (adapter.getItemCount() == 0)
            {
               emptyView.setVisibility(VISIBLE);
               EmptyRecylerView.this.setVisibility(GONE);
                Log.i("Empty ","Called");
            }
            else
            {
                emptyView.setVisibility(GONE);
                EmptyRecylerView.this.setVisibility(VISIBLE);
            }
            super.onChanged();
        }
    };

    public EmptyRecylerView(@NonNull Context context) {
        super(context);
    }

    public EmptyRecylerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecylerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();

        super.setAdapter(adapter);
    }
    public void setEmptyView(View emptyView)
    {
        this.emptyView=emptyView;
    }
}
