package com.fetch.androidtakehome.Adapter;

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

public class ListIDAdapter extends RecyclerView.Adapter<ListIDAdapter.ListIDViewHolder> {

    private final List<Map.Entry<Integer, List<Item>>> itemsGrouped;
    private final List<Boolean> expandedStates;

    public ListIDAdapter(List<Item> items) {
        this.itemsGrouped = items.stream()
                .collect(Collectors.groupingBy(Item::getListId))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
        this.expandedStates = new ArrayList<>(itemsGrouped.size());
        for (int i = 0; i < itemsGrouped.size(); i++) {
            expandedStates.add(false);
        }
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
        holder.textView.setText("List " + entry.getKey());
        holder.itemCount.setText("Number of items: " + entry.getValue().size());

        boolean isExpanded = expandedStates.get(position);
        holder.expandButton.setOnClickListener(v -> {
            expandedStates.set(position, !isExpanded);
            notifyItemChanged(position);
        });


        holder.expandButton.setImageResource(isExpanded ? R.drawable.listid_minimize_24dp : R.drawable.listid_expand_24dp);
        holder.expandButton.setContentDescription(isExpanded ? "less" : "more");

        RecyclerView itemsRecyclerView = holder.itemsRecyclerView;
        itemsRecyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        ItemNameAdapter itemNameAdapter = new ItemNameAdapter(entry.getValue());
        itemsRecyclerView.setAdapter(itemNameAdapter);

    }

    @Override
    public int getItemCount() {
        return itemsGrouped.size();
    }
    public boolean isExpanded(int position) {
        return expandedStates.get(position);
    }

    public static class ListIDViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView itemCount;
        CardView cardView;
        ImageButton expandButton;
        RecyclerView itemsRecyclerView;

        public ListIDViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textview_listidcard_title);
            itemCount = itemView.findViewById(R.id.textview_listidcard_itemcount);
            cardView = itemView.findViewById(R.id.cardview_listid_content);
            expandButton = itemView.findViewById(R.id.imgbutton_listidcard_expand);
            itemsRecyclerView = itemView.findViewById(R.id.recyclerview_namecard);
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        }
    }
}
