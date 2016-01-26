package pl.oldzi.olgachrostowska.DownloadFunctionality;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pl.oldzi.olgachrostowska.ViewFunctionality.SingleRecord;
import pl.oldzi.olgachrostowska.MainActivity;

public class DownloadData {

    private final static String LOG = MainActivity.class.getSimpleName();
    private List<SingleRecord> recordList;

    public DownloadData() {
        recordList = new ArrayList<>();
    }

    public void execute(String urlFromServer) {
        processJson(jsonToString(urlFromServer));
    }

    public List<SingleRecord> getRecordList() {
        return recordList;
    }

    protected String jsonToString(String serverUrl) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        if (serverUrl == null) {
            return null;
            }
        try {
            URL url = new URL(serverUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            StringBuilder glueData = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                glueData.append(line);
            }
            String finalJson = glueData.toString();
            return finalJson;
        } catch (MalformedURLException e) {
            Log.e(LOG, " Malformed URL exception");
        } catch (IOException e) {
            Log.e(LOG, "Error", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try { reader.close();
                } catch (final IOException e) { Log.e(LOG, "Error closing reader", e); }
            }
        }
        return null;
    }

    protected Void processJson(String jsonInString) {
        final String JSON_ITEMS = "array";
        final String JSON_TITLE = "title";
        final String JSON_DESC = "desc";
        final String JSON_URL = "url";

        try {
            JSONObject jsonObject = new JSONObject(jsonInString);
            JSONArray itemsArray = jsonObject.getJSONArray(JSON_ITEMS);

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonRecord = itemsArray.getJSONObject(i);
                String title = jsonRecord.getString(JSON_TITLE);
                String desc = jsonRecord.getString(JSON_DESC);
                String urll = jsonRecord.getString(JSON_URL);

                SingleRecord record = new SingleRecord(title, desc, urll);
                recordList.add(record);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
