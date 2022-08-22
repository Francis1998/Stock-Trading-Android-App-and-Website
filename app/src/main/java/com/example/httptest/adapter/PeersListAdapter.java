package com.example.httptest.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.httptest.R;
import java.util.List;

public class PeersListAdapter  extends RecyclerView.Adapter<com.example.httptest.adapter.PeersListAdapter.MyViewHolder> {
    private Context context;
    private List<String> peersList;
    private ItemClickListener clickListener;

    public PeersListAdapter(Context context, List<String> peersList, com.example.httptest.adapter.PeersListAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.peersList = peersList;
        this.clickListener = clickListener;
    }

    public void setPeersList(List<String> peersList) {
        this.peersList = peersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public com.example.httptest.adapter.PeersListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.peersrecycler_row, parent, false);
        return new com.example.httptest.adapter.PeersListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.httptest.adapter.PeersListAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(this.peersList.get(position) + ",");
        holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onMovieClick(peersList.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        if(this.peersList != null) {
            return this.peersList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.urlRecyclerView);
        }
    }


    public interface ItemClickListener{
        public void onMovieClick(String movie);
    }
}


