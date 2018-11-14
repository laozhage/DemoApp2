package com.demo.bs.demoapp2.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;


public class BaseFragment extends Fragment{
    protected static String TAG_LOG = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG =this.getClass().getSimpleName();
    }

    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT).show();
    }

}
