package com.example.eventelevate.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.ProfileActivity;
import com.example.eventelevate.Model.ServiceProviderModel;
import com.example.eventelevate.R;

import java.util.List;

public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ViewHolder> {
    private Activity activity;
    private int typ;
    private ServiceProviderModel servicetypes;
    public ServiceTypeAdapter(FragmentActivity activity, ServiceProviderModel servicetypes,int type) {
        this.activity = activity;
        this.typ = type;
        this.servicetypes = servicetypes;
    }

    @NonNull
    @Override
    public ServiceTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpage_item_view,parent,false);
        return new ServiceTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceTypeAdapter.ViewHolder holder, int position) {
        List<ServiceProviderModel.Servicetype> servicetypes1 = this.servicetypes.getServicetype();
        holder.name.setText(servicetypes1.get(position).getTitle());

        if(servicetypes1.get(position).getPaymenttype().equals("Hourly")){
            holder.price.setText("$"+servicetypes1.get(position).getPrice()+"/"+"hour");
        }else {
            holder.price.setText("$"+servicetypes1.get(position).getPrice()+"/"+"day");
        }
        Glide.with(activity).load(servicetypes1.get(position).getImages().get(0).getImages()).into(holder.imageView);

        if(typ==0){
            ViewGroup.LayoutParams layoutParams = holder.container.getLayoutParams();
            layoutParams.width = layoutParams.FILL_PARENT;
            holder.container.setLayoutParams(layoutParams);
        }else {
            ViewGroup.LayoutParams layoutParams = holder.container.getLayoutParams();
            layoutParams.width = 450;
            holder.container.setLayoutParams(layoutParams);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("datadata",""+servicetypes1.get(position).getUserid());
                Log.e("datadata",""+servicetypes1.get(position).getId());
                Intent intent = new Intent(activity, ProfileActivity.class);
                intent.putExtra("user_id",servicetypes1.get(position).getUserid().toString());
                intent.putExtra("service_id",servicetypes1.get(position).getId().toString());
                intent.putExtra("serviceName", servicetypes.getMessage());
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return servicetypes.getServicetype().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,price;
        private ImageView imageView;
        private CardView container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imageView =itemView.findViewById(R.id.icon);
            container = itemView.findViewById(R.id.container);

        }
    }
}
