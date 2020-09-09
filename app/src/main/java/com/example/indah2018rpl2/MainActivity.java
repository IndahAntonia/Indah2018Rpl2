package com.example.indah2018rpl2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    String username, password;
    private ProgressDialog progressBar;

    SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Model user = new Model();
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar = new ProgressDialog(MainActivity.this);
                progressBar.setMessage("Please wait");
                progressBar.show();
                progressBar.setCancelable(false);
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());

                mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);
                AndroidNetworking.post("https://192.168.43.32/RentalKamera/Login.php")
                        .addBodyParameter("email", etUsername.getText().toString())
                        .addBodyParameter("password", etPassword.getText().toString())
                        .addBodyParameter("roleuser", "1")
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: " + response);
                                try {
                                    JSONObject respon = response.getJSONObject("hasil");
                                    boolean sukses = respon.getBoolean("respon");
                                    if (sukses) {


                                        SharedPreferences.Editor editor = mLogin.edit();
                                        editor.putString("username", etUsername.getText().toString());
                                        editor.putString("password", etPassword.getText().toString());
                                        editor.apply();


                                        Intent in = new Intent(MainActivity.this, Dashboard.class);
                                        startActivity(in);
                                        finish();
                                        if (progressBar.isShowing()) {
                                            progressBar.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_LONG).show();


                                    }
                                } catch (JSONException e) {
                                    if (progressBar.isShowing()) {
                                        progressBar.dismiss();
                                    }

                                }


                            }

                            @Override
                            public void onError(ANError anError) {
                                if (progressBar.isShowing()) {
                                    progressBar.dismiss();
                                }
                                System.out.println("lala4");
                                Log.d("errorku", "onError: " + anError.getErrorCode());
                                Log.d("errorku", "onError: " + anError.getErrorBody());
                                Log.d("errorku", "onError: " + anError.getErrorDetail());
                            }


                        });
            }

        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
