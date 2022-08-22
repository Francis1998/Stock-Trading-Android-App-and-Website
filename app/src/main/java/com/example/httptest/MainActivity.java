package com.example.httptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.httptest.adapter.AutoMentionAdapter;
import com.example.httptest.adapter.MovieListAdapter;
import com.example.httptest.adapter.PortfolioListAdapter;
import com.example.httptest.model.MainLatestPriceModel;
import com.example.httptest.model.MovieModel;
import com.example.httptest.model.autoCompleteModel;
import com.example.httptest.viewmodel.MovieListViewModel;
import com.example.httptest.viewmodel.SearchViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity{
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String cashBalanceKey = "cashBalanceKey";
    public static final String netWorthKey = "netWorthKey";
    public static final String boughtStocksKey = "boughtStocksKey"; // set of strings
    public static final String favoriteKey = "favoriteKey"; // Set<string>
    public static final String nameKey = "_nameKey";


    private String[] autoCompleteList = new String[]{};
    private List<MovieModel> movieModelList;
    public MovieListAdapter adapter;
    public PortfolioListAdapter portfolioAdapter;
    public AutoMentionAdapter newsAdapter;
    private MovieListViewModel viewModel;
    private SearchViewModel searchViewModel;
    private autoCompleteModel autoCompleteModel;
    private String searchText = "1";
    private ProgressBar spinner;
    RecyclerView recyclerView;
    RecyclerView portfolioRecyclerView;
    //    MaterialSearchView searchView;
    Toolbar toolbar;
    TextView timeTextView, bottomTextView, NetWorthTextView, CashBalanceTextView;
    MenuItem searchitem;
    SearchView searchView;
    public List<MovieModel> favoriteStringArrayList = Collections.synchronizedList(new ArrayList<MovieModel>());
    public List<MovieModel> portfolioStringArrayList =  Collections.synchronizedList(new ArrayList<MovieModel>());
    AtomicInteger favoriteAtomicInteger;
    AtomicInteger portfolioAtomicInteger;
    SearchView.SearchAutoComplete searchAutoComplete;
    AtomicInteger backMainActivityTimes = new AtomicInteger(0);
    public List<String> favoriteMemoList = Collections.synchronizedList(new ArrayList<String>());
    public List<String> portfolioMemoList = Collections.synchronizedList(new ArrayList<String>());
    Timer timer;
    ItemTouchHelper.Callback favoriteCallback;
    ItemTouchHelper.Callback portfolioCallback;
    ItemTouchHelper portfolioTouchHelper;
    ItemTouchHelper favoriteTouchHelper;
    RelativeLayout chatsLayout;
    public Menu option_Menu;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(mypreference, MODE_PRIVATE);
        //TODO: CLEAR SHARED PREFERENCES
//        sharedpreferences.edit().clear().apply();
//        Log.d("clearShared", "onCreate: ");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        if (!sharedpreferences.contains(boughtStocksKey)) {
            saveArrayList(new ArrayList<String>(), boughtStocksKey);
            saveArrayList(new ArrayList<String>(), favoriteKey);

        }
//        if (!sharedpreferences.contains(favoriteKey)) {
//            saveArrayList(new ArrayList<String>(), favoriteKey);
////            SharedPreferences.Editor editor = sharedpreferences.edit();
////            editor.putStringSet(boughtStocksKey, new HashSet<>());
////            editor.apply();
//        }
        chatsLayout = (RelativeLayout)findViewById(R.id.main_show_container);
        recyclerView = (RecyclerView) findViewById(R.id.favoritesRecyclerView);
        portfolioRecyclerView = (RecyclerView) findViewById(R.id.PortfolioRecyclerView);
//        searchViewCode();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", java.util.Locale.getDefault());
        Date date = new Date();
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        timeTextView.setText(formatter.format(date));
//        RecyclerView recyclerView = findViewById(R.id.PortfolioRecyclerView);
//        final TextView noresult = findViewById(R.id.noResultTv);
//        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter =  new MovieListAdapter(this, movieModelList, this);
        NetWorthTextView = (TextView) findViewById(R.id.NetWorthTextView);
        CashBalanceTextView = (TextView) findViewById(R.id.CashBalanceTextView);
//        recyclerView.setAdapter(adapter);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

