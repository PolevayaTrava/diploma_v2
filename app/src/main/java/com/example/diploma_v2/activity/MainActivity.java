package com.example.diploma_v2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diploma_v2.Connection;
import com.example.diploma_v2.R;
import com.example.diploma_v2.api.LoginAPI;
import com.example.diploma_v2.entity.Employees;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText username;
    private EditText password;
    private Button login;

    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        username = (EditText)findViewById(R.id.edit_user);
        password = (EditText)findViewById(R.id.edit_password);
        login = (Button)findViewById(R.id.button_login);

        toolbar.setTitle("Авторизация");
    }

    public void Login(View view) {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        LoginAPI loginApi = Connection.getApi().create(LoginAPI.class);
        Call<Employees> call = loginApi.findByLogin(usernameText);
        call.enqueue(new Callback<Employees>() {
            @Override
            public void onResponse(Call<Employees> call, Response<Employees> response) {

                Boolean matchLogin = usernameText.equals(response.body().getLogin());
                Boolean matchPassword = passwordEncoder().matches(passwordText, response.body().getPassword());

                if (response.isSuccessful() && matchLogin && matchPassword) {
                    Toast.makeText(getApplicationContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employees> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ошибка подключения!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}