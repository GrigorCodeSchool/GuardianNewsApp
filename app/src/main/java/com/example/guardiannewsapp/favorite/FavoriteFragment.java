package com.example.guardiannewsapp.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardiannewsapp.adapters.ResultAdapter;
import com.example.guardiannewsapp.network.entities.Result;
import com.example.guardiannewsapp.network.retrofit.MainViewModel;
import com.example.guardiannewsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements ResultAdapter.OnFavoriteListener {
    private RecyclerView rvFavorite;
    private MainViewModel viewModel;
    private ResultAdapter resultAdapter;


    public FavoriteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorite = view.findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        resultAdapter = new ResultAdapter(getContext(),this);

        viewModel.getAllResultsFromDb().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                List<Result> results1 = new ArrayList<>();
                for (Result result:results) {
                    if (result.isFavorite()){
                        results1.add(result);
                    }
                }
                resultAdapter.setResultList(results1);
            }
        });
        rvFavorite.setAdapter(resultAdapter);
        return view;
    }

    @Override
    public void onFavoriteInserted(Result resultItem) {
        resultItem.setFavorite(true);
        viewModel.update(resultItem);
    }

    @Override
    public void onFavoriteDeleted(Result resultItem) {
        resultItem.setFavorite(false);
        viewModel.update(resultItem);
    }


}
