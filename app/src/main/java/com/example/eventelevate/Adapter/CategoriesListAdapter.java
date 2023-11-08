package com.example.eventelevate.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventelevate.Activity.ShowAllServices;
import com.example.eventelevate.Fragments.EventsFragment;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {

    private EventsFragment eventsFragment;
    public ArrayList<ServiceModel.Servicetype> servicetypes;

    public CategoriesListAdapter(EventsFragment eventsFragment, ArrayList<ServiceModel.Servicetype> servicetypes) {
        this.eventsFragment = eventsFragment;
        this.servicetypes = servicetypes;
    }

    @NonNull
    @Override
    public CategoriesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpage_view, parent, false);
        return new CategoriesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cates_name.setText(servicetypes.get(position).getServiceName());
        Log.e("datadata",""+servicetypes.get(position).getServiceId());
        getDatabyTable(String.valueOf(servicetypes.get(position).getServiceId()),holder);
        holder.txt_categories_see_all_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowAllServices.class);
                intent.putExtra("table_name",servicetypes.get(position).getServiceId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "name: "+servicetypes.get(position).getServiceName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDatabyTable(String serviceName, ViewHolder holder) {
        AppManager.showProgress((Activity) holder.itemView.getContext());
        try{
            APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
            Call<ServiceProviderModel> call = apiInterface.GetServicelistbytable(serviceName);
            call.enqueue(new Callback<ServiceProviderModel>() {
                @Override
                public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> response) {

                    AppManager.hideProgress();
                    if(response.body().getStatusCode()==200){
                        AppManager.hideProgress();
                        ServiceProviderModel servicetype =  response.body();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(eventsFragment.getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        holder.recyclerView.setLayoutManager(layoutManager);
                        ServiceTypeAdapter eventsListAdapter = new ServiceTypeAdapter(eventsFragment.getActivity(),servicetype,1);
                        holder.recyclerView.setAdapter(eventsListAdapter);
                    }

                }

                @Override
                public void onFailure(Call<ServiceProviderModel> call, Throwable t) {
                    AppManager.hideProgress();
                    // Snackbar snackbar = Snackbar.make(binding.container, "Something Went Wrong", 0);
                    //snackbar.show();
                }
            });
        }catch (Exception e){
            Toast.makeText(holder.itemView.getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return servicetypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public TextView cates_name;
        private TextView txt_categories_see_all_;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.item_list);
            cates_name = itemView.findViewById(R.id.txt_organizer);
            txt_categories_see_all_ = itemView.findViewById(R.id.txt_categories_see_all_);
        }
    }
}
