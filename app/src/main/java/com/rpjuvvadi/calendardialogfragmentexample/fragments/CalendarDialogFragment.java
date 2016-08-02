package com.rpjuvvadi.calendardialogfragmentexample.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.rpjuvvadi.calendardialogfragmentexample.R;
import com.rpjuvvadi.calendardialogfragmentexample.adapters.CalendarGridAdapter;
import com.rpjuvvadi.calendardialogfragmentexample.models.CalendarGridModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rpj on 8/2/16.
 */
public class CalendarDialogFragment extends DialogFragment implements View.OnClickListener {

    // Declaration
    Calendar currentCalendar;
    GridView gvCalendar;
    TextView tvDialogTitle;
    Button btnNext;
    Button btnPrevious;
    CalendarGridAdapter adapter;

    CalendarCallbacks callback;

    public interface CalendarCallbacks { /* POC */ }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CalendarCallbacks) {
            callback = (CalendarCallbacks) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName()
                    + " must implement CalendarCallbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_calendar, container);
        setStyle(STYLE_NO_TITLE, 0);

        currentCalendar = Calendar.getInstance();
        if ( getArguments() != null ) {
            int month       = getArguments().getInt("MONTH", currentCalendar.get(Calendar.MONTH));
            int year        = getArguments().getInt("YEAR", currentCalendar.get(Calendar.YEAR));
            currentCalendar.set(year, month, 1);
        } else {
            currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        // UI Binding
        gvCalendar = (GridView) view.findViewById(R.id.calendar_gv);
        tvDialogTitle = (TextView) view.findViewById(R.id.dialog_title_tv);
        btnNext = (Button) view.findViewById(R.id.next_button);
        btnPrevious = (Button) view.findViewById(R.id.previous_button);

        // Click Listener
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupCalendar();
        refreshCalendar();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void setupCalendar() {
        // Setup Legend or,
        // Any other logic
    }

    private void refreshCalendar() {
        DateFormat fullMonthNameFormat = new SimpleDateFormat("MMMM");
        tvDialogTitle.setText(fullMonthNameFormat.format(currentCalendar.getTime())
                +", "+currentCalendar.get(Calendar.YEAR));
        refreshCalendarAdapterWithCurrentCalendar();
    }

    private void refreshCalendarAdapterWithCurrentCalendar() {

        // Local Calendar Instance
        Calendar mCalendar = Calendar.getInstance();

        // Manipulating local Calendar Object, currentCalendar is untouched.
        mCalendar.setTime(currentCalendar.getTime());

        // Initialize or Clear Adapter
        if ( adapter == null ) {
            adapter = new CalendarGridAdapter( getActivity(), new ArrayList<CalendarGridModel>(),
                    new CalendarGridAdapter.AdapterCallback() {

                        @Override
                        public void onItemClick(int position) {
                            // More Logic
                        }
                    });
        } else {
            adapter.clearAll();
        }

        // Initialize Month Start & End Dates
        int minDayOfMonth = 1;
        int maxDayOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        // Add Dummy Dates to Adapter
        for ( int i = 1; i < dayOfWeek; i++) {
            adapter.add(new CalendarGridModel(null, "empty"));
        }

        // Add Dates To Adapter
        for ( int i = minDayOfMonth; i <= maxDayOfMonth; i++ ) {
            mCalendar.set(Calendar.DAY_OF_MONTH, i);
            adapter.add(new CalendarGridModel(mCalendar.getTime(), "available"));
        }

        gvCalendar.setAdapter(adapter);
    }

    public CalendarDialogFragment() {}

    public static CalendarDialogFragment newInstance(int month, int year) {
        CalendarDialogFragment dialog = new CalendarDialogFragment();

        // Arguments
        Bundle arguments = new Bundle();
        arguments.putInt("MONTH", month);
        arguments.putInt("YEAR", year);

        // Set Arguments
        dialog.setArguments(arguments);
        return dialog;
    }

    public static CalendarDialogFragment newInstance() {
        CalendarDialogFragment dialog = new CalendarDialogFragment();
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_button) {
            currentCalendar.add(Calendar.MONTH, 1);
            refreshCalendar();
        } else if ( view.getId() == R.id.previous_button ) {
            currentCalendar.add(Calendar.MONTH, -1);
            refreshCalendar();
        }
    }
}