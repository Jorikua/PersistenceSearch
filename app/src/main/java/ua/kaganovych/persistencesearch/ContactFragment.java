package ua.kaganovych.persistencesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    private ContactAdapter mContactAdapter;
    private ListView mContactListView;
    private ArrayList<Contact> mContactList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_contact, container, false);

        mContactListView = (ListView)rootView.findViewById(R.id.contactList);
        String[] names = new String[] {"Vasya", "Petya", "Kolya", "Serega", "Chernii", "Pepel", "Seva", "Shurik", "Uba",
                "Ches", "Kirill", "Toster", "Chipsi", "Karavan", "Omlet", "James", "Bond", "Iron Man", "Hulk", "Thor"};
        String[] cities = new String[] {"Dnepr", "Kiev", "Sloch", "Morjva", "Mars", "Tam", "London", "Capital", "Motherland", "Nowhere",
                "Somewhere", "Odindva", "Paris", "Valava", "Prussia", "Bratislava", "Kukuruza", "Vienna", "Berlin", "Lvov"};

        mContactList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            mContactList.add(new Contact(names[i], cities[i]));
        }

        mContactAdapter = new ContactAdapter(getActivity(), mContactList);
        mContactListView.setAdapter(mContactAdapter);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "ContactFragment Destroyed");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG", "ContactFragment Stopped");
    }
}
