package com.fetch.androidtakehome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RecyclerView Adapter for displaying items grouped by their list IDs.
 * Supports expanding and collapsing the grouped items for better user experience.
 */
public class ListIDAdapter extends RecyclerView.Adapter<ListIDAdapter.ListIDViewHolder> {
    private final List<Map.Entry<Integer, List<Item>>> itemsGrouped;// List of grouped items
    private final List<Boolean> expandedStates;// List to track expanded state of each group
    private final Context context;

    public ListIDAdapter(Context context, List<Item> items) {
        this.context = context;
        this.itemsGrouped = groupAndSortItems(items);
        this.expandedStates = new ArrayList<>(itemsGrouped.size());
        for (int i = 0; i < itemsGrouped.size(); i++) {
            expandedStates.add(false);
        }
    }

    // Method to group and sort items by their list IDs
    public static List<Map.Entry<Integer, List<Item>>> groupAndSortItems(List<Item> items) {
        return items.stream()
                .collect(Collectors.groupingBy(Item::getListId))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public ListIDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listid_card, parent, false);
        return new ListIDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListIDViewHolder holder, int position) {
        Map.Entry<Integer, List<Item>> entry = itemsGrouped.get(position);

        // Set the list name and item count
        holder.listName.setText(context.getString(R.string.listidcard_title, entry.getKey()));
        holder.itemCount.setText(context.getString(R.string.listidcard_itemcount, entry.getValue().size()));

        // Set the expand/collapse button state and functionality
        boolean isExpanded = expandedStates.get(position);
        holder.expandButton.setImageResource(isExpanded ? R.drawable.listid_minimize_24dp : R.drawable.listid_expand_24dp);
        holder.expandButton.setContentDescription(isExpanded ? "less" : "more");

        // Toggle the expanded state when the expand button is clicked
        holder.expandButton.setOnClickListener(v -> {
            expandedStates.set(position, !isExpanded);
            notifyItemChanged(position);
        });

        // Show or hide the RecyclerView based on the expanded state
        holder.itemsRecyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Update the items in the nested RecyclerView
        holder.itemNameAdapter.updateItems(entry.getValue());
    }

    @Override
    public int getItemCount() {
        return itemsGrouped.size();
    }

    // ViewHolder class to hold item views
    public static class ListIDViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        TextView itemCount;
        CardView listCardView;
        ImageButton expandButton;
        RecyclerView itemsRecyclerView;
        ItemNameAdapter itemNameAdapter;

        public ListIDViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.textview_listidcard_title);
            itemCount = itemView.findViewById(R.id.textview_listidcard_itemcount);
            listCardView = itemView.findViewById(R.id.cardview_listid_content);
            expandButton = itemView.findViewById(R.id.imgbutton_listidcard_expand);
            itemsRecyclerView = itemView.findViewById(R.id.recyclerview_namecard);
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            itemNameAdapter = new ItemNameAdapter(new ArrayList<>());
            itemsRecyclerView.setAdapter(itemNameAdapter);
        }
    }
}
