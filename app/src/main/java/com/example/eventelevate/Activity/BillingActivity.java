package com.example.eventelevate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.example.eventelevate.Adapter.CustomAdapter;
import com.example.eventelevate.Adapter.PaymentsAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.AboutUsModel;
import com.example.eventelevate.Model.PaymentDetailsModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivityBillingBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingActivity extends AppCompatActivity {

    ActivityBillingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppManager.changeStatusBarandBottomColor(this);
        GetPaymentDetails();

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void GetPaymentDetails(){
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<PaymentDetailsModel> call = apiInterface.GetUserPaymentsDetailsById(AppManager.user.getUserid().toString());
        call.enqueue(new Callback<PaymentDetailsModel>() {
            @Override
            public void onResponse(Call<PaymentDetailsModel> call, Response<PaymentDetailsModel> response) {
                if(response.body().getStatusCode()==200){
                    if (response.body().getMessage().equals("Payments details retrieved successfully")){
                        PaymentsAdapter customAdapter = new PaymentsAdapter(BillingActivity.this,response.body().getPayment());
                        binding.paymentList.setAdapter(customAdapter);
                        if(Integer.parseInt(response.body().getPayment().get((0)).getAmount())==19){
                            binding.plan.setText("$ "+response.body().getPayment().get((0)).getAmount()+"/1 Month");
                        } else if(Integer.parseInt(response.body().getPayment().get((0)).getAmount())==109){
                            binding.plan.setText("$ "+response.body().getPayment().get((0)).getAmount()+"/6 Month");
                        } else if(Integer.parseInt(response.body().getPayment().get((0)).getAmount())==199){
                            binding.plan.setText("$ "+response.body().getPayment().get((0)).getAmount()+"/1 Year");
                        }
                        Log.e("errroror",response.body().getPayment().get((0)).getAmount());

                        binding.endDate.setText(response.body().getPayment().get((0)).getEndDate());
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentDetailsModel> call, Throwable t) {

            }
        });
    }
}