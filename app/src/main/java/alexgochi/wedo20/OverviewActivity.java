package alexgochi.wedo20;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import alexgochi.wedo20.superb.Counter;

public class OverviewActivity extends Counter {
    PieChart pieChart;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        textView = (TextView) findViewById(R.id.image);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(4, 22, 4, 4);
        pieChart.setDragDecelerationFrictionCoef(0.25f);

        pieChart.setTransparentCircleRadius(61f);

        getCountToday();
        getCountTomorrow();
        getCountImportant();
        getCountWork();
        getCountSocial();

        final ArrayList<PieEntry> mValue = new ArrayList<>();

        if (mCountToday > 0) {
            mValue.add(new PieEntry(mCountToday, "Today"));
        }
        if (mCountTomorrow > 0) {
            mValue.add(new PieEntry(mCountTomorrow, "Tomorrow"));
        }
        if (mCountImportant > 0) {
            mValue.add(new PieEntry(mCountImportant, "Important"));
        }
        if (mCountWork > 0) {
            mValue.add(new PieEntry(mCountWork, "Work"));
        }
        if (mCountSocial > 0) {
            mValue.add(new PieEntry(mCountSocial, "Social"));
        }

        Description description = new Description();
        description.setText("TODO LIST");
        description.setTextColor(Color.WHITE);
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(2000, Easing.EasingOption.EaseInCubic);

        final PieDataSet mDataSet = new PieDataSet(mValue, "");
        mDataSet.setSliceSpace(3f);
        mDataSet.setSelectionShift(10f);
        mDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                String[] splits = e.toString().split(" ");
                String fix = splits[4].split("\\.")[0];

//                String fix = splits[4].substring(0,1);

//                int subs = e.toString().indexOf("x: 0.0 y: ");
//                String data = e.toString().substring(subs + 10);
//
                Toast.makeText(getApplicationContext(), "Value : " + fix + " List", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        PieData mData = new PieData(mDataSet);
        mData.setValueTextSize(15f);
        mData.setValueTextColor(Color.BLACK);

        pieChart.setData(mData);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }
}
