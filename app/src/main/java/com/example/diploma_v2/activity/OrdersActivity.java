package com.example.diploma_v2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.example.diploma_v2.Connection;
import com.example.diploma_v2.R;
import com.example.diploma_v2.api.OrdersAPI;
import com.example.diploma_v2.entity.Customer;
import com.example.diploma_v2.entity.Employees;
import com.example.diploma_v2.entity.Orders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends Activity {

    private static OrdersAPI ordersAPI;
    private static ArrayList<Orders> ordersList = new ArrayList<>();
    private static Long selectedItem;
    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);

        listView = findViewById(R.id.orderListView);
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Заказы");

        List<Long> ordersId = new ArrayList<>();
        ordersList = new ArrayList<>();
        ArrayAdapter<Long> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ordersId);

        ordersAPI = Connection.getApi().create(OrdersAPI.class);
        Call<List<Orders>> call = ordersAPI.findAll();

        call.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {

                for (int i = 0; i < response.body().size(); i++) {
                    Long id = response.body().get(i).getOrderId();
                    String date = response.body().get(i).getDate();
                    String status = response.body().get(i).getStatus();
                    Customer customer = response.body().get(i).getCustomer();
                    Employees manager = response.body().get(i).getManager();
                    Employees picker = response.body().get(i).getPicker();
                    ordersId.add(i, id);
                    ordersList.add(i, new Orders(id, date, status, customer, manager, picker));
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                nextActivity(ordersList, position);
            }
        });


    }

    public void nextActivity(List<Orders> ordersList, int position) {
        String status = "Сборка";
        Long selectedItem = ordersList.get(position).getOrderId();
        changeStatus(status, position);

        Intent intent = new Intent(OrdersActivity.this, OrderActivity.class);
        intent.putExtra("id", selectedItem);
        intent.putExtra("position", position);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String status = "Готов";
            int position = data.getIntExtra("position", 1);
            changeStatus(status, position);
        }
    }

    public void changeStatus(String status, int position) {

        Orders orders = new Orders(
                ordersList.get(position).getOrderId(),
                ordersList.get(position).getDate(),
                ordersList.get(position).getStatus(),
                ordersList.get(position).getCustomer(),
                ordersList.get(position).getManager(),
                ordersList.get(position).getPicker());

        orders.setStatus(status);
        Call<Orders> call = ordersAPI.updateStatus(orders.getOrderId(), orders);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {

            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
    }
}
