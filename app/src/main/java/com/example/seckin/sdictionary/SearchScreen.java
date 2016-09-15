package com.example.seckin.sdictionary;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import SearchWordAPIConnector.SearchWord;
import controller.DatabaseHandler;
import model.WordModel;

public class SearchScreen extends AppCompatActivity {

    private ListView lv;
    Toolbar actionBar;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    EditText inputSearch;


    private static int colorCounter = 0;
    private static String[] colorCodes = new String[]{"#07bbdf", "#cafff7", "#262275", "#17999b", "#09cee4"};
     String[] mPlanetTitles;
     DrawerLayout mDrawerLayout;
    ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       //SearchWord s=new SearchWord();
        //s.getWordMean("bread");
        setLayout();

        initializeObjectsAndGetFromDB();

        setHandlers();



    }

    private void setHandlers() {
        inputSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchScreen.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        actionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AddWord.class);
                v.getContext().startActivity(intent);

            }
        });
    }

    private void initializeObjectsAndGetFromDB() {
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<String> allModels = db.getAllWordsNameList();
        List<WordModel> allModels1 = db.getAllModels();
        String[] words = (String[]) allModels.toArray(new String[0]);
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        actionBar=(Toolbar) findViewById(R.id.toolbar);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.word_name, words);
        lv.setAdapter(adapter);
    }

    private void setLayout() {
        setContentView(R.layout.activity_search_screen);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar);

        actionBar.setDisplayShowCustomEnabled(true);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private EditText getSingleWordView(WordModel model) {
        EditText view = new EditText(this);
        view.setEnabled(false);
        view.setKeyListener(null);
        view.setTextColor(Color.parseColor("black"));
        view.setInputType(InputType.TYPE_CLASS_TEXT);
        view.setText(model.getName());
        view.setPadding(20, 50, 0, 50);
        view.setBackgroundColor(Color.parseColor(colorCodes[colorCounter]));
        colorCounter = (colorCounter + 1) % 5;
        return view;
    }

    public void AppExit(View view)
    {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }




}
