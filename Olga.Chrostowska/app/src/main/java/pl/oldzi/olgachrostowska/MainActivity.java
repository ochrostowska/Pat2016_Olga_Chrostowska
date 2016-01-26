package pl.oldzi.olgachrostowska;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import pl.oldzi.olgachrostowska.DownloadFunctionality.DownloadData;
import pl.oldzi.olgachrostowska.DownloadFunctionality.DownloadRawData;
import pl.oldzi.olgachrostowska.ViewFunctionality.JsonRecyclerViewAdapter;
import pl.oldzi.olgachrostowska.ViewFunctionality.ProgressAnimation;
import pl.oldzi.olgachrostowska.ViewFunctionality.SingleRecord;

public class MainActivity extends AppCompatActivity {

    private final static String LOG = MainActivity.class.getSimpleName();
    private DownloadRawData downloadRawData;
    private DownloadData dataManager;
    private List<SingleRecord> recordList;
    private int pageIndex;
    private JsonRecyclerViewAdapter adapter;
    private Handler handler;
    private ProgressAnimation animation;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO
        //Obsłużyć przywracanie stanu po obróceniu ekranu
        //Zdefiniować zachowanie dla aplikacji gdy nie mamy wifi
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.jsonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataManager = new DownloadData();
        recordList = dataManager.getRecordList();
        adapter = new JsonRecyclerViewAdapter(MainActivity.this, recordList);

        ProgressBar progressCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        animation = new ProgressAnimation(progressCircle);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(1500);
        recyclerView.setAdapter(alphaAdapter);

        final GridLayoutManager gridLayoutManager;
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            gridLayoutManager = new GridLayoutManager(MainActivity.this, 4);
        } else {
            gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        loadNewPortionOfData();

        handler = new Handler();
        Runnable runnableForDataDownload = new Runnable() {
            @Override
            public void run() {
                if (gridLayoutManager.findLastVisibleItemPosition() == recordList.size() - 1) {
                    loadNewPortionOfData();
                }
            }
        };
        handler.postDelayed(runnableForDataDownload, 1000);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean loading = true;
            Runnable runnableForDataDownload = new Runnable() {
                public void run() {
                    if (pageIndex < 3) { loadNewPortionOfData(); }
                    loading = true;
                    animation.animate();
                }
            };

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if (gridLayoutManager.findLastVisibleItemPosition() + 1 == recordList.size()) {
                        if (recordList.size() < 30 && loading) {
                            loading = false;
                            animation.resetAnimation();
                            handler.postDelayed(runnableForDataDownload, 3000);
                        }
                    }
                }
            }
        });
    }

    public void loadNewPortionOfData() {
        downloadRawData = new DownloadRawData();
        downloadRawData.exeSettings(pageIndex, dataManager, adapter, recordList);
        downloadRawData.execute();
        pageIndex++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}