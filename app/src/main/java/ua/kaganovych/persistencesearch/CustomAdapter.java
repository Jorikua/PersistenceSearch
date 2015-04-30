package ua.kaganovych.persistencesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Item> {
    public CustomAdapter(Context context, ArrayList<Item> list) {
        super(context, 0, list);
    }

    private static class ViewHolder {
        private TextView mSuggest;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
            viewHolder.mSuggest = (TextView)convertView.findViewById(R.id.suggest);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.mSuggest.setText(item.suggestion);

        return convertView;
    }
}
