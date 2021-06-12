package radenko.mihajlovic.smarthospital;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {

    private static final int SUCCESS = 200;
    //http get jsonArray
    public JSONArray getJSONArrayFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        java.net.URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        //header fields
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setReadTimeout(10000 /*milliseconds*/);
        urlConnection.setConnectTimeout(15000 /*milliseconds*/);
        try{
            urlConnection.connect();
        } catch (IOException e){
            Log.d("Ovde je problem ", " ");
            e.printStackTrace();
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null){
            sb.append(line + "\n");
        }
        br.close();
        String jsonString = sb.toString();
        Log.d("HTTP GET", "JSON data- " + jsonString);
        int responseCode = urlConnection.getResponseCode();
        urlConnection.disconnect();

        return responseCode == SUCCESS ? new JSONArray(jsonString) : null;
    }

    //http get json object
    public JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        java.net.URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        //header fields
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setReadTimeout(10000 /*milliseconds*/);
        urlConnection.setConnectTimeout(15000 /*milliseconds*/);
        try{
            urlConnection.connect();
        } catch (IOException e){
            return null;
        }
        //citanje odgovora od servera
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        /////////////////////////////dokraja

        String jsonString = sb.toString();
        Log.d("HTTP GET", "JSON obj- " + jsonString);
        int responseCode = urlConnection.getResponseCode();
        urlConnection.disconnect();
        return responseCode == SUCCESS ? new JSONObject(jsonString) : null;

    }

    //HTTP post
    public boolean postJSONObjestFromURL(String urlString, JSONObject jsonObject) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        java.net.URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        urlConnection.setRequestProperty("Accept", "application/json");
        //needed for post and put methods
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);

        try{
            urlConnection.connect();
        } catch (IOException e){
            return false;
        }
        //slanje podataka u telu zahteva
        DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
        //write json object
        os.writeBytes(jsonObject.toString());
        os.flush();
        os.close();
        /////////////////////////////
        int responseCode = urlConnection.getResponseCode();
        Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
        Log.i("MSG", urlConnection.getResponseMessage());
        urlConnection.disconnect();
        return (responseCode == SUCCESS);
    }

    //http delete
    public boolean httpDelete(String urlString) throws  IOException, JSONException{
        HttpURLConnection urlConnection = null;
        java.net.URL url= new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("DELETE");
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        urlConnection.setRequestProperty("Accept", "application/json");

        try{
            urlConnection.connect();
        } catch (IOException e){
            return false;
        }
        int responseCode = urlConnection.getResponseCode();

        Log.i("STATUS", String.valueOf(responseCode));
        Log.i("MSG", urlConnection.getResponseMessage());
        urlConnection.disconnect();
        return (responseCode == SUCCESS);
    }


    //HTTP post
    public JSONObject PostJSONObjectFromURL(String urlString, JSONObject jsonObject) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        java.net.URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setAllowUserInteraction(true);

        //needed for post and put methods
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);

        try{
            urlConnection.connect();
        } catch (IOException e){
            return null;
        }
        //slanje podataka u telu zahteva
        DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
        //write json object
        os.writeBytes(jsonObject.toString());
        os.flush();
        os.close();
        /////////////////////////////
        ///////////citanje odgovora servera
        //citanje odgovora od servera
        //DataInputStream input = new DataInputStream(urlConnection.getInputStream());
        String responseMessage = urlConnection.getResponseMessage();
        int responseCode = urlConnection.getResponseCode();
        String respCode = "" + responseCode;

        JSONObject json = new JSONObject();
        try{
            json.put("message", responseMessage);
            json.put("code",  respCode);

        } catch (JSONException e) {
            //TO DO obraditi izuzetak
            e.printStackTrace();
        }

        urlConnection.disconnect();
        return responseCode == SUCCESS ? json : json ;

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        String jsonString = sb.toString();
        br.close();

        //Log.d("HTTP GET OVAJ OVDE", "JSON obj- " + jsonString);
        int responseCode = urlConnection.getResponseCode();
        urlConnection.disconnect();
        return responseCode == SUCCESS ? new JSONObject(jsonString) : null;
        */
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //int responseCode = urlConnection.getResponseCode();
        //Log.i("STATUS", String.valueOf(urlConn())ection.getResponseCode()));
        //        //Log.i("MSG", urlConnection.getResponseMessage;
        //urlConnection.disconnect();
        //return (responseCode == SUCCESS);
    }




    //HTTP post
    public JSONObject POSTJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        java.net.URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        //urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setAllowUserInteraction(true);

        //needed for post and put methods
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);

        try{
            urlConnection.connect();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
        //write json object
        //os.writeBytes(jsonObject.toString());
        os.flush();
        os.close();


        //citanje odgovora od servera
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        /////////////////////////////dokraja

        String jsonString = sb.toString();
        Log.d("HTTP POST_STATE_SERVER_VRATIO", "JSON obj- " + jsonString);
        int responseCode = urlConnection.getResponseCode();
        urlConnection.disconnect();

        return responseCode == SUCCESS ? new JSONObject(jsonString) : null;

        //return true;

    }

}
