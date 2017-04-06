package com.stone.saveimage2systemalbum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author stone
 * @date 17/3/24
 */

public class ImageFragment extends Fragment {

    public static ImageFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("data", url);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
        String url = getData();
        if(url.toLowerCase().endsWith(".gif")) {
            Glide.with(this).load(url).asGif().into(imageView);
        } else {
            Glide.with(this).load(url).into(imageView);
        }
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SaveImageDialogFragment.show(getActivity(), getData());
                return true;
            }
        });

        return rootView;
    }


    private String getData() {
        return getArguments().getString("data", "");
    }
}
