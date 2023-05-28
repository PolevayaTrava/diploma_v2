package com.example.diploma_v2.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.diploma_v2.Connection;
import com.example.diploma_v2.R;
import com.example.diploma_v2.adapters.ItemsAdapter;
import com.example.diploma_v2.api.OrderAPI;
import com.example.diploma_v2.api.OrdersAPI;
import com.example.diploma_v2.entity.Items;
import com.example.diploma_v2.entity.Order;
import com.example.diploma_v2.entity.Orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends Activity {

    private TextView textView;
    private Toolbar toolbar;
    private ListView listView;

    private static ArrayList<Order> orderedItemsList = new ArrayList<>();
    private static HashMap<Long, ArrayList<Order>> ordersOrderedItems = new HashMap<>();

    private static OrderAPI orderAPI = Connection.getApi().create(OrderAPI.class);
    private static ItemsAdapter adapter;

    private static Long id;
    private static Orders orders;
    private static Items items;
    private static Integer count;
    private static Integer countFact;

    private static String scanResult;
    private static Long orderId;
    private static Integer position;
    private static Integer newCountFact;
    private static Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        textView = findViewById(R.id.search_edit_text);
        listView = findViewById(R.id.orderedItemsListView);
        toolbar = findViewById(R.id.toolbar3);

        orderId = getIntent().getExtras().getLong("id");
        position = getIntent().getExtras().getInt("position");
        getData();
    }

    public void getAdapter() {
        adapter = new ItemsAdapter(this, ordersOrderedItems.get(orderId));
    }

    public void getData() {
        Call<List<Order>> call = orderAPI.findById(orderId);

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                if (ordersOrderedItems.get(orderId) == null) {
                    orderedItemsList = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {

                        id = response.body().get(i).getId();
                        items = response.body().get(i).getItems();
                        orders = response.body().get(i).getOrders();
                        count = response.body().get(i).getCount();
                        countFact = response.body().get(i).getCountFact();

                        orderedItemsList.add(new Order(id, orders, items, count, countFact));
                    }
                    ordersOrderedItems.put(orderId, orderedItemsList);
                }
                getAdapter();
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    public void scanClick(View view) {
        checkCameraPermission();
    }

    public void findClick(View view) {
        String findCode = textView.getText().toString();
        if (!findCode.isEmpty()) {
            if (!findItem(findCode)) {
                Toast.makeText(getApplicationContext(), "Товар не найден!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Строка поиска пустая!", Toast.LENGTH_SHORT).show();
        }
    }

    public void exitClick(View view) {
        exitAlert();
    }

    public boolean findItem(String findCode) {
        orderedItemsList = ordersOrderedItems.get(orderId);

        for (Order order : orderedItemsList) {
            if (order.getItemId().equals(findCode)) {
                countAlert(order, findCode, position);
                return true;
            }
        }
        return false;
    }

    public void countAlert(Order order, String findCode, Integer position) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        final EditText editCount = (EditText) view.findViewById(R.id.editCount);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(order.getItemName());
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!editCount.getText().toString().isEmpty()) {
                    if (order.getCountFact() < order.getCount()) {
                        newCountFact = order.getCountFact() + Integer.parseInt(editCount.getText().toString());
                    }
                    else if (order.getCountFact() > order.getCount()) {
                        newCountFact = Integer.parseInt(editCount.getText().toString());
                    }
                } else {
                    newCountFact = order.getCountFact() + 1;
                }

                if (newCountFact > order.getCount() || newCountFact > 0 && newCountFact < order.getCount()) {
                    Toast.makeText(getApplicationContext(), "Ошибка в количестве товара!", Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(VibrationEffect.
                            createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                else if (newCountFact.equals(order.getCount())) {
                }
                order.setCountFact(newCountFact);
                Call<Order> call = orderAPI.updateOrder(orderId, Long.valueOf(findCode), order);

                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                    }
                });
                adapter.notifyDataSetChanged();
                newCountFact = 0;
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }
    public void exitAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Внимание");
        if (adapter.getCountDone().get(orderId) == orderedItemsList.size()) {
            builder.setMessage("Вы уверены, что хотите закрыть заказ?");
        }
        else {
            builder.setMessage("Вы уверены, что хотите выйти?");
            position = -1;
        }
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(OrderActivity.this, OrdersActivity.class);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        })
        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            String[] permission = {"Manifest.permission.CAMERA"};
            ActivityCompat.requestPermissions(this, permission, 200);
        }
        else {
            Intent intent = new Intent(OrderActivity.this, ScanActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(OrderActivity.this, ScanActivity.class);
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        scanResult = data.getStringExtra("scanResult");
        findItem(scanResult);
    }
}
