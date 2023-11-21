package com.example.eventelevate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 1;
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    String CustomerID,Ephemeral_keys,ClientServerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartPayment(200);
            }
        });

        PaymentConfiguration.init(this,getString(R.string.public_key));
        paymentSheet = new PaymentSheet(this,paymentSheetResult -> {
            PaymentResults(paymentSheetResult);
        });



    }

    private void PaymentResults(PaymentSheetResult paymentSheetResult) {
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            AppManager.StatusDialog(PaymentActivity.this,true,"Payments was Successfully");
        }
        if(paymentSheetResult instanceof PaymentSheetResult.Failed){
            AppManager.StatusDialog(PaymentActivity.this,false,"Payments was Failed");
        }

        if(paymentSheetResult instanceof PaymentSheetResult.Canceled){
            AppManager.StatusDialog(PaymentActivity.this,false,"Payments was Canceled");
        }
    }

    private void StartPayment(int amount) {
        createCustomer();
    }

    private void createCustomer() {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    CustomerID = jsonObject.getString("id");
                    Toast.makeText(PaymentActivity.this, "Success 1", Toast.LENGTH_SHORT).show();
                    getEphemeralKey(CustomerID);

                } catch (JSONException e) {
                    Log.e("error", e.toString());
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", "Error: " + error.toString());
                if (error.networkResponse != null) {
                    Log.e("Volley Error", "Status Code: " + error.networkResponse.statusCode);
                    Log.e("Volley Error", "Response Data: " + new String(error.networkResponse.data));
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + getString(R.string.private_key));
                return header;
            }
        };

        queue.add(stringRequest);
    }

    private void getEphemeralKey(String customerID) {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Ephemeral_keys = jsonObject.getString("id");
                    Toast.makeText(PaymentActivity.this, "Success 2", Toast.LENGTH_SHORT).show();
                    getPaymentSecret(customerID, Ephemeral_keys);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", "Error: " + error.toString());
                if (error.networkResponse != null) {
                    Log.e("Volley Error", "Status Code: " + error.networkResponse.statusCode);
                    Log.e("Volley Error", "Response Data: " + new String(error.networkResponse.data));
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + getString(R.string.private_key));
                header.put("Stripe-Version", "2023-10-16");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void getPaymentSecret(String customerID, String ephemeral_keys) {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ClientServerId = jsonObject.getString("client_secret");
                    Toast.makeText(PaymentActivity.this, "Success 3", Toast.LENGTH_SHORT).show();
                    Toast.makeText(PaymentActivity.this, ClientServerId, Toast.LENGTH_SHORT).show();

                    MakeaPayments();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + getString(R.string.private_key));
                header.put("Stripe-Version", "2023-10-16");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", "1000");
                params.put("currency", "usd");
                // Check the Stripe API documentation for the correct parameters
                // params.put("some_other_parameter", "value");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void MakeaPayments() {
        paymentSheet.presentWithPaymentIntent(ClientServerId,new PaymentSheet.Configuration("Abc Company", new PaymentSheet.CustomerConfiguration(CustomerID,Ephemeral_keys)));
    }

}