//        enableSwipeToDeleteAndUndo();
        adapter = new MovieListAdapter(MainActivity.this, favoriteStringArrayList);
        favoriteCallback = new ItemMoveCallback(MainActivity.this, adapter);
        favoriteTouchHelper = new ItemTouchHelper(favoriteCallback);
        favoriteTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        enableFavoriteSwipeToDeleteAndUndo(adapter);

        portfolioAdapter = new PortfolioListAdapter(MainActivity.this, portfolioStringArrayList);
        portfolioCallback = new PortfolioItemMoveCallback(MainActivity.this, portfolioAdapter);
        portfolioTouchHelper = new ItemTouchHelper(portfolioCallback);
        portfolioTouchHelper.attachToRecyclerView(portfolioRecyclerView);
        portfolioRecyclerView.setAdapter(portfolioAdapter);

        viewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
//        if (portfolioMemoList.size() == 0 && favoriteMemoList.size() == 0) {
//            try {
//                spinner.setVisibility(View.VISIBLE);
//                RelativeLayout chatsLayout = (RelativeLayout)findViewById(R.id.main_show_container);
//                chatsLayout.setVisibility(View.INVISIBLE);
//                Log.d("pauseArea", "onCreate: ");
//                Thread.sleep(400);
////                    spinner.setVisibility(View.INVISIBLE);
////                    chatsLayout.setVisibility(View.VISIBLE);
//                Log.d("pauseArea", "onCreate: ");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////            autoUpdateNetAndCash();
//        }
        bottomTextView  = (TextView) findViewById(R.id.bottomTextView);
        bottomTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.ﬁnnhub.io"));
                startActivity(browserIntent);
            }
        });
        if (sharedpreferences.contains(cashBalanceKey)) {
            CashBalanceTextView.setText("Cash Balance\n$" + String.format(Locale.US, "%.2f", sharedpreferences.getFloat(cashBalanceKey, 25000.00f)));
        } else {
            //TODO: can change to other thread
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putFloat(cashBalanceKey, 25000f);
            editor.apply();
        }
        if (sharedpreferences.contains(netWorthKey)) {
            NetWorthTextView.setText("Net Worth\n$" + String.format(Locale.US, "%.2f", sharedpreferences.getFloat(netWorthKey, 25000.00f)));
        } else {
            //TODO: can change to other thread, editor can be only one
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putFloat(netWorthKey, 25000f);
            editor.apply();
        }

        //TODO: should click to update
        searchViewModel.getAutoCompleteData().observe(this, new Observer<autoCompleteModel>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onChanged(autoCompleteModel movieModels) {
                if (movieModels != null && movieModels.getSearchWord().equals(searchText)) {
                    autoCompleteModel = movieModels;
                    List<String> copylist = new ArrayList<>();
                    for (int i = 0; i < autoCompleteModel.getCount(); i++) {
                        if (autoCompleteModel.getResult().get(i).getType().equals("Common Stock") && !(autoCompleteModel.getResult().get(i).getSymbol().contains("."))) { //&& !(autoCompleteModel.getResult().get(i).getSymbol().contains("."))
                            copylist.add(autoCompleteModel.getResult().get(i).getSymbol() + " | " + autoCompleteModel.getResult().get(i).getDescription());
                        }
                    }
                    autoCompleteList = copylist.toArray(new String[0]);
                    newsAdapter.inputIntoAutoMentionList(Arrays.asList(autoCompleteList));
                    newsAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
        //TODO: getMainLatestPriceData api call should clean all favoriteStringArrayList & portfolioStringArrayList
        searchViewModel.getMainLatestPriceData().observe(MainActivity.this, new Observer<MainLatestPriceModel>() {
            @Override
            public void onChanged(MainLatestPriceModel movieModels) {
                if (movieModels != null) {
                    //TODO: Comfirm it is necessary
                    favoriteMemoList = getArrayList(favoriteKey);

                    if (!movieModels.getTicker().equals("favoriteZeroList") && !movieModels.getTicker().equals("portfolioZeroList")) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("canmovenow", "auto update" + movieModels.getTicker());
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putFloat(movieModels.getTicker() + "_c", movieModels.getC().floatValue());
                                editor.putFloat(movieModels.getTicker() + "_d", movieModels.getD().floatValue());
                                editor.putFloat(movieModels.getTicker() + "_dp", movieModels.getDp().floatValue());
                                editor.apply();
                            }
                        });
                    }

                    if (favoriteMemoList.contains(movieModels.getTicker())) {
                        favoriteStringArrayList.add(new MovieModel(movieModels.getTicker(), sharedpreferences.getString(movieModels.getTicker() + nameKey, ""), movieModels.getC(), movieModels.getD(), movieModels.getDp()));
                        if (favoriteStringArrayList.size() == favoriteMemoList.size()) {
                            List<MovieModel> favoriteTransferList = Collections.synchronizedList(new ArrayList<MovieModel>());
                            for (String ticker: favoriteMemoList) {
                                for (MovieModel movieModel: favoriteStringArrayList) {
                                    if (ticker.equals(movieModel.getTicker())) {
                                        favoriteTransferList.add(movieModel);
                                    }
                                }
                            }
                            favoriteStringArrayList = favoriteTransferList;
                            setFavoriteAdapter();
                        }
                    }
                    // TODO: should open?
                    portfolioMemoList = getArrayList(boughtStocksKey);
                    if (portfolioMemoList.contains(movieModels.getTicker())) {
                        // TODO: Really need ?
                        String ticker = movieModels.getTicker();
                        int quantity = sharedpreferences.getInt(ticker + "_quantity", 0);
                        if (quantity != 0){
                            double avgCostPerShare = sharedpreferences.getFloat(ticker + "_avgCostPerShare", 0.00f);
                            double latestStockQuote = movieModels.getC();
                            double portfolioC = latestStockQuote  * quantity; //Market Value
                            double portfolioD = (latestStockQuote - avgCostPerShare) * quantity; //Change in Price from Total Cost
                            double portfolioDp = portfolioD / (avgCostPerShare * quantity) * 100; // Change in Price from Total Cost (%)
                            portfolioStringArrayList.add(new MovieModel(ticker, String.valueOf(quantity) + " shares", portfolioC, portfolioD, portfolioDp));
                        }

                        if (portfolioStringArrayList.size() == portfolioMemoList.size()) {
                            List<MovieModel> portfolioTransferList = Collections.synchronizedList(new ArrayList<MovieModel>());
                            for (String pticker: portfolioMemoList) { //make sure sequence
                                for (MovieModel movieModel: portfolioStringArrayList) {
                                    if (pticker.equals(movieModel.getTicker())) {
                                        portfolioTransferList.add(movieModel);
                                    }
                                }
                            }
                            portfolioStringArrayList = portfolioTransferList;
                            setPortfolioAdapter();
                        }


                    }
                    if ((portfolioStringArrayList.size() == portfolioMemoList.size()) && (favoriteStringArrayList.size() == favoriteMemoList.size())) {
                        spinner.setVisibility(View.INVISIBLE);
                        chatsLayout.setVisibility(View.VISIBLE);
                        option_Menu.findItem(R.id.action_search).setVisible(true);
                        autoUpdateNetAndCash();
                        Log.d("canmovenow", "main complete");
                    }

                } else {

                }
            }
        });
        // TODO: canMerge, auto update also should clean portfolioStringArrayList
        searchViewModel.getAutoUpdateData().observe(MainActivity.this, new Observer<MainLatestPriceModel>() {
            @Override
            public void onChanged(MainLatestPriceModel movieModels) {
                if (movieModels != null) {
                    if (!movieModels.getTicker().equals("favoriteZeroList") && !movieModels.getTicker().equals("portfolioZeroList")) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
//                                Log.d("canmovenow", "auto update" + movieModels.getTicker());
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putFloat(movieModels.getTicker() + "_c", movieModels.getC().floatValue());
                                editor.putFloat(movieModels.getTicker() + "_d", movieModels.getD().floatValue());
                                editor.putFloat(movieModels.getTicker() + "_dp", movieModels.getDp().floatValue());
                                editor.apply();
                            }
                        });
                    }

                    if (favoriteMemoList.contains(movieModels.getTicker())) {
                        favoriteStringArrayList.add(new MovieModel(movieModels.getTicker(), sharedpreferences.getString(movieModels.getTicker() + nameKey, ""), movieModels.getC(), movieModels.getD(), movieModels.getDp()));
                        if (favoriteStringArrayList.size() == favoriteMemoList.size()) {
                            List<MovieModel> favoriteTransferList = Collections.synchronizedList(new ArrayList<MovieModel>());
                            for (String ticker: favoriteMemoList) {
                                for (MovieModel movieModel: favoriteStringArrayList) {
                                    if (ticker.equals(movieModel.getTicker())) {
                                        favoriteTransferList.add(movieModel);
                                    }
                                }
                            }
                            favoriteStringArrayList = favoriteTransferList;
                            setFavoriteAdapter();
                        }
                    }

                    // TODO: should open?
                    if (portfolioMemoList.contains(movieModels.getTicker())) {

                        String ticker = movieModels.getTicker();
                        int quantity = sharedpreferences.getInt(ticker + "_quantity", 0);
                        if (quantity != 0){
                            double avgCostPerShare = sharedpreferences.getFloat(ticker + "_avgCostPerShare", 0.00f);
                            double latestStockQuote = movieModels.getC();
                            double portfolioC = latestStockQuote  * quantity; //Market Value
                            double portfolioD = (latestStockQuote - avgCostPerShare) * quantity; //Change in Price from Total Cost
                            double portfolioDp = portfolioD / (avgCostPerShare * quantity) * 100; // Change in Price from Total Cost (%)
                            portfolioStringArrayList.add(new MovieModel(ticker, String.valueOf(quantity) + " shares", portfolioC, portfolioD, portfolioDp));
                        }

                        if (portfolioStringArrayList.size() == portfolioMemoList.size()) {
                            List<MovieModel> portfolioTransferList = Collections.synchronizedList(new ArrayList<MovieModel>());
                            for (String pticker: portfolioMemoList) {
                                for (MovieModel movieModel: portfolioStringArrayList) {
                                    if (pticker.equals(movieModel.getTicker())) {
                                        portfolioTransferList.add(movieModel);
                                    }
                                }
                            }
                            portfolioStringArrayList = portfolioTransferList;
                            setPortfolioAdapter();
                        }
                    }
                    if ((portfolioStringArrayList.size() == portfolioMemoList.size()) && (favoriteStringArrayList.size() == favoriteMemoList.size())) {
                        autoUpdateNetAndCash();
                        Log.d("canmovenow", "auto complete");
                    }

                } else {

                }
            }
        });

        // use to extend spinner
        viewModel.getMoviesListObserver().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieModel> movieModels) {
                if (movieModels != null) {
//                    Log.d("receivespinner", "onChanged: ");
                    option_Menu.findItem(R.id.action_search).setVisible(true);
                    spinner.setVisibility(View.INVISIBLE);
//                    RelativeLayout chatsLayout = (RelativeLayout)findViewById(R.id.main_show_container);
                    chatsLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                if (getArrayList(boughtStocksKey).size() == 0 && getArrayList(favoriteKey).size() == 0) {
                    viewModel.makeApiCall("AAPL");
                    Log.d("makeAutoUpdate", "AAPL");
                }

            }
        });
        populateRecyclerView();
        timer = new Timer();
        //TODO: need to update memolist after make change
        timer.schedule( new TimerTask() {
            public void run() {
                updateRecyclerView();
//                Log.d("runautoupdate", "run: ");
            }
        }, 15*1000, 15*1000);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.material_search_menu, menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };
        option_Menu = menu;
        searchitem = menu.findItem(R.id.action_search);
        searchitem.setOnActionExpandListener(onActionExpandListener);
        searchView = (SearchView) searchitem.getActionView();
        searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.BLACK);
        searchAutoComplete.setDropDownBackgroundResource(R.color.white);
        searchAutoComplete.setThreshold(1);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
