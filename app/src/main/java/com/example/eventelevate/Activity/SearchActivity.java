package com.example.eventelevate.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventelevate.Adapter.AllServiceAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.databinding.ActivitySearchBinding;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private List<ServiceProviderModel.Servicetype> itemList;
    private AllServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppManager.changeStatusBarandBottomColor(this);

        init();
    }

    private void init() {
        AppManager.showProgress(this);
        GetAllServiceList();
        binding.editTextSearch.requestFocus();

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the list based on the search query
                filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void GetAllServiceList() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<ServiceProviderModel> call = apiInterface.GetServicelist();
        call.enqueue(new Callback<ServiceProviderModel>() {
            @Override
            public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> response) {
                AppManager.hideProgress();
                if (response.body().getStatusCode() == 200) {
                    itemList = response.body().getServicetype();
                    adapter = new AllServiceAdapter(SearchActivity.this, itemList);
                    binding.serviceList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    binding.serviceList.setAdapter(adapter);
                } else if (response.body().getStatusCode() == 201) {
                    // Handle other status codes as needed
                }
            }

            @Override
            public void onFailure(Call<ServiceProviderModel> call, Throwable t) {
                // Handle network errors
            }
        });
    }

    private void filterList(String query) {
        if (adapter != null) {
            adapter.filterList(query);
        }
    }
}
