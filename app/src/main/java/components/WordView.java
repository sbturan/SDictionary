package components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.seckin.sdictionary.R;
import com.example.seckin.sdictionary.SearchScreen;

import java.net.URL;

import model.WordModel;

/**
 * Created by seckin on 7/3/2016.
 */
public class WordView extends RelativeLayout {

    TextView word;
    TextView macMilDef;
    TextView defInClass;
    TextView ExSentences;
    Button macMillianBtn;
    Button googleBtn;
    WordModel model;
    WebView webView;
    TextView errorMessage;

    public WordView(Context context, final WordModel model) {
        super(context);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.word_view_layout,this);
        this.model=model;
        word=(TextView) findViewById(R.id.word);
        macMilDef=(TextView) findViewById(R.id.macMilDef);
        defInClass=(TextView) findViewById(R.id.defInClass);
        ExSentences=(TextView) findViewById(R.id.ExSentences);
        macMillianBtn=(Button) findViewById(R.id.button);
        googleBtn=(Button) findViewById(R.id.button2);

        word.setText(model.getName());
        macMilDef.setText(model.getOfficalDefinition());
        defInClass.setText(model.getUnOfficalDefinition());
        ExSentences.setText(model.getExampleSentences());
        errorMessage=(TextView) findViewById(R.id.errorMessage);
        webView=(WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void  onReceivedError(WebView view, int errorCode,
                                         String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
                webView.setVisibility(INVISIBLE);
                errorMessage.setVisibility(VISIBLE);

            }

        });

        macMillianBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final URL  macMillianUrl = model.getMacMillianUrl();
                createWebView(macMillianUrl.toString());

            }
        });

        googleBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final URL  googleImgUrl = model.getGoogleImageUrl();
                createWebView(googleImgUrl.toString());

            }
        });

    }


    private  void createWebView(String url){

        webView.setVisibility(VISIBLE);
        webView.loadUrl(url.toString());


    }


    
}
