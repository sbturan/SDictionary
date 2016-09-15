package model;

import android.graphics.Color;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by seckin on 7/3/2016.
 */
public class WordModel {

    private String name;
    private String officalDefinition;
    private String unOfficalDefinition;
    private String exampleSentences;
    private String color;
    private int searchedCount;
    private URL macMillianUrl;
    private URL googleImageUrl;



    public WordModel(String name,String officalDefinition,String unOfficalDefinition,String exampleSentences,int searchedCount)  {

        this.name=name;
        this.exampleSentences=exampleSentences;
        this.officalDefinition=officalDefinition;
        this.unOfficalDefinition=unOfficalDefinition;

        this.searchedCount=searchedCount;

        String macMillian="http://www.macmillandictionary.com/dictionary/british/";
        this.name=this.name.trim();
        macMillian=macMillian.concat(this.name.replace(' ','-'));
        String googleImage="http://www.google.com/images?q=";
        googleImage=googleImage.concat(this.name.replace(' ','+'));
        try {
            macMillianUrl=new URL(macMillian);
            googleImageUrl=new URL(googleImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public String getUnOfficalDefinition() {
        return unOfficalDefinition;
    }

    public void setUnOfficalDefinition(String unOfficalDefinition) {
        this.unOfficalDefinition = unOfficalDefinition;
    }

    public String getOfficalDefinition() {
        return officalDefinition;
    }

    public void setOfficalDefinition(String officalDefinition) {
        this.officalDefinition = officalDefinition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExampleSentences() {
        return exampleSentences;
    }

    public void setExampleSentences(String exampleSentences) {
        this.exampleSentences = exampleSentences;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public URL getMacMillianUrl() {
        return macMillianUrl;
    }

    public void setMacMillianUrl(URL macMillianUrl) {
        this.macMillianUrl = macMillianUrl;
    }

    public URL getGoogleImageUrl() {
        return googleImageUrl;
    }

    public void setGoogleImageUrl(URL googleImageUrl) {
        this.googleImageUrl = googleImageUrl;
    }


}
