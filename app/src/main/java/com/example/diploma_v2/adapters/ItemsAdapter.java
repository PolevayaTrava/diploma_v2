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
import java.util.HashMap;

public class ItemsAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Order> objects;
    Integer countGet = 0;
    HashMap<Long, Integer> countDone = new HashMap<>();


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

    public HashMap<Long, Integer> getCountDone() { return countDone; }
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
            ((TextView) view.findViewById(R.id.text1)).setBackgroundColor(Color.parseColor("#b1ff9a"));
            ((TextView) view.findViewById(R.id.text2)).setBackgroundColor(Color.parseColor("#b1ff9a"));
            ((TextView) view.findViewById(R.id.text3)).setBackgroundColor(Color.parseColor("#b1ff9a"));
            ((TextView) view.findViewById(R.id.text4)).setBackgroundColor(Color.parseColor("#b1ff9a"));
            ((TextView) view.findViewById(R.id.textDone)).setBackgroundColor(Color.parseColor("#b1ff9a"));
            ((TextView) view.findViewById(R.id.textNeed)).setBackgroundColor(Color.parseColor("#b1ff9a"));
            countDone.put(order.getOrders().getOrderId(), countGet+=1);

        }
        else if (order.getCount() > order.getCountFact() && order.getCountFact() > 0 || order.getCountFact() > order.getCount()){
            ((TextView) view.findViewById(R.id.text1)).setBackgroundColor(Color.parseColor("#d45b4c"));
            ((TextView) view.findViewById(R.id.text2)).setBackgroundColor(Color.parseColor("#d45b4c"));
            ((TextView) view.findViewById(R.id.text3)).setBackgroundColor(Color.parseColor("#d45b4c"));
            ((TextView) view.findViewById(R.id.text4)).setBackgroundColor(Color.parseColor("#d45b4c"));
            ((TextView) view.findViewById(R.id.textDone)).setBackgroundColor(Color.parseColor("#d45b4c"));
            ((TextView) view.findViewById(R.id.textNeed)).setBackgroundColor(Color.parseColor("#d45b4c"));
        }

        return view;
    }

    Order getOrder(int position) {
        return ((Order) getItem(position));
    }

}
