package com.example.numad22sp_shujunxiao;

import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class RViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView name;
    public TextView link;
    public CheckBox checkBox;

    public RViewHolder(View itemView, final ItemClickListener listener) {
        super(itemView);
        img = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.text_name);
        link = itemView.findViewById(R.id.text_link);
        checkBox = itemView.findViewById(R.id.checkBox);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        listener.onItemClick(position);
                    }
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCheckBoxClick(position);
                    }
                }
            }
        });
    }

}
