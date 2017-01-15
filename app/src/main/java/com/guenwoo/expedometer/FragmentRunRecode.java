package com.guenwoo.expedometer;

import android.content.Context;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

/**
 * Created by SPECIAL-GW on 2017-01-14.
 */

public class FragmentRunRecode extends Fragment
{
    public FragmentRunRecode()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragmentrecode, container, false);

        return view;
    }
}

