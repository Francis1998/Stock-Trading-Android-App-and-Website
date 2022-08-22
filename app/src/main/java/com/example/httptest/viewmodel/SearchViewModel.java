package com.example.httptest.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.httptest.model.CompanyDesModel;
import com.example.httptest.model.CompanyNewsModel;
import com.example.httptest.model.HistoricalDataModel;
import com.example.httptest.model.LatestPriceModel;
import com.example.httptest.model.MainLatestPriceModel;
import com.example.httptest.model.MovieModel;
import com.example.httptest.model.SocialSentiment;
import com.example.httptest.model.autoCompleteModel;
import com.example.httptest.network.APIService;
import com.example.httptest.network.RetroInstance;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<HistoricalDataModel> summaryChartsData;
    private MutableLiveData<CompanyDesModel> companyDesData;
    private MutableLiveData<LatestPriceModel> latestPriceData;
    private MutableLiveData<MainLatestPriceModel> mainLatestPriceData;
    private MutableLiveData<MainLatestPriceModel> autoUpdateData;

    private MutableLiveData<List<String>> companyPeersData;
    private MutableLiveData<SocialSentiment> socialSentimentData;
    private MutableLiveData<autoCompleteModel> autoCompleteData;
    private MutableLiveData<List<CompanyNewsModel>> companyNewsData;

    public SearchViewModel(){
        summaryChartsData = new MutableLiveData<>();
        companyDesData = new MutableLiveData<>();
        latestPriceData = new MutableLiveData<>();
        companyPeersData = new MutableLiveData<>();
        socialSentimentData = new MutableLiveData<>();
        companyNewsData = new MutableLiveData<>();
        autoCompleteData = new MutableLiveData<>();
        mainLatestPriceData = new MutableLiveData<>();
        autoUpdateData = new MutableLiveData<>();
    }

    public MutableLiveData<List<CompanyNewsModel>> getCompanyNewsData() {
        return companyNewsData;
    }

    public MutableLiveData<HistoricalDataModel> getMoviesListObserver() {
//        Log.d("willget", moviesList);
        return summaryChartsData;
    }

    public MutableLiveData<MainLatestPriceModel> getAutoUpdateData() {
        return autoUpdateData;
    }

    public MutableLiveData<SocialSentiment> getSocialSentimentData() {
        return socialSentimentData;
    }

    public MutableLiveData<CompanyDesModel> getCompanyDesDataObserver() {
        return companyDesData;
    }

    public MutableLiveData<LatestPriceModel> getLatestPriceData() {
        return latestPriceData;
    }

    public MutableLiveData<List<String>> getCompanyPeersData() {
        return companyPeersData;
    }

    public MutableLiveData<autoCompleteModel> getAutoCompleteData() {
        return autoCompleteData;
    }

    public MutableLiveData<MainLatestPriceModel> getMainLatestPriceData() {
        return mainLatestPriceData;
    }

    public void makeApiCall(String query) {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);

