package com.example.eventelevate.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Activity.FirebaseActivity;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.LoginModel;
import com.example.eventelevate.Model.MessageModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseMessageListAdapter extends RecyclerView.Adapter<FirebaseMessageListAdapter.ViewHolder> {
    private ArrayList<MessageModel>  data;
    int clientId,userId;
    //private final Fragment eventFragment;

    Context context;

    public FirebaseMessageListAdapter(ArrayList<MessageModel> data, Context context, int ul, int cl) {
            this.data =data;
            this.context = context;
        this.userId=ul;
            this.clientId=cl;

    }

    @NonNull
    @Override
    public FirebaseMessageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelist_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseMessageListAdapter.ViewHolder holder, int position) {
        MessageModel item = data.get(position);
        if (item.getUserId() != null && item.getUserid2() != null) {

            holder.lluser1.setVisibility(View.VISIBLE);
            holder.keyTextView.setText(item.getStatus());
            GetUserByID(item.getUserId(),holder);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FirebaseActivity.class);

                String message = "Hello, Second Activity!";
                intent.putExtra("msg", message);
                intent.putExtra("status", item.getStatus());
                intent.putExtra("userId", item.getUserId());
                intent.putExtra("clientId", item.getUserid2());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class
    ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lluser1,lluser2;
        private ImageView imageView;
        private TextView keyTextView,keyTextView1;
        private TextView valueTextView,valueTextView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            keyTextView = itemView.findViewById(R.id.keyTextView);
            valueTextView = itemView.findViewById(R.id.valueTextView);
            lluser1 = itemView.findViewById(R.id.lluser1);
            imageView = itemView.findViewById(R.id.profile_PHOTO);

        }
    }

    public void GetUserByID(String userId, ViewHolder holder){
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<LoginModel> call = apiInterface.GetUserDetailsById(userId);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.body().getStatusCode()==200){
                    if(response.body().getMessage().trim().equals("User details retrieved successfully")){
                        Glide.with(context).load(response.body().getUser().getPhoto()).into(holder.imageView);
                        holder.valueTextView.setText(response.body().getUser().getFirstName()+" "+response.body().getUser().getLastName());
                    }
                }else if(response.body().getStatusCode()==201){

                }

                Log.e("errror",response.body().getMessage());

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                AppManager.hideProgress();
//                Toast.makeText((Activity) context.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("errror",t.getMessage());
            }
        });

    }
}