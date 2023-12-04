package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class PaymentActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 1;
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret,transactionId;
    int Amounts = 0;
    private TextView textView;
    String CustomerID, Ephemeral_keys, ClientServerId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_payment);
        findViewById(R.id.month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showProgress(PaymentActivity.this);
                StartPayment(19);
            }
        });

        findViewById(R.id.six_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showProgress(PaymentActivity.this);
                StartPayment(109);
            }
        });

        findViewById(R.id.year).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showProgress(PaymentActivity.this);
                StartPayment(199);
            }
        });

        PaymentConfiguration.init(this, getString(R.string.public_key));
        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            PaymentResults(paymentSheetResult);
        });

        textView = findViewById(R.id.privacy);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse("https://sites.google.com/view/videodownloderpolicy/home"));
                startActivity(intent);
            }
        });

    }

    private void PaymentResults(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            AppManager.hideProgress();
            StatusDialog(PaymentActivity.this, true, "Payments was Successfully");
        }
        if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            AppManager.StatusDialog(PaymentActivity.this, false, "Payments was Failed");
        }

        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            AppManager.StatusDialog(PaymentActivity.this, false, "Payments was Canceled");
        }
    }

    public void StatusDialog(Context context, boolean status, String message){

        if(status==true){
            Dialog download_completed = new Dialog(context);
            download_completed.setCancelable(false);
            download_completed.setContentView(R.layout.task_completed_view_dialog);
            download_completed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT ));
            download_completed.show();
            TextView textView= (TextView)download_completed.findViewById(R.id.message);
            textView.setText(message);

            download_completed.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    download_completed.dismiss();
                    AppManager.showProgress(PaymentActivity.this);
                    AddPaymentsDetails();

                }
            });
        }
    }

    private void AddPaymentsDetails() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<SignupModel> call = apiInterface.AddUserPaymentDetails(""+AppManager.user.getUserid(),""+Amounts,transactionId,CustomerID,"Success");
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                if(response.body().getStatusCode().equals(200)){
                    startActivity(new Intent(PaymentActivity.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(PaymentActivity.this,MainActivity.class));
                    finish();
                    Toast.makeText(PaymentActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {

            }
        });
    }

    private void StartPayment(int amount) {
        createCustomer(amount);
    }

    private void createCustomer(int amount) {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    CustomerID = jsonObject.getString("id");
                    getEphemeralKey(CustomerID, amount);

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

    private void getEphemeralKey(String customerID, int amount) {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Ephemeral_keys = jsonObject.getString("id");
                    getPaymentSecret(customerID, Ephemeral_keys, amount);
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

    private void getPaymentSecret(String customerID, String ephemeral_keys, int amount) {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ClientServerId = jsonObject.getString("client_secret");
                    transactionId = jsonObject.getString("id");
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
                params.put("amount", String.valueOf(amount * 100));
                params.put("currency", "usd");
                Amounts = amount;
                // Check the Stripe API documentation for the correct parameters
                // params.put("some_other_parameter", "value");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void MakeaPayments() {
        paymentSheet.presentWithPaymentIntent(ClientServerId, new PaymentSheet.Configuration("Abc Company", new PaymentSheet.CustomerConfiguration(CustomerID, Ephemeral_keys)));
    }

}