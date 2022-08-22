package com.example.httptest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.httptest.adapter.NewsListAdapter;
import com.example.httptest.adapter.PeersListAdapter;
import com.example.httptest.adapter.ViewPagerAdapter;
import com.example.httptest.model.CompanyDesModel;
import com.example.httptest.model.CompanyNewsModel;
import com.example.httptest.model.HistoricalDataModel;
import com.example.httptest.model.LatestPriceModel;
import com.example.httptest.model.SocialSentiment;
import com.example.httptest.viewmodel.SearchViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SearchActivity extends AppCompatActivity implements PeersListAdapter.ItemClickListener, NewsListAdapter.ItemClickListener {
    private SearchViewModel searchViewModel;
    private PeersListAdapter adapter;
    private List<String> peersList;
    private NewsListAdapter newsAdapter;
    private List<CompanyNewsModel> newsList;
    //datamodel
    private HistoricalDataModel summaryChartsData;
    private CompanyDesModel companyDesData;
    private LatestPriceModel latestPriceData;
    private List<String> companyPeersData;
    private SocialSentiment socialSentimentData;
    // store
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String favoriteKey = "favoriteKey"; // Set<string>
    public static final String netWorthKey = "netWorthKey";
    public static final String cashBalanceKey = "cashBalanceKey";
    public static final String boughtStocksKey = "boughtStocksKey";
    public static final String nameKey = "_nameKey";
    //UI
    private ImageView firstCompanyImageView, firstPriceImageView;
    TextView firstTickerView, firstNameView, firstPriceView, firstPriceChangeView, StatsLeftOView, StatsLeftLView, StatsRightHView,
            StatsRightPView, IPOView, IndustryView, WebUrlView, PeersView, InsightTitleView, TotalMentionsRedditView, TotalMentionsTwitterView,
            NegativeMentionsRedditView, NegativeMentionsTwitterView, PositiveMentionsRedditView, PositiveMentionsTwitterView, action_bar_title,
            sharedOwnedValueView, AvgCostValueView, totalCostValueView, changeValueView, marketValueView;
    private Menu menu;
    private String query;
    private Button stockTradeButton;
    private ProgressBar spinner2;

    double totalMoney;
    int quantity;
    double totalCost;
    double avgCostPerShare;
    double change;
    double marketValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(mypreference, MODE_PRIVATE);
        if (!sharedpreferences.contains(favoriteKey)) {
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putStringSet(favoriteKey, new HashSet<>());
//            editor.apply();
            saveArrayList( new ArrayList<String>(), favoriteKey);
//            Log.d("favoriteKey", "onCreate generate new favoriteKey");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String query = getIntent().getStringExtra("query");//给liveData赋值
        this.query = query;
//        Log.d("SearchActivity", "onCreate: " + query);
        ((TextView) findViewById(R.id.TestView)).setText(query);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        getSupportActionBar().getCustomView().setPadding(0,0,0,0);
//        Toolbar parent =(Toolbar) getSupportActionBar().getCustomView().getParent();
//        parent.setContentInsetsAbsolute(0,0);
//        parent.setPadding(0,0,0,0);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(query);
        firstTickerView = findViewById(R.id.firstTickerView);
        firstNameView = findViewById(R.id.firstNameView);
        firstCompanyImageView = findViewById(R.id.firstCompanyImageView);
        firstPriceView = findViewById(R.id.firstPriceView);
        firstPriceChangeView = findViewById(R.id.firstPriceChangeView);
        firstPriceImageView = findViewById(R.id.firstPriceImageView);
        StatsLeftOView = findViewById(R.id.StatsLeftOView);
        StatsLeftLView = findViewById(R.id.StatsLeftLView);
        StatsRightHView = findViewById(R.id.StatsRightHView);
        StatsRightPView = findViewById(R.id.StatsRightPView);
        IPOView = findViewById(R.id.IPOValueView);
        IndustryView = findViewById(R.id.IndustryValueView);
        WebUrlView = findViewById(R.id.WebUrlView);
        PeersView = findViewById(R.id.PeersTagView);
        InsightTitleView = findViewById(R.id.InsightTitleView);
        TotalMentionsRedditView = findViewById(R.id.TotalMentionsRedditView);
        TotalMentionsTwitterView = findViewById(R.id.TotalMentionsTwitterView);
        NegativeMentionsRedditView = findViewById(R.id.NegativeMentionsRedditView);
        NegativeMentionsTwitterView = findViewById(R.id.NegativeMentionsTwitterView);
        PositiveMentionsRedditView = findViewById(R.id.PositiveMentionsRedditView);
        PositiveMentionsTwitterView = findViewById(R.id.PositiveMentionsTwitterView);
        stockTradeButton = findViewById(R.id.stockTradeButton);
        marketValueView = findViewById(R.id.marketValueView);
        changeValueView = findViewById(R.id.changeValueView);
        totalCostValueView = findViewById(R.id.totalCostValueView);
        AvgCostValueView = findViewById(R.id.AvgCostValueView);
        sharedOwnedValueView = findViewById(R.id.sharedOwnedValueView);
        spinner2 = (ProgressBar)findViewById(R.id.progressBar2);


//        action_bar_title = findViewById(R.id.action_bar_title);
//        action_bar_title.setText(query);
        stockTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SearchActivity.this);
                dialog.setContentView(R.layout.stock_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                window.setLayout(1000, RelativeLayout.LayoutParams.WRAP_CONTENT);

                final Dialog messageDialog = new Dialog(SearchActivity.this);
                messageDialog.setContentView(R.layout.stock_afteroperate_dialog);
                messageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window1 = messageDialog.getWindow();
                window1.setLayout(1000, RelativeLayout.LayoutParams.WRAP_CONTENT);

                TextView stock_sourceTextView = (TextView) dialog.findViewById(R.id.stock_sourceTextView);
                stock_sourceTextView.setText("Trade " + companyDesData.getName() + " shares");
                EditText stockNumberTextView = (EditText) dialog.findViewById(R.id.stockNumberTextView);
                stockNumberTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                        Log.d("onTextChanged", "onTextChanged: " + charSequence + " " + i + " " + i1 + " " + i2);
                        TextView stockTradeTotalPriceTextView = (TextView) dialog.findViewById(R.id.stockTradeTotalPriceTextView);
                        String price = "$" + String.format(Locale.US, "%.2f", latestPriceData.getC());
                        if (charSequence.length() == 0 || (charSequence.length() == 1 && charSequence.charAt(0) == '-')) {
                            stockTradeTotalPriceTextView.setText("0*" + price + "/share = 0.00");
                        } else {
//                            Log.d("onTextChanged", "onTextChanged: " + charSequence.toString());
                            String totalNumber = String.format(Locale.US, "%.1f", Double.parseDouble(charSequence.toString()));
                            String totalPrice = String.format(Locale.US, "%.2f", Float.parseFloat(totalNumber) * latestPriceData.getC());
                            stockTradeTotalPriceTextView.setText(totalNumber + "*" + price + "/share = " + totalPrice);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                TextView stockTradeTotalPriceTextView = (TextView) dialog.findViewById(R.id.stockTradeTotalPriceTextView);
                String price = "$" + String.format(Locale.US, "%.2f", latestPriceData.getC());
                stockTradeTotalPriceTextView.setText("0*" + price + "/shares = 0.00");
                float remainMoneyText = sharedpreferences.getFloat(cashBalanceKey, 25000.00f);
                String remainPrice = "$" + String.format(Locale.US, "%.2f", remainMoneyText);
                TextView stockRemainMoneyTextView = (TextView) dialog.findViewById(R.id.stockRemainMoneyTextView);
                stockRemainMoneyTextView.setText(remainPrice + " to buy " + companyDesData.getTicker());
                Button stockBuyButton = (Button) dialog.findViewById(R.id.stockBuyButton);

                TextView flag_messageTextView = (TextView) messageDialog.findViewById(R.id.flag_messageTextView);
                stockBuyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float remainMoney = sharedpreferences.getFloat(cashBalanceKey, 25000.00f);
                        double currentPrice = latestPriceData.getC();
                        if (stockNumberTextView.getText().toString().length() == 0 || stockNumberTextView.getText().toString().substring(1).contains("-")) {
                            Toast.makeText(SearchActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                        } else if (stockNumberTextView.getText().toString().charAt(0) == '-' || (stockNumberTextView.getText().toString().charAt(0) == '0' && stockNumberTextView.getText().toString().length() == 1)) {
                            Toast.makeText(SearchActivity.this, "Cannot buy non-positive shares", Toast.LENGTH_SHORT).show();
                        } else if (Float.parseFloat(stockNumberTextView.getText().toString()) * latestPriceData.getC() > remainMoney) {
                            Toast.makeText(SearchActivity.this, "Not enough money to buy", Toast.LENGTH_SHORT).show();
                        } else if (!getArrayList(boughtStocksKey).contains(companyDesData.getTicker())) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            ArrayList<String> string_set = getArrayList(boughtStocksKey);
                            ArrayList<String> string_set_copy = new ArrayList<String>(string_set);
                            string_set_copy.add(companyDesData.getTicker());
                            saveArrayList(string_set_copy, boughtStocksKey);

                            totalMoney = remainMoney - Float.parseFloat(stockNumberTextView.getText().toString()) * latestPriceData.getC();
                            quantity = Integer.parseInt(stockNumberTextView.getText().toString());
                            totalCost = latestPriceData.getC() * quantity;
                            avgCostPerShare = latestPriceData.getC();
                            change = 0.00f;
                            marketValue = latestPriceData.getC() * quantity;
//                            Log.d("stockchange", "detail: " + latestPriceData.getC() + " " + quantity + " " + totalCost + " " + avgCostPerShare);
                            editor.putFloat(cashBalanceKey, (float) totalMoney);
                            editor.putInt(companyDesData.getTicker() + "_quantity", quantity);
                            editor.putFloat(companyDesData.getTicker() + "_totalCost", (float) totalCost);
                            editor.putFloat(companyDesData.getTicker() + "_avgCostPerShare", (float) avgCostPerShare);
                            editor.putFloat(companyDesData.getTicker() + "_change", (float) change);
                            editor.putFloat(companyDesData.getTicker() + "_marketValue", (float) marketValue);
                            editor.apply();
                            dialog.dismiss();
                            if (quantity == 1){
                                flag_messageTextView.setText("You have successfully bought " + stockNumberTextView.getText().toString() + "\nshare of " + companyDesData.getTicker());
                            } else {
                                flag_messageTextView.setText("You have successfully bought " + stockNumberTextView.getText().toString() + "\nshares of " + companyDesData.getTicker());
                            }
                            messageDialog.show();
                            changeStockView();
                        } else if (getArrayList(boughtStocksKey).contains(companyDesData.getTicker())) {

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            quantity = Integer.parseInt(stockNumberTextView.getText().toString());
                            totalMoney = remainMoney - Float.parseFloat(stockNumberTextView.getText().toString()) * latestPriceData.getC();
                            int remainQuantity = sharedpreferences.getInt(companyDesData.getTicker() + "_quantity", 0) + quantity;
                            totalCost = sharedpreferences.getFloat(companyDesData.getTicker() + "_totalCost", 0) + latestPriceData.getC() * quantity;
                             avgCostPerShare = totalCost / remainQuantity;
                             change = (latestPriceData.getC() - avgCostPerShare) * remainQuantity;
                             marketValue = latestPriceData.getC() * remainQuantity;
                            editor.putFloat(cashBalanceKey, (float) totalMoney);
                            editor.putInt(companyDesData.getTicker() + "_quantity", remainQuantity);
                            editor.putFloat(companyDesData.getTicker() + "_totalCost", (float) totalCost);
                            editor.putFloat(companyDesData.getTicker() + "_avgCostPerShare", (float) avgCostPerShare);
                            editor.putFloat(companyDesData.getTicker() + "_change", (float) change);
                            editor.putFloat(companyDesData.getTicker() + "_marketValue", (float) marketValue);
                            editor.apply();
                            dialog.dismiss();
                            if (quantity == 1){
                                flag_messageTextView.setText("You have successfully bought " + stockNumberTextView.getText().toString() + "\nshare of " + companyDesData.getTicker());
                            } else {
                                flag_messageTextView.setText("You have successfully bought " + stockNumberTextView.getText().toString() + "\nshares of " + companyDesData.getTicker());
                            }
                            messageDialog.show();
                            changeStockView();
//                            sharedOwnedValueView.setText(String.valueOf(sharedpreferences.getInt(companyDesData.getTicker() + "_quantity", 0)));
//                            AvgCostValueView.setText(String.valueOf(sharedpreferences.getFloat(companyDesData.getTicker() + "_avgCostPerShare", 0.00f)));
//                            totalCostValueView.setText(String.valueOf(sharedpreferences.getFloat(companyDesData.getTicker() + "_totalCost", 0.00f)));
//                            changeValueView.setText(String.valueOf(sharedpreferences.getFloat(companyDesData.getTicker() + "_change", 0.00f)));
//                            marketValueView.setText(String.valueOf(sharedpreferences.getFloat(companyDesData.getTicker() + "_marketValue", 0.00f)));
                        }
                    }
                });
                Button stockSellButton = (Button) dialog.findViewById(R.id.stockSellButton);
                stockSellButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (stockNumberTextView.getText().toString().length() == 0 || stockNumberTextView.getText().toString().substring(1).contains("-")) {
                            Toast.makeText(SearchActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                        } else if (stockNumberTextView.getText().toString().charAt(0) == '-' || (stockNumberTextView.getText().toString().charAt(0) == '0' && stockNumberTextView.getText().toString().length() == 1)) {
                            Toast.makeText(SearchActivity.this, "Cannot sell non-positive shares", Toast.LENGTH_SHORT).show();
                        } else if (Integer.parseInt(stockNumberTextView.getText().toString()) > sharedpreferences.getInt(companyDesData.getTicker() + "_quantity", 0)) {
                            Toast.makeText(SearchActivity.this, "Not enough shares to sell", Toast.LENGTH_SHORT).show();
                        } else {
                            float remainMoney = sharedpreferences.getFloat(cashBalanceKey, 25000.00f);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            double totalMoney = remainMoney + Float.parseFloat(stockNumberTextView.getText().toString()) * latestPriceData.getC();
                            int remainQuantity = sharedpreferences.getInt(companyDesData.getTicker() + "_quantity", 0) - Integer.parseInt(stockNumberTextView.getText().toString());
                            int quantity = Integer.parseInt(stockNumberTextView.getText().toString());
                            if (remainQuantity == 0) {
                                double totalCost = 0.00;
                                double avgCostPerShare = 0.00;
                                double change = 0.00;
                                double marketValue = 0.00;
                                editor.putFloat(cashBalanceKey, (float) totalMoney);
                                editor.putInt(companyDesData.getTicker() + "_quantity", remainQuantity);
                                editor.putFloat(companyDesData.getTicker() + "_totalCost", (float) totalCost);
                                editor.putFloat(companyDesData.getTicker() + "_avgCostPerShare", (float) avgCostPerShare);
                                editor.putFloat(companyDesData.getTicker() + "_change", (float) change);
                                editor.putFloat(companyDesData.getTicker() + "_marketValue", (float) marketValue);
                                ArrayList<String> string_set = getArrayList(boughtStocksKey);
                                ArrayList<String> string_set_copy = new ArrayList<String>(string_set);
                                string_set_copy.remove(companyDesData.getTicker());
//                                editor.putStringSet(boughtStocksKey, string_set_copy);
                                saveArrayList(string_set_copy, boughtStocksKey);
                                editor.apply();
                                dialog.dismiss();
                                if (quantity == 1){
                                    flag_messageTextView.setText("You have successfully sold " + stockNumberTextView.getText().toString() + "\nshare of " + companyDesData.getTicker());
                                } else {
                                    flag_messageTextView.setText("You have successfully sold " + stockNumberTextView.getText().toString() + "\nshares of " + companyDesData.getTicker());
                                }
                                messageDialog.show();
                                changeStockView();

                            } else {
                                totalCost = sharedpreferences.getFloat(companyDesData.getTicker() + "_totalCost", 0) - latestPriceData.getC() * quantity;
                                avgCostPerShare = totalCost / remainQuantity;
                                change = (latestPriceData.getC() - avgCostPerShare) * remainQuantity;
                                marketValue = latestPriceData.getC() * remainQuantity;

                                editor.putFloat(companyDesData.getTicker() + "_change", (float) change);
                                editor.putFloat(companyDesData.getTicker() + "_marketValue", (float) marketValue);
                                editor.putFloat(cashBalanceKey, (float) totalMoney);
                                editor.putInt(companyDesData.getTicker() + "_quantity", remainQuantity);
                                editor.putFloat(companyDesData.getTicker() + "_totalCost", (float) totalCost);
                                editor.putFloat(companyDesData.getTicker() + "_avgCostPerShare", (float) avgCostPerShare);
                                editor.apply();

                                dialog.dismiss();
                                if (quantity == 1){
                                    flag_messageTextView.setText("You have successfully sold " + stockNumberTextView.getText().toString() + "\nshare of " + companyDesData.getTicker());
                                } else {
                                    flag_messageTextView.setText("You have successfully sold " + stockNumberTextView.getText().toString() + "\nshares of " + companyDesData.getTicker());
                                }
                                messageDialog.show();
                                changeStockView();

                            }

                        }
                    }
                });

                Button flagDoneButton = (Button) messageDialog.findViewById(R.id.flagDoneButton);
                flagDoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageDialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //store


        // PEERS URL
        RecyclerView recyclerView = findViewById(R.id.PeersUrlView);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        adapter = new PeersListAdapter(this, peersList, this);
        recyclerView.setAdapter(adapter);

        //NEWS recyclerView
        RecyclerView newsRecyclerView = findViewById(R.id.NewsRecyclerView);
        LinearLayoutManager newsHorizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setLayoutManager(newsHorizontalLayoutManager);

        newsAdapter = new NewsListAdapter(this, newsList, this);
        newsRecyclerView.setAdapter(newsAdapter);
