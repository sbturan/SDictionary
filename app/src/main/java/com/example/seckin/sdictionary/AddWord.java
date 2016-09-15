package com.example.seckin.sdictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import controller.DatabaseHandler;
import model.WordModel;

/**
 * Created by seckin on 7/17/2016.
 */
public class AddWord extends AppCompatActivity {

    EditText add_word_word;
    EditText definition;
    EditText extraDefinition;
    EditText example;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        add_word_word=(EditText)findViewById(R.id.add_word_word);
        definition=(EditText)findViewById(R.id.editText);
        extraDefinition=(EditText)findViewById(R.id.extraDefinition);
        example=(EditText)findViewById(R.id.example);
        addBtn=(Button)findViewById(R.id.button3);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_word_word.getText().toString()!=null&&add_word_word.getText().toString().length()!=0&&
                definition.getText().toString()!=null&&definition.getText().toString().length()!=0){
                    WordModel model= new WordModel(add_word_word.getText().toString(),definition.getText().toString(),
                            extraDefinition.getText().toString(),example.getText().toString(),0);
                    String log=add_word_word.getText().toString()+" "+
                            definition.getText().toString()+" "+
                            extraDefinition.getText().toString()+"  "+example.getText().toString();
                    Log.i("that word  added:  ",log);
                    DatabaseHandler dbh=new DatabaseHandler(v.getContext());
                    dbh.addWord(model);
                    Intent intent = new Intent(v.getContext(),SearchScreen.class);
                    v.getContext().startActivity(intent);

                }

            }
        });

    }
}
