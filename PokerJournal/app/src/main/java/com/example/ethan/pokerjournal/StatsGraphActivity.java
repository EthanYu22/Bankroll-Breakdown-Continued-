package com.example.ethan.pokerjournal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class StatsGraphActivity extends AppCompatActivity
{

    DatabaseHelper db;
    List<Session> sessionList;
    LineGraphSeries<DataPoint> series;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_stats_graph);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(getApplicationContext());
        sessionList = db.getAllSessions();


        SessionArrayAdapter adapter = new SessionArrayAdapter(getApplicationContext(), sessionList);

        // Sorts Sessions by Date
        adapter.sort(new Comparator<Session>()
        {
            public int compare(Session arg0, Session arg1)
            {
                return arg0.date.compareTo(arg1.date);
            }
        });

        // generate Dates
        Date[] dates = new Date[sessionList.size()];
        for (int i = 0; i < sessionList.size() - 1; i++)
        {
            dates[i] = new Date(sessionList.get(i).getConvertedDateMMddyyyy());
        }

        GraphView graph = (GraphView) findViewById(R.id.statsGraph);

        DataPoint[] dataPoints = new DataPoint[sessionList.size()];

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        int netBankroll = 0;


        for (int i = 0; i < sessionList.size() - 1; i++)
        {
            netBankroll += (sessionList.get(i).getCashOut() - sessionList.get(i).getBuyIn());
            dataPoints[i] = new DataPoint(dates[i], netBankroll);
            series.appendData(dataPoints[i], true, 100);
        }


        graph.addSeries(series);


        // you can directly pass Date objects to DataPoint-Constructor


        // this will convert the Date to double via Date#getTime()





        /*double y,x;
        x = -5.0;

        GraphView graph = (GraphView) findViewById(R.id.statsGraph);
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i<500;i++) {
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 500);
        }
        graph.addSeries(series);*/

        /*
        // generate Dates
        Date[] dates = new Date[sessionList.size()];
        for(int i = 0; i < sessionList.size() - 1; i++){
            dates[i] = new Date(sessionList.get(i).getConvertedDateMMddyyyy());
        }

        GraphView graph = (GraphView) findViewById(R.id.statsGraph);

// you can directly pass Date objects to DataPoint-Constructor


// this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(dates[0], (sessionList.get(0).getCashOut() - sessionList.get(0).getBuyIn())),
                new DataPoint(dates[1], (sessionList.get(1).getCashOut() - sessionList.get(1).getBuyIn())),
                new DataPoint(dates[2], (sessionList.get(2).getCashOut() - sessionList.get(2).getBuyIn()))
        });

        */


        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Date");

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-15000);
        graph.getViewport().setMaxY(15000);

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        Date dMin = new Date(sessionList.get(0).getConvertedDateMMddyyyy());
        Date dMax = new Date(sessionList.get(sessionList.size() / 2 - 1).getConvertedDateMMddyyyy());
        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(dMin.getTime());
        graph.getViewport().setMaxX(d1.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling

        graph.getGridLabelRenderer().setNumVerticalLabels(21);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Bankroll");

        graph.setTitle("Bankroll Breakdown");
        graph.setTitleTextSize(120);
        graph.setTitleColor(R.color.colorPrimaryDark);

    }

    // Functionality of Toolbar Back Arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        int id = menuItem.getItemId();

        if (id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


}