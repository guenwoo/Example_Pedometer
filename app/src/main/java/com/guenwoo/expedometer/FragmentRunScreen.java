package com.guenwoo.expedometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import com.nhn.android.nmaps.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SPECIAL-GW on 2017-01-14.
 */

public class FragmentRunScreen extends Fragment
{

    Button BtnStart; // Start, Eng Toggle
    TextView tvCounting; // Walking Count

    // Serivce , Intent
    Intent SensorIntent;
    BroadcastReceiver receiver;

    String strBroadCast;
    boolean nFlag = true;

    public FragmentRunScreen()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragmentscreen, container, false);

        Bundle bundle = getArguments();
        boolean bundleStart = false;

        SensorIntent = new Intent(getActivity(),SensorService.class);
        tvCounting = (TextView)view.findViewById(R.id.textView2);
        receiver = new CountingRecever();

        BtnStart = (Button)view.findViewById(R.id.btn_toggle);

        if(bundle!= null && bundle.getBoolean("isStart") == true)
        {
            // Prefrence
            SharedPreferences prefs = getActivity().getSharedPreferences("Setting",Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            if(nFlag)
            {
                BtnStart.setText("STOP");
                try{

                    String oTime = "";
                    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd");

                    // Preference 값 - 로드
                    String saveTime = prefs.getString("Days", "");

                    int compare = oTime.compareTo( saveTime ); // 날짜비교

                    if(compare != 0)
                        values.Step = 0;

                    // Preference 값 - 설정
                    Date currentTime = new Date();
                    oTime = mSimpleDateFormat.format ( currentTime );

                    edit.putBoolean("isStart" , nFlag);
                    edit.putString("Days",oTime);
                    edit.commit();

                    // 종류 후 재 시작 시 회수 표기
                    tvCounting.setText( values.Step);

                    IntentFilter mainFilter = new IntentFilter("com.guenwoo.expedometer.step");

                    // Receiver , Service
                    getActivity().registerReceiver(receiver, mainFilter);
                    getActivity().startService(SensorIntent);
                }
                catch (Exception e) {}

                // Flag 값 변경 필요
                nFlag = false;
            }
        }
        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Prefrence
                SharedPreferences prefs = getActivity().getSharedPreferences("Setting",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();

                if(nFlag)
                {
                    BtnStart.setText("STOP");
                    try{

                        String oTime = "";
                        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd");

                        // Preference 값 - 로드
                        String saveTime = prefs.getString("Days", "");

                        int compare = oTime.compareTo( saveTime ); // 날짜비교

                        if(compare != 0)
                        {
                            // 날짜 변경에 따른 Counting 초기화
                            values.Step = 0;
                        }
                        // Preference 값 - 설정
                        Date currentTime = new Date();
                        oTime = mSimpleDateFormat.format ( currentTime );

                        edit.putBoolean("isStart" , nFlag);
                        edit.putString("Days",oTime);
                        edit.commit();

                        IntentFilter mainFilter = new IntentFilter("com.guenwoo.expedometer.step");

                        // Receiver , Service
                        getActivity().registerReceiver(receiver, mainFilter);
                        getActivity().startService(SensorIntent);
                    }
                    catch (Exception e) {}
                }
                else
                {
                    BtnStart.setText("START");

                    try{

                        // Receiver , Service 해제
                        getActivity().unregisterReceiver(receiver);
                        getActivity().stopService(SensorIntent);

                        edit.putBoolean("isStart" , nFlag);
                        edit.commit();

                    }
                    catch (Exception e) {}
                }
                // Flag 값 변경
                nFlag = !nFlag;
            }
        });

        return view;
    }

    class CountingRecever extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            strBroadCast = intent.getStringExtra("serviceData");
            tvCounting.setText(strBroadCast);
        }
    }
}
