package com.fetch.androidtakehome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.R;

import java.util.List;

/**
 * RecyclerView Adapter for displaying the name of objects belonging a list.
 * Uses DiffUtil for efficient updates when the data set changes.
 */
public class ItemNameAdapter extends RecyclerView.Adapter<ItemNameAdapter.ItemViewHolder> {
    private final List<Item> items; // List of items to display

    public ItemNameAdapter(List<Item> items) {
        this.items = items;
    }

    // Update the items in the adapter using DiffUtil for efficient changes
    public void updateItems(List<Item> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ItemDiffCallback(this.items, newItems));
        this.items.clear();
        this.items.addAll(newItems);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name_row, parent, false);
        return new ItemViewHolder(view);// Inflate the item layout and create the ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }// Return the size of the list

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;// TextView to display the item name

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textview_namerow_title);
        }
    }

    //DiffUtil.Callback class for more efficient updates
    private static class ItemDiffCallback extends DiffUtil.Callback {
        private final List<Item> oldList;// Old list of items
        private final List<Item> newList;// New list of items

        public ItemDiffCallback(List<Item> oldList, List<Item> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            // Check if items are the same based on their IDs
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            // Check if item contents are the same
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}

