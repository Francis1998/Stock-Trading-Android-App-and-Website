package com.example.httptest.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptest.ItemMoveCallback;
import com.example.httptest.MainActivity;
import com.example.httptest.PortfolioItemMoveCallback;
import com.example.httptest.R;
import com.example.httptest.SearchActivity;
import com.example.httptest.model.MovieModel;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PortfolioListAdapter extends RecyclerView.Adapter<PortfolioListAdapter.MyViewHolder> implements PortfolioItemMoveCallback.ItemTouchHelperContract{
    private Context context;
    private List<MovieModel> movieList;
    private ItemClickListener clickListener;

    public PortfolioListAdapter(Context context, List<MovieModel> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public PortfolioListAdapter(Context context, List<MovieModel> movieList, ItemClickListener clickListener) {
        this.context = context;
        this.movieList = movieList;
        this.clickListener = clickListener;
    }

    public void setMovieList(List<MovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public List<MovieModel> getMovieList() {
        return movieList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.porfolio_recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        int holderposition = holder.getAdapterPosition();
        holder.portfolio_re_TickerView.setText(this.movieList.get(position).getTicker());
        holder.portfolio_re_SharesView.setText(this.movieList.get(position).getSharesNumber());
        String c = String.format(Locale.US, "%.2f", this.movieList.get(position).getC());
        if (c.equals("-0.00")) {
            c = "0.00";
        }
        String d = String.format(Locale.US, "%.2f", this.movieList.get(position).getD());
        if (d.equals("-0.00")) {
            d = "0.00";
        }
        String dp = String.format(Locale.US, "%.2f", this.movieList.get(position).getDp());
        if (dp.equals("-0.00")) {
            dp = "0.00";
        }

        String price = "$" + c;
        holder.portfolio_re_CurrentPriceView.setText(price);
        String change = "$" + d + " ( " + dp+ "% )";
        holder.portfolio_re_PriceChangeView.setText(change);
        holder.portfolio_re_PriceImageView.setVisibility(View.VISIBLE);
        if (this.movieList.get(position)!=null) {
            if (this.movieList.get(position).getD() >= 0.01) {
                holder.portfolio_re_PriceChangeView.setTextColor(Color.parseColor("#00CC00"));
                holder.portfolio_re_PriceImageView.setImageResource(R.drawable.trending_up);
                holder.portfolio_re_PriceImageView.setColorFilter(Color.parseColor("#00CC00"));
            } else if (this.movieList.get(position).getD() <= -0.01) {
                holder.portfolio_re_PriceChangeView.setTextColor(Color.RED);
                holder.portfolio_re_PriceImageView.setImageResource(R.drawable.trending_down);
                holder.portfolio_re_PriceImageView.setColorFilter(Color.RED);
            } else {
                holder.portfolio_re_PriceChangeView.setTextColor(Color.BLACK);
                holder.portfolio_re_PriceImageView.setVisibility(View.INVISIBLE);
            }
        }
        holder.portfolio_re_JumpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                intent.putExtra("query", movieList.get(holder.getAdapterPosition()).getTicker());
                context.startActivity(intent);
                ((MainActivity)context).saveAllData();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(this.movieList != null) {
            return this.movieList.size();
        }
        return 0;
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.movieList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.movieList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView portfolio_re_TickerView;
        TextView portfolio_re_SharesView;
        TextView portfolio_re_CurrentPriceView;
        TextView portfolio_re_PriceChangeView;
        ImageView portfolio_re_PriceImageView;
        Button portfolio_re_JumpView;
        View rowView;
        public MyViewHolder(View itemView) {
            super(itemView);
            rowView = itemView;
            portfolio_re_TickerView = (TextView)itemView.findViewById(R.id.portfolio_re_TickerView);
            portfolio_re_PriceImageView = (ImageView)itemView.findViewById(R.id.portfolio_re_PriceImageView);
            portfolio_re_SharesView = (TextView)itemView.findViewById(R.id.portfolio_re_SharesView);
            portfolio_re_CurrentPriceView = (TextView)itemView.findViewById(R.id.portfolio_re_CurrentPriceView);
            portfolio_re_PriceChangeView = (TextView)itemView.findViewById(R.id.portfolio_re_PriceChangeView);
            portfolio_re_JumpView = (Button)itemView.findViewById(R.id.portfolio_re_JumpView);
        }
    }


    public interface ItemClickListener{
        public void onMovieClick(MovieModel movie);
    }

    public void removeItem(int position) {
        this.movieList.remove(position);
        notifyItemRemoved(position);
    }
}
