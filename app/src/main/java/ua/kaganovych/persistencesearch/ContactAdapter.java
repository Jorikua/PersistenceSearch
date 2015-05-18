package ua.kaganovych.persistencesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    private static class ViewHolder {
        private TextView mName;
        private TextView mCity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contact item = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact_listview, parent, false);
            viewHolder.mName = (TextView)convertView.findViewById(R.id.name);
            viewHolder.mCity = (TextView)convertView.findViewById(R.id.city);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.mName.setText(item.name);
        viewHolder.mCity.setText(item.city);

        return convertView;
    }
}