//        newsRecyclerView.setFocusable(false);
        //recomendation
        WebView mWebView = findViewById(R.id.recommend_webview);
//        mWebView.loadUrl("http://google.com");
        mWebView.loadUrl("file:///android_asset/index_recommend.html");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:init('" + query + "')");
            }
        });

        //surprise
        //recomendation
        WebView mWebView2 = findViewById(R.id.surprise_webview);
//        mWebView.loadUrl("http://google.com");
        mWebView2.loadUrl("file:///android_asset/index_surprises.html");

        WebSettings webSettings2 = mWebView2.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        mWebView2.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mWebView2.loadUrl("javascript:init('" + query + "')");
            }
        });


        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        searchViewModel.getCompanyDesDataObserver().observe(this, new Observer<CompanyDesModel>() {
            @Override
            public void onChanged(CompanyDesModel movieModels) {
                if (movieModels != null) {
                    companyDesData = movieModels;
//                    if (getArrayList(boughtStocksKey).contains(companyDesData.getTicker())) {
//                        changeStockView();
//                    }
                    firstTickerView.setText(movieModels.getTicker());
                    firstNameView.setText(movieModels.getName());

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(movieModels.getTicker() + nameKey, movieModels.getName());
                    editor.apply();

                    Glide.with(SearchActivity.this).load(movieModels.getLogo()).into(firstCompanyImageView);
                    String ipotime = movieModels.getIpo().substring(movieModels.getIpo().length() - 5) + "-" + movieModels.getIpo().substring(0, movieModels.getIpo().length() - 6);
                    IPOView.setText(ipotime);
                    String Industry =  movieModels.getFinnhubIndustry();
                    IndustryView.setText(Industry);
//                    firstNameView.setFocusable(true);
//                    firstNameView.setFocusable(false);
//                    String Webpage = "<a href='" + movieModels.getWeburl() + "' style=\"color: blue\"><font color='green'>" + movieModels.getWeburl() + "</font></a>";
//                    Log.d("Webpage", "onChanged: "+Webpage);
                    String Webpage = movieModels.getWeburl();
//                    Log.d("Webpage", "onChanged: "+Webpage);
                    if (!Webpage.equals("null")) {
                        WebUrlView.setText(Html.fromHtml(Webpage));
                    }
                    WebUrlView.setPaintFlags(WebUrlView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    WebUrlView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Webpage));
                            startActivity(browserIntent);
                        }
                    });
                    WebUrlView.setMovementMethod(LinkMovementMethod.getInstance());
                    InsightTitleView.setText(movieModels.getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    spinner2.setVisibility(View.INVISIBLE);
                    ScrollView chatsLayout = (ScrollView) findViewById(R.id.search_container);
                    chatsLayout.setVisibility(View.VISIBLE);
                } else {

                }
            }
        });

        searchViewModel.getLatestPriceData().observe(this, new Observer<LatestPriceModel>() {
            @Override
            public void onChanged(LatestPriceModel movieModels) {
                if (movieModels != null) {
                    latestPriceData = movieModels;
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putFloat(query + "_c", movieModels.getC().floatValue());
                            editor.putFloat(query + "_d", movieModels.getD().floatValue());
                            editor.putFloat(query + "_dp", movieModels.getDp().floatValue());
                            editor.apply();
                        }
                    });
                    changeStockViewWhenEnter();
                    String price = "$" + String.format(Locale.US, "%.2f", movieModels.getC());
                    firstPriceView.setText(price);
                    String change = "$" + String.format(Locale.US, "%.2f", movieModels.getD()) + " (" + String.format(Locale.US, "%.2f", movieModels.getDp()) + "%)";
//                    Log.d("change", "onChanged: " + change);
                    firstPriceChangeView.setText(change);
                    firstPriceImageView.setVisibility(View.VISIBLE);
                    if (movieModels.getD() >= 0.01) {
                        firstPriceChangeView.setTextColor(Color.parseColor("#00CC00"));//Color.GREEN
                        firstPriceImageView.setImageResource(R.drawable.trending_up);
                        firstPriceImageView.setColorFilter(Color.parseColor("#00CC00"));
                    } else if (movieModels.getD() <= -0.01) {
                        firstPriceChangeView.setTextColor(Color.RED);
                        firstPriceImageView.setImageResource(R.drawable.trending_down);
                        firstPriceImageView.setColorFilter(Color.RED);
                    } else {
                        firstPriceChangeView.setTextColor(Color.BLACK);
                        firstPriceImageView.setVisibility(View.INVISIBLE);
                    }
                    String leftOStats = "Open Price :$" + String.format(Locale.US, "%.2f", movieModels.getO());
                    String leftLStats = "Low Price :  $" + String.format(Locale.US, "%.2f", movieModels.getL());
                    String RightHStats = "High Price :  $" + String.format(Locale.US, "%.2f", movieModels.getH());
                    String RightPStats = "Prev. Price : $" + String.format(Locale.US, "%.2f", movieModels.getPc());
                    StatsLeftOView.setText(leftOStats);
                    StatsLeftLView.setText(leftLStats);
                    StatsRightHView.setText(RightHStats);
                    StatsRightPView.setText(RightPStats);
                } else {

                }
            }
        });

        searchViewModel.getCompanyPeersData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> movieModels) {
                if (movieModels != null) {
                    peersList = movieModels;
//                    Log.d("peersList", "onChanged: "+peersList);
                    adapter.setPeersList(movieModels);
                } else {

                }
            }
        });

        searchViewModel.getCompanyNewsData().observe(this, new Observer<List<CompanyNewsModel>>() {
            @Override
            public void onChanged(List<CompanyNewsModel> movieModels) {
                if (movieModels != null) {
                    int len = movieModels.size();
                    List<CompanyNewsModel> tempData = new ArrayList<>();
                    int i = 0;
                    int count = 0;
                    while (i < len && count < 20) {
                        if (movieModels.get(i).getImage().length() > 0) {
                            tempData.add(movieModels.get(i));
                            count++;
                        }
                        i++;
                    }
                    newsList = tempData;
                    newsAdapter.setNewsList(newsList);
                } else {

                }
            }
        });

        searchViewModel.getSocialSentimentData().observe(this, new Observer<SocialSentiment>() {
            @Override
            public void onChanged(SocialSentiment movieModels) {
                if (movieModels != null) {
                    socialSentimentData = movieModels;
                    Long mentionReddit = 0L;
                    Long positiveMentionReddit = 0L;
                    Long negativeMentionReddit = 0L;
                    Long mentionTwitter = 0L;
                    Long positiveMentionTwitter = 0L;
                    Long negativeMentionTwitter = 0L;
                    if (movieModels.getReddit() != null) {
                        Integer len = movieModels.getReddit().size();
                        for (Integer i = 0; i < len; i++) {
                            mentionReddit += movieModels.getReddit().get(i).getMention();
                            positiveMentionReddit += movieModels.getReddit().get(i).getPositiveMention();
                            negativeMentionReddit += movieModels.getReddit().get(i).getNegativeMention();
                        }
                    }
                    if (movieModels.getTwitter() != null) {
                        Integer len = movieModels.getTwitter().size();
                        for (Integer i = 0; i < len; i++) {
                            mentionTwitter += movieModels.getTwitter().get(i).getMention();
                            positiveMentionTwitter += movieModels.getTwitter().get(i).getPositiveMention();
                            negativeMentionTwitter += movieModels.getTwitter().get(i).getNegativeMention();
                        }
                    }
                    TotalMentionsRedditView.setText(String.format(Locale.US, "%d", mentionReddit));
                    PositiveMentionsRedditView.setText(String.format(Locale.US, "%d", positiveMentionReddit));
                    NegativeMentionsRedditView.setText(String.format(Locale.US, "%d", negativeMentionReddit));
                    TotalMentionsTwitterView.setText(String.format(Locale.US, "%d", mentionTwitter));
                    PositiveMentionsTwitterView.setText(String.format(Locale.US, "%d", positiveMentionTwitter));
                    NegativeMentionsTwitterView.setText(String.format(Locale.US, "%d", negativeMentionTwitter));
                } else {

                }
            }
        });


        //< get elements >
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(SearchActivity.this, R.color.purple_500);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        tabLayout.setFocusable(false);
//        viewPager2.setFocusable(false);
        //</ get elements >


        ViewPagerAdapter adapter = new ViewPagerAdapter(this, query);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setIcon(R.drawable.chart_line);
                        } else if (position == 1) {
                            tab.setIcon(R.drawable.clock_time_three);
                        }
                    }
                }).attach();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                searchViewModel.makeApiCall(query);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_favorite:
