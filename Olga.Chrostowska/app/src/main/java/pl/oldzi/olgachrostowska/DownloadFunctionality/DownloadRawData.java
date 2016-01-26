package pl.oldzi.olgachrostowska.DownloadFunctionality;

import android.os.AsyncTask;

import java.util.List;

import pl.oldzi.olgachrostowska.ViewFunctionality.JsonRecyclerViewAdapter;
import pl.oldzi.olgachrostowska.ViewFunctionality.SingleRecord;

public class DownloadRawData extends AsyncTask<String, String, String> {

    private final static String BASE_SERVER_URL = "http://start.biz.pl/";
    private int pageNumber;
    private DownloadData downloadData;
    private JsonRecyclerViewAdapter adapter;
    private List<SingleRecord> recordList;

    public void exeSettings(int pageNumber, DownloadData downloadData,
                            JsonRecyclerViewAdapter jsonRecyclerViewAdapter,
                            List<SingleRecord> singleRecordList) {
        this.pageNumber = pageNumber;
        this.downloadData = downloadData;
        this.adapter = jsonRecyclerViewAdapter;
        this.recordList = singleRecordList;
    }

    @Override
    protected String doInBackground(String... params) {
        if(pageNumber<3) {
            String url = createAndUpdateUrl(pageNumber);
            downloadData.execute(url);
            recordList = downloadData.getRecordList();
        }
        return null;
        }

    @Override
    protected void onPostExecute(final String result) {
        super.onPostExecute(result);
        adapter.notifyDataSetChanged();
    }

    public String createAndUpdateUrl(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_SERVER_URL);
        sb.append("page_");
        sb.append(Integer.toString(i));
        sb.append(".json");
        return sb.toString();
    }
}
