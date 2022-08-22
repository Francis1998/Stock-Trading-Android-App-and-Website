package com.example.httptest;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptest.adapter.PortfolioListAdapter;
import com.example.httptest.model.MovieModel;

import java.util.ArrayList;

public class PortfolioItemMoveCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperContract mAdapter;
    private MainActivity mainActivity;
    public PortfolioItemMoveCallback(MainActivity mainActivity, ItemTouchHelperContract adapter) {
        mAdapter = adapter;
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        if (mAdapter.hashCode() == this.mainActivity.adapter.hashCode()){ //favorite
//            Log.d("onMove", "favorite onMove " + mAdapter.hashCode());
//            Log.d("onMove", " origin favorite" + this.mainActivity.adapter.hashCode() +" portfolio" + this.mainActivity.portfolioAdapter.hashCode());
//            MovieModel source = this.mainActivity.favoriteStringArrayList.get(viewHolder.getAdapterPosition());
//            MovieModel targetMovie = this.mainActivity.favoriteStringArrayList.get(target.getAdapterPosition());
//            this.mainActivity.favoriteStringArrayList.set(viewHolder.getAdapterPosition(), targetMovie);
//            this.mainActivity.favoriteStringArrayList.set(target.getAdapterPosition(), source);
//            this.mainActivity.favoriteMemoList.set(viewHolder.getAdapterPosition(), source.getTicker());
//            this.mainActivity.favoriteMemoList.set(target.getAdapterPosition(), targetMovie.getTicker());
////            ArrayList<String> temp = new ArrayList<>();
////            for (int i = 0; i < this.mainActivity.favoriteStringArrayList.size(); i++) {
////                temp.add(this.mainActivity.favoriteStringArrayList.get(i).getTicker());
////            }
//            AsyncTask.execute(new Runnable() {
//                @Override
//                public void run() {
//                    mainActivity.saveArrayList((ArrayList<String>) mainActivity.favoriteMemoList, "favoriteKey");
//                    Log.d("save", "save" + "favoriteKey operation" + mainActivity.favoriteMemoList.toString());
//                }
//            });
//
//        } else{
//            Log.d("onMove", "onMove " + mAdapter.hashCode());
//            Log.d("onMove", "origin favorite" + this.mainActivity.adapter.hashCode() +" portfolio" + this.mainActivity.portfolioAdapter.hashCode());
//            MovieModel source = this.mainActivity.portfolioStringArrayList.get(viewHolder.getAdapterPosition());
//            MovieModel targetMovie = this.mainActivity.portfolioStringArrayList.get(target.getAdapterPosition());
//            this.mainActivity.portfolioStringArrayList.set(viewHolder.getAdapterPosition(), targetMovie);
//            this.mainActivity.portfolioStringArrayList.set(target.getAdapterPosition(), source);
//            this.mainActivity.portfolioMemoList.set(viewHolder.getAdapterPosition(), source.getTicker());
//            this.mainActivity.portfolioMemoList.set(target.getAdapterPosition(), targetMovie.getTicker());
////            ArrayList<String> temp = new ArrayList<>();
////            for (int i = 0; i < this.mainActivity.portfolioStringArrayList.size(); i++) {
////                temp.add(this.mainActivity.portfolioStringArrayList.get(i).getTicker());
////            }
//            AsyncTask.execute(new Runnable() {
//                @Override
//                public void run() {
//                    mainActivity.saveArrayList((ArrayList<String>) mainActivity.portfolioMemoList, "boughtStocksKey");
//                    Log.d("save", "save" + mainActivity.portfolioMemoList.toString());
//                }
//            });
//        }

//        Log.d("onMove", "onMove " + mAdapter.hashCode());
//        Log.d("onMove", "origin favorite" + this.mainActivity.adapter.hashCode() +" portfolio" + this.mainActivity.portfolioAdapter.hashCode());
        if (viewHolder.getAdapterPosition() < this.mainActivity.portfolioStringArrayList.size()){
            MovieModel source = this.mainActivity.portfolioStringArrayList.get(viewHolder.getAdapterPosition());
            MovieModel targetMovie = this.mainActivity.portfolioStringArrayList.get(target.getAdapterPosition());
//            Log.d("onMove", "source" + source.getTicker() + " target " + targetMovie.getTicker());
//        this.mainActivity.portfolioStringArrayList.set(viewHolder.getAdapterPosition(), source);
//        this.mainActivity.portfolioStringArrayList.set(target.getAdapterPosition(), targetMovie);
            mainActivity.portfolioMemoList.set(viewHolder.getAdapterPosition(), source.getTicker());
            mainActivity.portfolioMemoList.set(target.getAdapterPosition(), targetMovie.getTicker());
//            Log.d("onMove", "memo " + mainActivity.portfolioMemoList.toString());
            //TODO: REALLY NEED THIS? SAVE IN DISK
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    mainActivity.saveArrayList((ArrayList<String>) mainActivity.portfolioMemoList, "boughtStocksKey");
                }
            });
        }


//            @Override
//            public void run() {
//                mainActivity.saveArrayList((ArrayList<String>) mainActivity.portfolioMemoList, "boughtStocksKey");
//                Log.d("save", "save" + mainActivity.portfolioMemoList.toString());
//            }
//        });
//        this.mainActivity.portfolioAdapter.notifyDataSetChanged();
//            ArrayList<String> temp = new ArrayList<>();
//            for (int i = 0; i < this.mainActivity.portfolioStringArrayList.size(); i++) {
//                temp.add(this.mainActivity.portfolioStringArrayList.get(i).getTicker());
//            }
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                mainActivity.saveArrayList((ArrayList<String>) mainActivity.portfolioMemoList, "boughtStocksKey");
//                Log.d("save", "save" + mainActivity.portfolioMemoList.toString());
//            }
//        });

//        Log.d("onMove", "onMove" + mAdapter.hashCode());
//        Log.d("onMove", "origin" + this.mainActivity.adapter.hashCode() +" " + this.mainActivity.portfolioAdapter.hashCode());
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {


        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof PortfolioListAdapter.MyViewHolder) {
                PortfolioListAdapter.MyViewHolder myViewHolder=
                        (PortfolioListAdapter.MyViewHolder) viewHolder;
                mAdapter.onRowSelected(myViewHolder);
            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }
    @Override
    public void clearView(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof PortfolioListAdapter.MyViewHolder) {
            PortfolioListAdapter.MyViewHolder myViewHolder=
                    (PortfolioListAdapter.MyViewHolder) viewHolder;
            mAdapter.onRowClear(myViewHolder);
        }
    }

    public interface ItemTouchHelperContract {

        void onRowMoved(int fromPosition, int toPosition);
        void onRowSelected(PortfolioListAdapter.MyViewHolder myViewHolder);
        void onRowClear(PortfolioListAdapter.MyViewHolder myViewHolder);

    }

}

