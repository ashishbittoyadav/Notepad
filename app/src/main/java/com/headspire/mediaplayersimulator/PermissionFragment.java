package com.headspire.mediaplayersimulator;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PermissionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.permission_layout,container,false);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
        ,Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        return view;
    }
}
