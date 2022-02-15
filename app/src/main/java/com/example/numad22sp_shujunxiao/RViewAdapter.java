package com.example.numad22sp_shujunxiao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RViewAdapter extends RecyclerView.Adapter<RViewHolder> {
    private final ArrayList<ItemCard> itemList;
    private ItemClickListener listener;
    private Context context;

    public RViewAdapter(Context context, ArrayList<ItemCard> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new RViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        ItemCard currentItem = itemList.get(position);
        holder.img.setImageResource(currentItem.getImg());
        holder.name.setText(currentItem.getName());
        holder.link.setText(currentItem.getLink());
        holder.checkBox.setChecked(currentItem.getStatus());
        //add underline to the link
        holder.link.setPaintFlags(holder.link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //open link
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriLink = holder.link.getText().toString();
                if(!uriLink.startsWith("http://") && !uriLink.startsWith("https://")) {
                    uriLink = "http://" + uriLink;
                }
                Uri uri = Uri.parse(uriLink);
                context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
