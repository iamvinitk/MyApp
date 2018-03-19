package com.example.admin.myapp.activities;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;


import static com.example.admin.myapp.utils.Constants.BASE_URL;
import static com.example.admin.myapp.utils.Constants.KEY_EMAIL;
import static com.example.admin.myapp.utils.Constants.KEY_FIRSTNAME;
import static com.example.admin.myapp.utils.Constants.KEY_LASTNAME;
import static com.example.admin.myapp.utils.Constants.KEY_PASSWORD;
import static com.example.admin.myapp.utils.Constants.KEY_USERNAME;
import static com.example.admin.myapp.utils.Constants.SIGNUP_URL;


public class SignUpActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstname);
        editTextLastName = findViewById(R.id.editTextLastname);

        Button signupButton = findViewById(R.id.buttonRegister);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = BASE_URL + SIGNUP_URL;

        //Read data from edit_text
        final String user_name = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String first_name = editTextFirstName.getText().toString().trim();
        final String last_name = editTextLastName.getText().toString().trim();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Display Error Toast
                Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_USERNAME, user_name);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_FIRSTNAME, first_name);
                params.put(KEY_LASTNAME, last_name);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}