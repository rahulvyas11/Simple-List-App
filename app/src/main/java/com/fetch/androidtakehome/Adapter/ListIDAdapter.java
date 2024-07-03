package com.fetch.androidtakehome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fetch.androidtakehome.R;

import java.util.List;

public class ListIDAdapter extends RecyclerView.Adapter<ListIDAdapter.ListIdViewHolder> {

    private List<Integer> listIds;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int listId);
    }

    public ListIDAdapter(List<Integer> listIds, OnItemClickListener listener) {
        this.listIds = listIds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListIdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listid_row, parent, false);
        return new ListIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListIdViewHolder holder, int position) {
        int listId = listIds.get(position);
        holder.listIdTextView.setText("List " + listId);
        holder.cardView.setOnClickListener(v -> listener.onItemClick(listId));
    }

    @Override
    public int getItemCount() {
        return listIds.size();
    }

    public static class ListIdViewHolder extends RecyclerView.ViewHolder {
        private final TextView listIdTextView;
        private final CardView cardView;

        public ListIdViewHolder(View itemView) {
            super(itemView);
            listIdTextView = itemView.findViewById(R.id.listName);
            cardView = itemView.findViewById(R.id.listCard);
        }
    }

    public void updateListIds(List<Integer> newListIds) {
        this.listIds = newListIds;
        notifyDataSetChanged();
    }
}
