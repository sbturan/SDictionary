package SearchWordAPIConnector;


import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by seckin on 7/17/2016.
 */
public class SearchWord {

    private final String USER_AGENT = "Mozilla/5.0";

public String getWordMean(String word){

    try{
        String result=sendGet(word);
        Log.i("sendget response","result="+result);

        return result;
    }catch (Exception e){
        Log.i("ERROR IN SEARCH WORD","asddsadsadsa="+e);
    }
    return "";


}

    private String sendGet(String word) throws Exception {

        String url = "https://glosbe.com/gapi/translate?from=eng&dest=eng&format=json&phrase="+word;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return  response.toString();

    }
}
