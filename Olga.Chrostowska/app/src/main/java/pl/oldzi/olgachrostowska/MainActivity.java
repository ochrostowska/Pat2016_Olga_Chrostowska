package pl.oldzi.olgachrostowska;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String LOG = MainActivity.class.getSimpleName();
    final static String BASE_SERVER_URL = "http://start.biz.pl/";
    private DownloadData dataManager;
    private List<SingleRecord> recordList;
    private int pageIndex;
    private JsonRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.jsonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataManager = new DownloadData();
        recordList = dataManager.getRecordList();
        DownloadRawData drd = new DownloadRawData();

        Log.i(LOG, "SSS 1 : " + getResources().getConfiguration().screenLayout);
        Log.i(LOG, "SSS 2: " + Configuration.SCREENLAYOUT_SIZE_MASK);
        adapter = new JsonRecyclerViewAdapter(MainActivity.this, recordList);
        recyclerView.setAdapter(adapter);

        final GridLayoutManager ggm;
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            ggm = new GridLayoutManager(MainActivity.this, 4);
        }
        else {
            ggm = new GridLayoutManager(MainActivity.this, 2);
        }



        recyclerView.setLayoutManager(ggm);

        drd.setPageNumber(pageIndex);
        drd.execute();
        pageIndex++;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean loading = true;
            Handler h = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    if (pageIndex < 3) {
                        Log.i(LOG, "pageindex is : " + pageIndex);
                        DownloadRawData d = new DownloadRawData();
                        d.setPageNumber(pageIndex);
                        d.execute();
                        Log.i(LOG, "PPS : " + recordList.size());
                        pageIndex++;
                    }
                    loading = true;
                }
            };

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {

                    if (ggm.findLastVisibleItemPosition() + 1 == recordList.size()) {

                        Log.i(LOG, "VAR ggm.findlastvisible is : " + ggm.findLastVisibleItemPosition());
                        Log.i(LOG, "VAR finalList.size is : " + recordList.size());
                        if (pageIndex < 2) {
                            Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                        }
                        if (recordList.size() < 30 && loading) {
                            loading = false;
                            h.postDelayed(r, 3000);
                        }
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class DownloadRawData extends AsyncTask<String, String, String> {

        private int pageNumber;

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();

        }
        @Override
        protected String doInBackground(String... params) {

            if(pageNumber<3) {
                String url = createAndUpdateUrl(pageNumber);
                dataManager.execute(url);
                recordList = dataManager.getRecordList();}
            return null;
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
}

