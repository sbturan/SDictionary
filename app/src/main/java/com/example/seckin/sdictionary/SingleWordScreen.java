package com.example.seckin.sdictionary;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Locale;

import components.WordView;
import controller.DatabaseHandler;
import model.WordModel;

/**
 * Created by seckin on 7/5/2016.
 */
public class SingleWordScreen extends AppCompatActivity {

    private String wordName;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String word=intent.getStringExtra("wordName");
        wordName=word;
        DatabaseHandler db= new DatabaseHandler(this);
        WordModel wm=db.getWordByName(word.trim());
        WordView view=new WordView(this,wm);
        tts=new TextToSpeech(SingleWordScreen.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }

                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
        setContentView(view);

    }

    private void ConvertTextToSpeech() {
        if(wordName==null||"".equals(wordName))
        {
            wordName = "Content not available";
            tts.speak(wordName, TextToSpeech.QUEUE_FLUSH, null);
        }else
            tts.speak(wordName, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void TextToSpeech(View view)
    {
        ConvertTextToSpeech();
    }


}
