package ua.kaganovych.persistencesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultAdapter extends ArrayAdapter<SearchResult> {

    public SearchResultAdapter(Context context, ArrayList<SearchResult> results) {
        super(context, 0, results);
    }

    private static class ViewHolder {
        private TextView mName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SearchResult item = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_result_listview, parent, false);
            viewHolder.mName = (TextView)convertView.findViewById(R.id.resultName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.mName.setText(item.name);
        return convertView;
    }
}
