package com.rpjuvvadi.calendardialogfragmentexample.activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rpjuvvadi.calendardialogfragmentexample.R;
import com.rpjuvvadi.calendardialogfragmentexample.fragments.CalendarDialogFragment;

public class MainActivity extends AppCompatActivity implements CalendarDialogFragment.CalendarCallbacks {

    Button btnLaunchCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLaunchCalendar = (Button) findViewById(R.id.launch_calendar_btn);
        btnLaunchCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCalendar();
            }
        });
    }

    private void launchCalendar() {
        DialogFragment calendarDialogFragment = CalendarDialogFragment.newInstance();
        calendarDialogFragment.show(getSupportFragmentManager(), "Calendar");
    }
}