//                Log.d("item", "onItemClick: " + itemIndex);
                hideSoftKeyboard(MainActivity.this);
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(queryString.split(" | ")[0]);
                newsAdapter.inputIntoAutoMentionList(new ArrayList<>());
                newsAdapter.notifyDataSetChanged();

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", searchAutoComplete.getText().toString());
                startActivity(intent);
            }
        });
        newsAdapter = new AutoMentionAdapter(android.R.layout.simple_dropdown_item_1line, MainActivity.this);
        searchAutoComplete.setAdapter(newsAdapter); //线程堵塞
        Log.d("judge", " autocomplet comopent create");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {
                  return false;
              }

              @Override
              public boolean onQueryTextChange(final String newText) {
                  if (searchView.getQuery().toString().equals(newText)&& !(searchText.equals(newText))) {
                      AsyncTask.execute(new Runnable() {
                          @Override
                          public void run() {
                              //TODO your background code
                              String copytext = newText;
                              if (copytext.length() == 0){
                                  copytext = "~";
                              }
                              searchViewModel.autoCompleteApiCall(copytext);
                              searchText = copytext;
                          }
                      });

                  }
                  return true;
              }
        });
        searchView.setQueryHint("Search...");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
//        if (searchView.isSearchOpen()) {
//            searchView.closeSearch();
//        } else {
//            super.onBackPressed();
//        }
    }
    private void populateRecyclerView() {
        favoriteStringArrayList = Collections.synchronizedList(new ArrayList<MovieModel>());
        portfolioStringArrayList =  Collections.synchronizedList(new ArrayList<MovieModel>());
//        Log.d("populateRecyclerView", "populateRecyclerView: " + sharedpreferences.getStringSet(favoriteKey, new HashSet<String>()).toString() + " " + sharedpreferences.getStringSet(boughtStocksKey, new HashSet<String>()).size());
        favoriteMemoList = getArrayList(favoriteKey);
        portfolioMemoList = getArrayList(boughtStocksKey);
//        Log.d("getBACKtoMain", "populateRecyclerView: " + portfolioMemoList.toString());
//        Log.d("populateRecyclerView", "populateRecyclerView: " + getArrayList(favoriteKey) + " " + portfolioMemoList);
        if (favoriteMemoList.size() > 0) {
            for (String s : favoriteMemoList) {
                Log.d("submitQuery", ": " + s);
                searchViewModel.favoriteApiCall(s);
            }
        } else{
            searchViewModel.favoriteApiCall("favoriteZeroList");

        }

        if (portfolioMemoList.size() > 0) {
            for (String s : portfolioMemoList) {
                if (!favoriteMemoList.contains(s)){
                    Log.d("submitQuery", ": " + s);
                    searchViewModel.favoriteApiCall(s);
                }
            }
        } else {
            searchViewModel.favoriteApiCall("portfolioZeroList");
        }



    }

    //autoupdate view
    private void updateRecyclerView() {
        favoriteStringArrayList = Collections.synchronizedList(new ArrayList<MovieModel>());
        portfolioStringArrayList =  Collections.synchronizedList(new ArrayList<MovieModel>());
        if (favoriteMemoList.size() > 0) {
            for (String s : favoriteMemoList) {
                Log.d("submitQuery", ": " + s);
                searchViewModel.autoUpdateApiCall(s);
            }
        } else{
            searchViewModel.autoUpdateApiCall("favoriteZeroList");
        }
        if (portfolioMemoList.size() > 0) {
            for (String s : portfolioMemoList) {
                if (!favoriteMemoList.contains(s)){
                    Log.d("submitQuery", ": " + s);
                    searchViewModel.autoUpdateApiCall(s);
                }
            }
        } else {
            searchViewModel.autoUpdateApiCall("portfolioZeroList");
        }
    }



    private void enableFavoriteSwipeToDeleteAndUndo(MovieListAdapter mAdapter) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                Log.d("onSwiped", "onSwiped: " + viewHolder.getAdapterPosition());
                final int position = viewHolder.getAdapterPosition();
                final MovieModel item = mAdapter.getMovieList().get(position);

                mAdapter.removeItem(position);
