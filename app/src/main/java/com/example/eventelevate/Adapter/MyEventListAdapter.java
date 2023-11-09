package com.example.eventelevate.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.MyServiceModel;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventListAdapter extends RecyclerView.Adapter<MyEventListAdapter.Viewholder> {
    private Activity activity;
    private List<MyServiceModel.Event> events;
    public MyEventListAdapter(FragmentActivity activity, List<MyServiceModel.Event> events) {
        this.activity = activity;
        this.events =events;
    }

    @NonNull
    @Override
    public MyEventListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myevents_view,parent,false);
        return new MyEventListAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventListAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView.getContext()).load(events.get(position).getImages().get(0).getImages()).into(holder.imageView);
        holder.header.setText(events.get(position).getTitle());
        holder.description.setText(events.get(position).getDescription());
        holder.price.setText("Price : $"+events.get(position).getPrice());
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOptionBottomDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView imageView,btn_edit;
        private TextView header,description,price;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            header = itemView.findViewById(R.id.header);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            btn_edit = itemView.findViewById(R.id.edit);
        }
    }


    @SuppressLint("MissingInflatedId")
    private void ShowOptionBottomDialog(int position){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        View bottom = LayoutInflater.from(activity).inflate(R.layout.items_of_create_service_bootom_dialog, null);
        bottomSheetDialog.setContentView(bottom);
        bottomSheetDialog.show();

        ((TextView)bottom.findViewById(R.id.txt_header)).setText("Edit Service");
        ((TextView)bottom.findViewById(R.id.first_text)).setText("Delete");
        ((TextView)bottom.findViewById(R.id.second_text)).setText("Edit");
        bottom.findViewById(R.id.btn_packages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBottomDialog(position);
            }
        });
        bottom.findViewById(R.id.btn_Events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      DeleteService(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void DeleteService(int position) {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<SignupModel> call = apiInterface.DeletePost(AppManager.user.getId().toString(),events.get(position).getId().toString());
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if(response.body().getStatusCode()==200){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Item deleted successfully");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {

            }
        });
    }

    @SuppressLint("MissingInflatedId")
    private void ShowBottomDialog(int position) {
        String[] paymentTypes = new String[]{"Select Payment Type","Hourly", "Per day"};
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity, R.style.BottomSheetDialog);
        View bottom = LayoutInflater.from(activity).inflate(R.layout.edit_service_item, null);
        bottomSheetDialog.setContentView(bottom);
        bottomSheetDialog.show();

        EditText title = bottom.findViewById(R.id.edit_title);
        EditText price = bottom.findViewById(R.id.edit_price);
        EditText description = bottom.findViewById(R.id.edit_description);
        EditText termsandcondition = bottom.findViewById(R.id.edit_termsandcondition);

        title.setText(events.get(position).getTitle());
        price.setText(events.get(position).getPrice());
        description.setText(events.get(position).getDescription());
        termsandcondition.setText(events.get(position).getTerms());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, paymentTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner )bottom.findViewById(R.id.edit_payment_type)).setAdapter(adapter);
        bottom.findViewById(R.id.btn_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateService(title,price,description,termsandcondition, ((Spinner )bottom.findViewById(R.id.edit_payment_type)).getSelectedItem().toString(),position,bottomSheetDialog);
            }
        });
    }

    private void UpdateService(EditText titles, EditText pricee, EditText descriptionn, EditText termsandconditionn, String type, int position, BottomSheetDialog bottomSheetDialog) {

        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), AppManager.user.getId().toString());
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),titles.getText().toString());
        RequestBody paymentType = RequestBody.create(MediaType.parse("text/plain"),type );
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), pricee.getText().toString());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), descriptionn.getText().toString());
        RequestBody terms = RequestBody.create(MediaType.parse("text/plain"), termsandconditionn.getText().toString());
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), "Kitchener, ON, CA");
        RequestBody serviceId = RequestBody.create(MediaType.parse("text/plain"), events.get(position).getId().toString());

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<SignupModel> call = apiInterface.UpdatePost(userID, title, paymentType, price, description, terms, location, serviceId);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body() != null && response.body().getStatusCode() == 200) {
                    AppManager.hideProgress();
                    bottomSheetDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                AppManager.hideProgress();
                bottomSheetDialog.dismiss();
            }
        });
    }
}
