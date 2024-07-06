package com.fetch.androidtakehome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.R;
import com.fetch.androidtakehome.Models.Item;

import java.util.List;

public class ItemNameAdapter extends RecyclerView.Adapter<ItemNameAdapter.ItemNameViewHolder> {

    private List<Item> items;

    public ItemNameAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name_card, parent, false);
        return new ItemNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNameViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemNameViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;

        public ItemNameViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textview_namecard_title);
        }
    }
}