//                Log.d("onSwiped", "onSwiped: " + position);
                if (position < favoriteMemoList.size()) {
                    favoriteMemoList.remove(position);
                    AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO your background code
//                        List<String> string_set = favoriteMemoList;
                        saveArrayList((ArrayList<String>) favoriteMemoList, favoriteKey);
                    }
                    });
                    Log.d("onSwiped", "onSwiped: " + favoriteMemoList);
                }
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void autoUpdateNetAndCash(){
//        if (sharedpreferences.contains(cashBalanceKey)) {
//            CashBalanceTextView.setText("Cash Balance\n$" + String.format(Locale.US, "%.2f", sharedpreferences.getFloat(cashBalanceKey, 25000.00f)));
//        }
        CashBalanceTextView.setText("Cash Balance\n$" + String.format(Locale.US, "%.2f", sharedpreferences.getFloat(cashBalanceKey, 25000.00f)));
        SharedPreferences.Editor editor = sharedpreferences.edit();
        float total = sharedpreferences.getFloat(cashBalanceKey, 25000.00f);
        for (String ticker : portfolioMemoList) {
            total += sharedpreferences.getInt(ticker + "_quantity", 0) * sharedpreferences.getFloat(ticker + "_c", 0.00f);
        }
        NetWorthTextView.setText("Net Worth\n$" + String.format(Locale.US, "%.2f", total));
        editor.putFloat(netWorthKey, 25000f);
        editor.apply();

    }

    public void useRamToReload(){
        favoriteStringArrayList.clear();
//        favoriteMemoList = getArrayList(favoriteKey);
        for (String ticker: favoriteMemoList) {
            //TODO: SearchActivity should store like this
                    favoriteStringArrayList.add(new MovieModel(ticker, sharedpreferences.getString(ticker + nameKey, ""), (double)sharedpreferences.getFloat(ticker + "_c", 0.00f), (double)sharedpreferences.getFloat(ticker + "_d", 0.00f), (double)sharedpreferences.getFloat(ticker + "_dp", 0.00f)));
            }
        setFavoriteAdapter();

        //TODO: REMOVE ALL portfolioStringArrayList NEW()
        //TODO: portfolioStringArrayList structure is not correct
        portfolioStringArrayList.clear();
//        portfolioMemoList = getArrayList(boughtStocksKey);
        for (String ticker: portfolioMemoList) {
            double avgCostPerShare = sharedpreferences.getFloat(ticker + "_avgCostPerShare", 0.00f);
            double latestStockQuote = sharedpreferences.getFloat(ticker + "_c", 0.00f);
            int quantity = sharedpreferences.getInt(ticker + "_quantity", 0);

            double portfolioC = latestStockQuote  * quantity; //Market Value
            double portfolioD = (latestStockQuote - avgCostPerShare) * quantity; //Change in Price from Total Cost
            double portfolioDp = portfolioD / (avgCostPerShare * quantity) * 100; // Change in Price from Total Cost (%)
            portfolioStringArrayList.add(new MovieModel(ticker, String.valueOf(quantity) + " shares", portfolioC, portfolioD, portfolioDp));
        }
        setPortfolioAdapter();
        autoUpdateNetAndCash();
        Log.d("canmovenow", "load disk complete");
    }

    @Override
    protected void onResume() {
        favoriteMemoList = getArrayList(favoriteKey);
        portfolioMemoList = getArrayList(boughtStocksKey);
        Log.d("onResume", ": " + favoriteMemoList.toString() +"  " + portfolioMemoList.toString());
        backMainActivityTimes.incrementAndGet();
        if (backMainActivityTimes.get() != 1) {
            useRamToReload();
//            if (portfolioMemoList.size() == 0){
//                setPortfolioAdapter();
//            }
//            if (favoriteMemoList.size() == 0){
//                setFavoriteAdapter();
//            }
            Log.d("onResume", ": backto main activity");
            timer = new Timer();
            timer.schedule( new TimerTask() {
                public void run() {
                    // do your work
                    Log.d("autorun", "run: ");
                    updateRecyclerView();
                }
            }, 15*1000, 15*1000);
            //TODO: HOW TO SHOW SO QUICK? CHANGE IT ON ACTIVITY MAIN?
            super.onResume();

        }
        else {
            super.onResume();
        }
    }

    public void saveArrayList(ArrayList<String> list, String key){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Gson gson = new Gson();
                List<String> listWithoutDuplicates = new ArrayList<>(
                        new LinkedHashSet<>(list));
                String json = gson.toJson(listWithoutDuplicates);
                editor.putString(key, json);
                editor.apply();
            }
        });
    }

    public ArrayList<String> getArrayList(String key){
        Gson gson = new Gson();
        String json = sharedpreferences.getString(key, "");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList res =  gson.fromJson(json, type);
        if (res == null){
            return new ArrayList<String>();
        } else {
            return res;
        }
    }

    public void saveAllData(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                saveArrayList((ArrayList<String>) favoriteMemoList, favoriteKey);
                saveArrayList((ArrayList<String>) portfolioMemoList, boughtStocksKey);
            }
        });
    }
    @Override
    protected void onStop() {
        saveAllData();
        super.onStop();
    }
    //ondestroy
    @Override
    protected void onDestroy() {
        saveAllData();
        super.onDestroy();
    }
    //onpause
    @Override
    protected void onPause() {
        saveAllData();
        timer.cancel();
        super.onPause();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }


    private void setPortfolioAdapter(){
        portfolioAdapter.setMovieList(portfolioStringArrayList);
        portfolioAdapter.notifyDataSetChanged();

    }
    //set favorite adapter
    private void setFavoriteAdapter(){
        adapter.setMovieList(favoriteStringArrayList);
        adapter.notifyDataSetChanged();
    }
}