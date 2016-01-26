package pl.oldzi.olgachrostowska.ViewFunctionality;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.oldzi.olgachrostowska.R;

/**
 * Created by Oldzi on 18.12.2015.
 */
public class JsonRecyclerViewHolder extends RecyclerView.ViewHolder {

    protected ImageView thumbnail;
    protected TextView desc;
    protected TextView title;

    public JsonRecyclerViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.jsonImageThumbnail);
        this.desc = (TextView) view.findViewById(R.id.descTextView);
        this.title = (TextView) view.findViewById(R.id.titleTextView);
    }
}
