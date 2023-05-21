package com.example.diploma_v2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.VibrationEffect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diploma_v2.R;
import com.example.diploma_v2.entity.Order;
import com.example.diploma_v2.entity.Orders;

import java.util.ArrayList;

public class ItemsAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Order> objects;


    public ItemsAdapter(Context context, ArrayList<Order> orderedItemsList) {
        ctx = context;
        objects = orderedItemsList;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item_2, parent, false);
        }
        Order order = getOrder(position);

        ((TextView) view.findViewById(R.id.text1)).setText(order.getItemName());
        ((TextView) view.findViewById(R.id.text2)).setText(order.getCountFact().toString());
        ((TextView) view.findViewById(R.id.text3)).setText(order.getCount().toString());
        ((TextView) view.findViewById(R.id.text4)).setText("Ряд: " + order.getRow()
                + " Полка: " + order.getShelf());

        if (order.getCount().equals(order.getCountFact())) {
            ((TextView) view.findViewById(R.id.text1)).setBackgroundColor(Color.GREEN);
            ((TextView) view.findViewById(R.id.text2)).setBackgroundColor(Color.GREEN);
            ((TextView) view.findViewById(R.id.text3)).setBackgroundColor(Color.GREEN);
            ((TextView) view.findViewById(R.id.text4)).setBackgroundColor(Color.GREEN);
            ((TextView) view.findViewById(R.id.textDone)).setBackgroundColor(Color.GREEN);
            ((TextView) view.findViewById(R.id.textNeed)).setBackgroundColor(Color.GREEN);
            //((TextView) view.findViewById(R.id.text2)).setTextColor(Color.GREEN);

        }
        else if (order.getCount() > order.getCountFact() && order.getCountFact() > 0 || order.getCountFact() > order.getCount()){
            ((TextView) view.findViewById(R.id.text1)).setBackgroundColor(Color.RED);
            ((TextView) view.findViewById(R.id.text2)).setBackgroundColor(Color.RED);
            ((TextView) view.findViewById(R.id.text3)).setBackgroundColor(Color.RED);
            ((TextView) view.findViewById(R.id.text4)).setBackgroundColor(Color.RED);
            ((TextView) view.findViewById(R.id.textDone)).setBackgroundColor(Color.RED);
            ((TextView) view.findViewById(R.id.textNeed)).setBackgroundColor(Color.RED);
            //((TextView) view.findViewById(R.id.text2)).setTextColor(Color.RED);
        }

        return view;
    }

    Order getOrder(int position) {
        return ((Order) getItem(position));
    }

}
