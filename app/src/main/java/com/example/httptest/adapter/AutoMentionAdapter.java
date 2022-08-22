package com.example.httptest.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AutoMentionAdapter extends ArrayAdapter<String> implements Filterable {
    public List<String> receiveData;
    public List<String> resultData;
    public int DataCount;
    public AutoMentionAdapter(int resource, @NonNull Context context) {
        super(context, resource);
        receiveData = new ArrayList<>();
        resultData = new ArrayList<>();
    }



    public AutoMentionAdapter(@NonNull Context context, int resource, List<String> receiveData, List<String> resultData) {
        super(context, resource);
        this.receiveData = receiveData;
        this.resultData = resultData;
    }

    public AutoMentionAdapter(@NonNull Context context, int resource, int textViewResourceId, List<String> receiveData, List<String> resultData) {
        super(context, resource, textViewResourceId);
        this.receiveData = receiveData;
        this.resultData = resultData;
    }

    @Override
    public int getCount() {
        int size = receiveData.size();
        return size;
    }



    public AutoMentionAdapter(@NonNull Context context, int resource, @NonNull String[] objects, List<String> receiveData, List<String> resultData) {
        super(context, resource, objects);
        this.receiveData = receiveData;
        this.resultData = resultData;
    }
    public void inputIntoAutoMentionList(List<String> data) {
        receiveData.clear();
        receiveData.addAll(data);
    }
    public AutoMentionAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects, List<String> receiveData, List<String> resultData) {
        super(context, resource, textViewResourceId, objects);
        this.receiveData = receiveData;
        this.resultData = resultData;
    }

    public AutoMentionAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, List<String> receiveData, List<String> resultData) {
        super(context, resource, objects);
        this.receiveData = receiveData;
        this.resultData = resultData;
    }
    @Nullable
    @Override
    public String getItem(int position) {
        String pos = receiveData.get(position);
        return pos;
    }
    public AutoMentionAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects, List<String> receiveData, List<String> resultData) {
        super(context, resource, textViewResourceId, objects);
        this.receiveData = receiveData;
        this.resultData = resultData;
    }

    public List<String> getReceiveData() {
        return receiveData;
    }

    public List<String> getResultData() {
        return resultData;
    }

    public int getDataCount() {
        return DataCount;
    }

    public void setDataCount(int dataCount) {
        DataCount = dataCount;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    results.values = receiveData;
                    resultData = receiveData;
                    results.count = receiveData.size();
                    resultData.toString();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//                Log.d("AutoMentionAdapter", "publishResults: " + results.values);
                if (results != null) {
                    DataCount = results.count;
                    notifyDataSetChanged();
//                    if (results.count > 0) {
//                        DataCount = results.count;
//                        notifyDataSetChanged();
//                    }
                } else {
                    DataCount = 0;
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public void setReceiveData(List<String> receiveData) {
        this.receiveData = receiveData;
    }

    public void setResultData(List<String> resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "AutoMentionAdapter{" +
                "receiveData=" + receiveData +
                ", resultData=" + resultData +
                '}';
    }
}
