package com.example.httptest.adapter;

//public class NewsListAdapter {
//}
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.httptest.model.CompanyNewsModel;
import com.example.httptest.R;

import java.util.Date;
import java.util.List;

public class NewsListAdapter  extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    private Context context;
    private List<CompanyNewsModel> newsList;
    private ItemClickListener clickListener;

    public NewsListAdapter(Context context, List<CompanyNewsModel> newsList, ItemClickListener clickListener) {
        this.context = context;
        this.newsList = newsList;
        this.clickListener = clickListener;
    }

    public void setNewsList(List<CompanyNewsModel> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsrecycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.newsTitle.setText(this.newsList.get(position).getHeadline());
        Date date = new Date();
        Long elaspsedTime = (date.getTime()/1000 - this.newsList.get(position).getDatetime())/(60*60);
        String time = DateUtils.getRelativeTimeSpanString( this.newsList.get(position).getDatetime()*1000,date.getTime(), DateUtils.HOUR_IN_MILLIS).toString();
        holder.newsDateView.setText(time);//elaspsedTime.toString() + " hours ago"
        holder.newsSourceView.setText(this.newsList.get(position).getSource());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onMovieClick(newsList.get(holder.getAdapterPosition()));
            }
        });
//        Log.d("NewsListAdapter", "onBindViewHolder: " + this.newsList.get(position).getImage());
//                Log.d("NewsListAdapter", "onBindViewHolder: " + this.newsList.get(position).getHeadline());


        if (position == 0){
            holder.frameLayout.getLayoutParams().height = 940;
            holder.borderCardView.getLayoutParams().height = 900;
            holder.relativeLayout.getLayoutParams().height = 900;
            Glide.with(context)
                    .load(this.newsList.get(position).getImage())
                    .apply(new RequestOptions().override(1000, 600))
                    .into(holder.imageView);
            RelativeLayout.LayoutParams params_img = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params_img.setMargins(0,0,0,30);
            holder.imgCardView.setLayoutParams(params_img);
            holder.imgCardView.getLayoutParams().height = 600;
            holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//            holder.imageView.getLayoutParams().height = 600;

            RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.newsSourceView);
            params.setMargins(10,0,10,20);
//            holder.imageView.setAdjustViewBounds(true);
            holder.newsTitle.setLayoutParams(params);
            RelativeLayout.LayoutParams params2 =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.BELOW, R.id.newsImageUpperView);
            params2.setMargins(10,0,20,10);
//            holder.imageView.setAdjustViewBounds(true);
            holder.newsSourceView.setLayoutParams(params2);
            RelativeLayout.LayoutParams params3 =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            params3.addRule(RelativeLayout.BELOW, R.id.newsImageUpperView);
            params3.addRule(RelativeLayout.END_OF, R.id.newsSourceView);

//            holder.imageView.setAdjustViewBounds(true);
            holder.newsDateView.setLayoutParams(params3);
//            RelativeLayout.LayoutParams params =
//                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                            RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params.removeRule(RelativeLayout.START_OF);
//            params.addRule(RelativeLayout.BELOW, R.id.newsSourceView);
//            holder.newsTitle.setLayoutParams(params);


//            holder.imageView.getLayoutParams().width = "wrap_content";
        }else {
            Glide.with(context)
                    .load(this.newsList.get(position).getImage())
                    .apply(new RequestOptions().override(100, 100))
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if(this.newsList != null) {
            return this.newsList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsSourceView, newsDateView;
        ImageView imageView;
        CardView borderCardView, imgCardView;
        FrameLayout frameLayout;
        RelativeLayout relativeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView)itemView.findViewById(R.id.newsTitleView);
            imageView = (ImageView)itemView.findViewById(R.id.newsImageView);
            newsSourceView = (TextView)itemView.findViewById(R.id.newsSourceView);
            newsDateView = (TextView)itemView.findViewById(R.id.newsDateView);
            borderCardView = (CardView)itemView.findViewById(R.id.cardview_news_row);
            imgCardView = (CardView)itemView.findViewById(R.id.newsImageUpperView);
            frameLayout = (FrameLayout)itemView.findViewById(R.id.framelayout_news_row);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.newsRelativeLayout);
        }
    }


    public interface ItemClickListener{
        public void onMovieClick(CompanyNewsModel movie);
    }
}
