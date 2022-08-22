package com.example.httptest;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptest.adapter.MovieListAdapter;
import com.example.httptest.model.MovieModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ItemMoveCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperContract mAdapter;
    private MainActivity mainActivity;
    public ItemMoveCallback(MainActivity mainActivity, ItemTouchHelperContract adapter) {
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
        if (viewHolder.getAdapterPosition() < this.mainActivity.favoriteStringArrayList.size() && target.getAdapterPosition() < this.mainActivity.favoriteStringArrayList.size()) {
            MovieModel source = this.mainActivity.favoriteStringArrayList.get(viewHolder.getAdapterPosition());
            MovieModel targetMovie = this.mainActivity.favoriteStringArrayList.get(target.getAdapterPosition());
//            Log.d("onMove", " origin favorite" + source.getTicker() +" to " + targetMovie.getTicker());
            this.mainActivity.favoriteMemoList.set(viewHolder.getAdapterPosition(), source.getTicker());
            this.mainActivity.favoriteMemoList.set(target.getAdapterPosition(), targetMovie.getTicker());
            AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mainActivity.saveArrayList((ArrayList<String>) mainActivity.favoriteMemoList, "favoriteKey");
            }
        });
        }

        //TODO: REALLY NEED THIS? SAVE IN DISK
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                mainActivity.saveArrayList((ArrayList<String>) mainActivity.favoriteMemoList, "favoriteKey");
//            }
//        });
        Log.d("move finish", "favorite finish ");
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {


        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof MovieListAdapter.MyViewHolder) {
                MovieListAdapter.MyViewHolder myViewHolder=
                        (MovieListAdapter.MyViewHolder) viewHolder;
                mAdapter.onRowSelected(myViewHolder);
            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }
    @Override
    public void clearView(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof MovieListAdapter.MyViewHolder) {
            MovieListAdapter.MyViewHolder myViewHolder=
                    (MovieListAdapter.MyViewHolder) viewHolder;
            mAdapter.onRowClear(myViewHolder);
        }
    }

    public interface ItemTouchHelperContract {

        void onRowMoved(int fromPosition, int toPosition);
        void onRowSelected(MovieListAdapter.MyViewHolder myViewHolder);
        void onRowClear(MovieListAdapter.MyViewHolder myViewHolder);

    }

}

