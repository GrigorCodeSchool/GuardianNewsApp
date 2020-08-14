package com.example.guardiannewsapp.details;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.guardiannewsapp.network.entities.Result;
import com.example.guardiannewsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvType;
    private TextView tvSectionName;
    private TextView tvWebTitle;


    public DetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ivImage = view.findViewById(R.id.iv_detail_image);
        tvTitle = view.findViewById(R.id.tv_detail_title);
        tvType = view.findViewById(R.id.tv_detail_type);
        tvSectionName = view.findViewById(R.id.tv_detail_sectionName);
        tvWebTitle = view.findViewById(R.id.tv_detail_webTitle);
        Bundle bundle = getArguments();
        Result result = (Result) bundle.getSerializable("result");
        Glide.with(this).load(result.getFields().getThumbnail()).into(ivImage);
        tvTitle.setText(result.getWebTitle());
        tvType.setText(result.getType());
        tvSectionName.setText(result.getSectionName());
        tvWebTitle.setText(result.getWebTitle());
        return view;
    }

}
