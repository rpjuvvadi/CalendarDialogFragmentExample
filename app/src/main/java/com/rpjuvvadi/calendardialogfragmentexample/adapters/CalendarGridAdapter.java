package com.rpjuvvadi.calendardialogfragmentexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rpjuvvadi.calendardialogfragmentexample.R;
import com.rpjuvvadi.calendardialogfragmentexample.models.CalendarGridModel;
import com.rpjuvvadi.calendardialogfragmentexample.utilities.CalendarDateTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rpj on 8/2/16.
 */
public class CalendarGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<CalendarGridModel> datesList;
    AdapterCallback callback;

    public interface AdapterCallback {
        void onItemClick(int position);
    }

    public CalendarGridAdapter(Context context, ArrayList<CalendarGridModel> datesList, AdapterCallback callback) {
        this.context = context;
        this.datesList = datesList;
        this.callback = callback;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CalendarGridModel dateModel  = datesList.get(position);

        // Recycle the view
        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_calendar_cell, null);

            // GC
            inflater = null;
        }

        Calendar calendar = Calendar.getInstance();
        CalendarDateTextView calendarDateTV = (CalendarDateTextView) convertView.findViewById(R.id.cell_tv);

        switch ( dateModel.getStatus() ) {
            case "empty":
                convertView.setVisibility(View.GONE);
                break;

            case "disabled":
                calendar.setTime(dateModel.getDate());
                calendarDateTV.setText(""+calendar.get(Calendar.DAY_OF_MONTH));
                calendarDateTV.setTextViewType(CalendarDateTextView.DISABLED);
                break;

            case "available":
                calendar.setTime(dateModel.getDate());
                calendarDateTV.setText(""+calendar.get(Calendar.DAY_OF_MONTH));
                calendarDateTV.setTextViewType(CalendarDateTextView.AVAILABLE);
                calendarDateTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ( callback != null ) callback.onItemClick(position);
                    }
                });
                break;

            case "closed":
                calendar.setTime(dateModel.getDate());
                calendarDateTV.setText(""+calendar.get(Calendar.DAY_OF_MONTH));
                calendarDateTV.setTextViewType(CalendarDateTextView.CLOSED);
                calendarDateTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ( callback != null ) callback.onItemClick(position);
                    }
                });
                break;

            case "booked":
                calendar.setTime(dateModel.getDate());
                calendarDateTV.setText(""+calendar.get(Calendar.DAY_OF_MONTH));
                calendarDateTV.setTextViewType(CalendarDateTextView.BOOKED);
                break;

            case "requested":
                calendar.setTime(dateModel.getDate());
                calendarDateTV.setText(""+calendar.get(Calendar.DAY_OF_MONTH));
                calendarDateTV.setTextViewType(CalendarDateTextView.REQUESTED);
                // Do we need Click Listener for requested Dates?
                break;

            default:
                convertView.setVisibility(View.GONE);
                break;
        }

        // GC
        dateModel = null;
        return  convertView;
    }

    public void add(List<CalendarGridModel> list) {
        if ( list != null && list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    datesList.add(list.get(i));
                }
            }
            notifyDataSetChanged();
        }
    }

    public void changeStatus(int position, String status) {
        datesList.get(position).setStatus(status);
        notifyDataSetChanged();
    }

    public void clearAll() {
        datesList.clear();
        notifyDataSetChanged();
    }

    public void add(CalendarGridModel date) {
        datesList.add(date);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if ( datesList != null ) {
            return datesList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if ( datesList != null ) {
            return datesList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setAvailabilityFromHashMap(HashMap<String, String> availability) {
        DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

        Iterator iterator = datesList.iterator();
        while ( iterator.hasNext() ) {
            CalendarGridModel dateModel = (CalendarGridModel) iterator.next();
            if ( dateModel.getDate() != null ) {
                String status = availability.get(yyyyMMdd.format(dateModel.getDate()));
                if ( status != null && "available".equalsIgnoreCase(status) ) {
                    dateModel.setStatus("available");
                } else if ( status != null && "closed".equalsIgnoreCase(status) ) {
                    dateModel.setStatus("closed");
                } else if ( status != null && "booked".equalsIgnoreCase(status) ) {
                    dateModel.setStatus("booked");
                } else if ( status != null && "requested".equalsIgnoreCase(status) ) {
                    dateModel.setStatus("requested");
                }
            }
        }

        notifyDataSetChanged();

    }
}
