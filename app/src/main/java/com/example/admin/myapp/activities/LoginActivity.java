package com.example.admin.myapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.myapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.admin.myapp.utils.Constants.BASE_URL;
import static com.example.admin.myapp.utils.Constants.IS_LOGGED_OUT;
import static com.example.admin.myapp.utils.Constants.KEY_PASSWORD;
import static com.example.admin.myapp.utils.Constants.KEY_USERNAME;
import static com.example.admin.myapp.utils.Constants.LOGIN_URL;
import static com.example.admin.myapp.utils.Constants.PREFS_NAME;
import static com.example.admin.myapp.utils.Constants.TOKEN;
import static com.example.admin.myapp.utils.Constants.USER;


public class LoginActivity extends AppCompatActivity {
    private SharedPreferences prefs = null;

    private EditText editTextUsername;
    private EditText editTextPassword;
    String token = "";
    String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.login_userid);
        editTextPassword = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void login() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = BASE_URL + LOGIN_URL;

        //Read data from edit_text
        final String user_name = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            token = jsonObject.getString("token");
                            user = jsonObject.getString("username");
                            prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            prefs.edit().putString(TOKEN, token).apply();
                            prefs.edit().putString(USER, user).apply();
                            prefs.edit().putBoolean(IS_LOGGED_OUT, false).apply();

                            Toast.makeText(LoginActivity.this, token, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Display Error Toast
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_USERNAME, user_name);
                params.put(KEY_PASSWORD, password);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