//                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (menu.getItem(0).getIcon().getConstantState() == getResources().getDrawable(R.drawable.star).getConstantState()) {
                    menu.getItem(0).setIcon(R.drawable.star_outline);//remove from favorite
                    ArrayList<String> string_set = getArrayList(favoriteKey);
                    ArrayList<String> string_set_copy = new ArrayList<String>(string_set);
                    string_set_copy.remove(query);
                    saveArrayList(string_set_copy, favoriteKey);
//                    Log.d("clicktest1", string_set_copy.toString());
                    Toast.makeText(this, query + " is removed from favorites", Toast.LENGTH_SHORT).show();
//                    editor.putStringSet(favoriteKey, string_set_copy);
//                    editor.apply();
                } else {
//                    Log.d("Favorite", "fill");
                    menu.getItem(0).setIcon(R.drawable.star);
                    ArrayList<String> string_set = getArrayList(favoriteKey);
                    ArrayList<String> string_set_copy = new ArrayList<String>(string_set);
                    string_set_copy.add(query);
                    saveArrayList(string_set_copy, favoriteKey);
//                    Log.d("clicktest1", string_set_copy.toString());
                    Toast.makeText(this, query + " is added to favorites", Toast.LENGTH_SHORT).show();
//                    editor.putStringSet(favoriteKey, string_set_copy);
//                    editor.apply();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Drawable changeDrawableColor(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        if (getArrayList(favoriteKey).contains(this.query)) {
            menu.getItem(0).setIcon(R.drawable.star);
        }
        this.menu = menu;
        return true;
    }

    @Override
    public void onMovieClick(String movie) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("query", movie);
        startActivity(intent);
    }

    @Override
    public void onMovieClick(CompanyNewsModel newsModel) {
        // show new card
//        Toast.makeText(this, newsModel.getSummary(), Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.news_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(1000, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView dialog_sourceTextView = (TextView) dialog.findViewById(R.id.dialog_sourceTextView);
        dialog_sourceTextView.setText(newsModel.getSource());
        TextView dialog_dateTextView = (TextView) dialog.findViewById(R.id.dialog_dateTextView);
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy", java.util.Locale.getDefault());
        dialog_dateTextView.setText(formatter.format(newsModel.getDatetime() * 1000));
//        Log.d("date", newsModel.getDatetime().toString());
        TextView dialog_titleTextView = (TextView) dialog.findViewById(R.id.dialog_headlineTextView);
        dialog_titleTextView.setText(newsModel.getHeadline());
        TextView dialog_summaryTextView = (TextView) dialog.findViewById(R.id.dialog_descriptionTextView);
        dialog_summaryTextView.setText(newsModel.getSummary());
        ImageView dialog_chromeButton = (ImageView) dialog.findViewById(R.id.dialog_chromeButton);
        dialog_chromeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsModel.getUrl()));
                startActivity(intent);
            }
        });
        ImageView dialog_twitterButton = (ImageView) dialog.findViewById(R.id.dialog_twitterButton);
        dialog_twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=Check out this Link:" + "&url=%0A" + newsModel.getUrl()));
                startActivity(intent);
            }
        });
        ImageView dialog_facebookButton = (ImageView) dialog.findViewById(R.id.dialog_facebookButton);
        dialog_facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" + newsModel.getUrl()));
                startActivity(intent);
            }
        });
        dialog.show();

    }

    private void changeStockView() {
//        Log.d("changeStockView", "changeStockView");
//        String leftOStats = "$" + String.format(Locale.US, "%.2f", sharedpreferences.getFloat(companyDesData.getTicker() + "_avgCostPerShare", 0.00f));
        sharedOwnedValueView.setText(String.valueOf(sharedpreferences.getInt(companyDesData.getTicker() + "_quantity", 0)));
        AvgCostValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", sharedpreferences.getFloat(companyDesData.getTicker() + "_avgCostPerShare", 0.00f))));
        totalCostValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", sharedpreferences.getFloat(companyDesData.getTicker() + "_totalCost", 0.00f))));   //_totalCost
//        Log.d("stockchange", sharedpreferences.getFloat(companyDesData.getTicker() + "_change", 0.00f) + "");
        if (sharedpreferences.getFloat(companyDesData.getTicker() + "_change", 0.00f) >= 0.01) {
            changeValueView.setTextColor(Color.parseColor("#00CC00"));
            marketValueView.setTextColor(Color.parseColor("#00CC00"));
        } else if (sharedpreferences.getFloat(companyDesData.getTicker() + "_change", 0.00f) <= -0.01) {
            changeValueView.setTextColor(Color.RED);
            marketValueView.setTextColor(Color.RED);
        } else {
            changeValueView.setTextColor(StatsLeftOView.getTextColors().getDefaultColor());
            marketValueView.setTextColor(StatsLeftOView.getTextColors().getDefaultColor());
        }
        changeValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", sharedpreferences.getFloat(companyDesData.getTicker() + "_change", 0.00f)))); // _change
        marketValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", sharedpreferences.getFloat(companyDesData.getTicker() + "_marketValue", 0.00f)))); //_marketValue
    }

    //TODO: change color in enter when have quantity
    private void changeStockViewWhenEnter() {
        if (getArrayList(boughtStocksKey).contains(query)) {
            sharedOwnedValueView.setText(String.valueOf(sharedpreferences.getInt(query + "_quantity", 0)));
            AvgCostValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", sharedpreferences.getFloat(query + "_avgCostPerShare", 0.00f))));
            totalCostValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", sharedpreferences.getFloat(query + "_totalCost", 0.00f))));   //_totalCost
//        Log.d("stockchange", sharedpreferences.getFloat(companyDesData.getTicker() + "_change", 0.00f) + "");
            float change = (float) ((latestPriceData.getC() - sharedpreferences.getFloat(query+ "_avgCostPerShare", 0.00f)) * sharedpreferences.getInt(query + "_quantity", 0));
            if (change >= 0.01) {
                changeValueView.setTextColor(Color.parseColor("#00CC00"));
                marketValueView.setTextColor(Color.parseColor("#00CC00"));
            } else if (change <= -0.01) {
                changeValueView.setTextColor(Color.RED);
                marketValueView.setTextColor(Color.RED);
            } else {
                changeValueView.setTextColor(changeValueView.getTextColors().getDefaultColor());
                marketValueView.setTextColor(changeValueView.getTextColors().getDefaultColor());
            }

            changeValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", change))); // _change
            marketValueView.setText("$" + getFormattedValue(String.format(Locale.US, "%.2f", latestPriceData.getC() * sharedpreferences.getInt(query + "_quantity", 0)))); //_marketValue
            // Store the data in the shared preferences(check if the stock is stored)
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putFloat(query + "_change", change);
            editor.putFloat(query + "_marketValue", (float) (latestPriceData.getC() * sharedpreferences.getInt(query + "_quantity", 0)));
            editor.apply();
        }
    }

    public String getFormattedValue(String input) {
        if (input.equals("-0.00")) {
            return "0.00";
        }
        return input;
    }
    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key){
        Gson gson = new Gson();
        String json = sharedpreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
