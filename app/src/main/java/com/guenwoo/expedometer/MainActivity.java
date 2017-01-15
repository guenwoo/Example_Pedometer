package com.guenwoo.expedometer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button RunRecode = (Button)findViewById(R.id.btn_recode);
        Button RunScreen = (Button)findViewById(R.id.btn_screen);
        Button RunMap = (Button)findViewById(R.id.btn_map);

        RunRecode.setOnClickListener(mClickListener);
        RunScreen.setOnClickListener(mClickListener);
        RunMap.setOnClickListener(mClickListener);

        SharedPreferences pref = getSharedPreferences("Setting",0);
        boolean isStart = pref.getBoolean("isStart", false);

        if(isStart == true)
        {
            Fragment fr = new FragmentRunScreen();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_home, fr);
            fragmentTransaction.commit();

            Bundle bundle = new Bundle();
            bundle.putBoolean("isStart", isStart);
            fr.setArguments(bundle);
        }
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {

        Fragment fr; // Fragment
        //RunScreen = new FragmentRunScreen();
        //RunRecode = new FragmentRunRecode();

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_recode:
                    fr = new FragmentRunRecode();
                    break;
                case R.id.btn_screen:
                    fr = new FragmentRunScreen();
                    break;
                case R.id.btn_map:
                    fr = new FragmentMap();
                    break;

            }

            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_home, fr);
            fragmentTransaction.commit();
        }
    };
}