//        Call<HistoricalDataModel> call = apiService.getSummaryCharts(query,"1631022248","1631627048");
//        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL And Date
//        call.enqueue(new Callback<HistoricalDataModel>() {
//            @Override
//            public void onResponse(Call<HistoricalDataModel> call, Response<HistoricalDataModel> response) {
//                Log.d("willresponse", String.valueOf(response.body()));
//                summaryChartsData.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<HistoricalDataModel> call, Throwable t) {
//                Log.d("willresponse", t.getMessage());
//                summaryChartsData.postValue(null);
//            }
//        });

        Call<CompanyDesModel> call1 = apiService.getCompanyDesData(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        call1.enqueue(new Callback<CompanyDesModel>() {
            @Override
            public void onResponse(Call<CompanyDesModel> call, Response<CompanyDesModel> response) {
                Log.d("willresponse", String.valueOf(response.body()));
                companyDesData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<CompanyDesModel> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                companyDesData.postValue(null);
            }
        });
        Call<LatestPriceModel> call2 = apiService.getcompanyLatestPrice(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        call2.enqueue(new Callback<LatestPriceModel>() {
            @Override
            public void onResponse(Call<LatestPriceModel> call, Response<LatestPriceModel> response) {
                Log.d("willPostPrice", response.body().toString());
                if (response.body().getD() != null) {
                    latestPriceData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LatestPriceModel> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                latestPriceData.postValue(null);
            }
        });

        Call<List<String>> call3 = apiService.getcompanyPeers(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        call3.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                Log.d("willPostPrice", response.body().toString());
//                if (response.body().getD() != null) {
                    companyPeersData.postValue(response.body());
//                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                companyPeersData.postValue(null);
            }
        });

        Call<SocialSentiment> call4 = apiService.getcompanySocialSentiment(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        call4.enqueue(new Callback<SocialSentiment>() {
            @Override
            public void onResponse(Call<SocialSentiment> call, Response<SocialSentiment> response) {
                Log.d("willPostPrice", response.body().toString());
                socialSentimentData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<SocialSentiment> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                socialSentimentData.postValue(null);
            }
        });

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        Date sevenDaysAgo = new Date(now.getTime() - (7 * 24 * 60 * 60 * 1000));
        String sevenDaysAgoDate = sdfDate.format(sevenDaysAgo);
//        Log.d("willPostPrice", strDate);
//        Log.d("willPostPrice", sevenDaysAgoDate);
        Call<List<CompanyNewsModel>> call5 = apiService.getcompanyNews(query,sevenDaysAgoDate, strDate);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL And Date
        call5.enqueue(new Callback<List<CompanyNewsModel>>() {
            @Override
            public void onResponse(Call<List<CompanyNewsModel>> call, Response<List<CompanyNewsModel>> response) {
                Log.d("willresponse", String.valueOf(response.body()));
                companyNewsData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CompanyNewsModel>> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                companyNewsData.postValue(null);
            }
        });
    }

    public void makeLatestPriceApiCall(String query){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<LatestPriceModel> call2 = apiService.getcompanyLatestPrice(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        call2.enqueue(new Callback<LatestPriceModel>() {
            @Override
            public void onResponse(Call<LatestPriceModel> call, Response<LatestPriceModel> response) {
                Log.d("willPostPrice", response.body().toString());
                if (response.body().getD() != null) {
                    latestPriceData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LatestPriceModel> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                latestPriceData.postValue(null);
            }
        });
    }

    public void autoCompleteApiCall(String query) {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<autoCompleteModel> call4 = apiService.getautoCompleteList(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        call4.enqueue(new Callback<autoCompleteModel>() {
            @Override
            public void onResponse(Call<autoCompleteModel> call, Response<autoCompleteModel> response) {
                Log.d("willPostPrice", response.body().toString());
                autoCompleteData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<autoCompleteModel> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                autoCompleteData.postValue(null);
            }
        });
    }
    public void favoriteApiCall(String query) {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<MainLatestPriceModel> callf = apiService.getcompanyMainLatestPrice(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        callf.enqueue(new Callback<MainLatestPriceModel>() {
            @Override
            public void onResponse(Call<MainLatestPriceModel> call, Response<MainLatestPriceModel> response) {
                Log.d("willMainPostPrice", response.body().toString());
                mainLatestPriceData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MainLatestPriceModel> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                mainLatestPriceData.postValue(null);
            }
        });
    }

    public void autoUpdateApiCall(String query) {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<MainLatestPriceModel> callf = apiService.getcompanyAutoUpdateLatestPrice(query);
        //TODO: CHANGE AAPL TO YOUR OWN STOCK SYMBOL
        callf.enqueue(new Callback<MainLatestPriceModel>() {
            @Override
            public void onResponse(Call<MainLatestPriceModel> call, Response<MainLatestPriceModel> response) {
                Log.d("willUpdatePostPrice", response.body().toString());
                autoUpdateData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MainLatestPriceModel> call, Throwable t) {
                Log.d("willresponse", t.getMessage());
                autoUpdateData.postValue(null);
            }
        });
    }
}
