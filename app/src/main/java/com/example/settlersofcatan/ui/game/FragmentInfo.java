package com.example.settlersofcatan.ui.game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.settlersofcatan.R;

public class FragmentInfo extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rules, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton btn = getView().findViewById(R.id.backbutton);
        btn.setOnClickListener((v) -> getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit());
        Button tbn2 = getView().findViewById(R.id.button_video);
        tbn2.setOnClickListener((vi)->watchYoutubeVideo(getContext(),"8Yj0Y3GKE40"));
    }

    public static void watchYoutubeVideo(Context context, String id) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        context.startActivity(webIntent);
    }
}
