package ua.kaganovych.persistencesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment {

    private SearchResultAdapter mResultAdapter;
    private ListView mResultListView;
    private ArrayList<SearchResult> mResultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search_result, container, false);
        mResultListView = (ListView)rootView.findViewById(R.id.searchResultList);
        mResultList = new ArrayList<>();
        String[] names = new String[] {"Katya", "Horosho", "Morojko", "Asfalt", "Kukan", "Malta"};

        for (String name : names) {
            mResultList.add(new SearchResult(name));
        }
        mResultAdapter = new SearchResultAdapter(getActivity(), mResultList);
        mResultListView.setAdapter(mResultAdapter);

        return rootView;
    }
}
