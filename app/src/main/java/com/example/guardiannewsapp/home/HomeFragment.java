package com.example.guardiannewsapp.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardiannewsapp.adapters.ResultAdapter;
import com.example.guardiannewsapp.details.DetailsFragment;
import com.example.guardiannewsapp.network.entities.Result;
import com.example.guardiannewsapp.network.retrofit.MainViewModel;
import com.example.guardiannewsapp.network.utils.ConnetivityUtils;
import com.example.guardiannewsapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ResultAdapter.OnFavoriteListener, ResultAdapter.OnitemClickListener {
    private ResultAdapter resultAdapter;
    private MainViewModel mainViewModel;
    private RecyclerView rvHome;


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvHome = view.findViewById(R.id.rv_home);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        resultAdapter = new ResultAdapter(getActivity(), this);

        if (ConnetivityUtils.isConnected(getActivity())) {
            mainViewModel.search();
            MutableLiveData<List<Result>> listMutableLiveData = mainViewModel.getResults();
            listMutableLiveData.observe(getActivity(), new Observer<List<Result>>() {
                @Override
                public void onChanged(List<Result> results) {
                    List<Result> cachedList = mainViewModel.getAllResultsFromDb().getValue();
                    if (!cachedList.isEmpty() && cachedList.containsAll(results) && results.containsAll(cachedList)) {
                        return;
                    } else {
                        mainViewModel.insertAll(results);
                    }

                }
            });
        }
        mainViewModel.getAllResultsFromDb().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                resultAdapter.setResultList(results);
            }
        });
        rvHome.setAdapter(resultAdapter);
        resultAdapter.setOnitemClickListener(this);
        return view;
    }

    @Override
    public void onFavoriteInserted(Result resultItem) {
        resultItem.setFavorite(true);
        mainViewModel.update(resultItem);
    }

    @Override
    public void onFavoriteDeleted(Result resultItem) {
        resultItem.setFavorite(false);
        mainViewModel.update(resultItem);


    }

    @Override
    public void onItemClicked(int position) {
        Result result = resultAdapter.getResultAt(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", result);
        Fragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, fragment).addToBackStack(null).commit();
    }
}
