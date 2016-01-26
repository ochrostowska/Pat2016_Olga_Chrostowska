package pl.oldzi.olgachrostowska.ViewFunctionality;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.oldzi.olgachrostowska.R;

public class JsonRecyclerViewAdapter extends RecyclerView.Adapter<JsonRecyclerViewHolder> {
    private List<SingleRecord> jsonRecordsList;
    private Context jsonContext;

    public JsonRecyclerViewAdapter(Context context, List<SingleRecord> recordsList) {
        jsonContext = context;
        this.jsonRecordsList = recordsList;
    }

    @Override
    public void onBindViewHolder(JsonRecyclerViewHolder viewHolder, int position) {
        SingleRecord record = jsonRecordsList.get(position);
        Picasso.with(jsonContext).load(record.getJsonUrl())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .resize(200,200)
                .into(viewHolder.thumbnail);
        viewHolder.desc.setText(record.getJsonDesc());
        viewHolder.title.setText(record.getJsonTitle());
    }

    @Override
    public int getItemCount() {
        if(jsonRecordsList==null) return 0;
        else return jsonRecordsList.size();
    }

    @Override
    public JsonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_layout, viewGroup, false); // sprawdz czy dziala!!!
        JsonRecyclerViewHolder jsonViewHolder = new JsonRecyclerViewHolder(layoutView);
        return jsonViewHolder;
    }
}
