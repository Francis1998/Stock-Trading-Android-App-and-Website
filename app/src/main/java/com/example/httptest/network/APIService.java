package com.example.httptest.network;

import com.example.httptest.model.CompanyDesModel;
import com.example.httptest.model.CompanyNewsModel;
import com.example.httptest.model.HistoricalDataModel;
import com.example.httptest.model.LatestPriceModel;
import com.example.httptest.model.MainLatestPriceModel;
import com.example.httptest.model.MovieModel;
import com.example.httptest.model.SocialSentiment;
import com.example.httptest.model.autoCompleteModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("companyDesTest/{ticker}")
    Call<List<MovieModel>> getMovieList(@Path("ticker") String ticker);

    @GET("companyDes/{ticker}")
    Call<CompanyDesModel> getCompanyDesData(@Path("ticker") String ticker);

    @GET("companyHisData/{ticker}/5/{startTimeStamp}/{endTimeStamp}")
    Call<HistoricalDataModel> getSummaryCharts(@Path("ticker") String ticker, @Path("startTimeStamp") String startTimeStamp, @Path("endTimeStamp") String endTimeStamp);

    @GET("companyLatestPrice/{ticker}")
    Call<LatestPriceModel> getcompanyLatestPrice(@Path("ticker") String ticker);

    @GET("companyMainLatestPrice/{ticker}")
    Call<MainLatestPriceModel> getcompanyMainLatestPrice(@Path("ticker") String ticker);

    @GET("companyAutoUpdateLatestPrice/{ticker}")
    Call<MainLatestPriceModel> getcompanyAutoUpdateLatestPrice(@Path("ticker") String ticker);

    @GET("companyPeers/{ticker}")
    Call<List<String>> getcompanyPeers(@Path("ticker") String ticker);

    @GET("companySocialSentiment/{ticker}")
    Call<SocialSentiment> getcompanySocialSentiment(@Path("ticker") String ticker);

    @GET("companyNews/{ticker}/{startTimeStamp}/{endTimeStamp}")
    Call<List<CompanyNewsModel>> getcompanyNews(@Path("ticker") String ticker, @Path("startTimeStamp") String startTimeStamp, @Path("endTimeStamp") String endTimeStamp);

    @GET("autoComplete/{ticker}")
    Call<autoCompleteModel> getautoCompleteList(@Path("ticker") String ticker);
}
