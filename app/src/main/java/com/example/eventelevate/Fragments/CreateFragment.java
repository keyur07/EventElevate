package com.example.eventelevate.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.eventelevate.Activity.MainActivity;
import com.example.eventelevate.Adapter.ImageAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.Model.ServiceModel;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.Utils.RealPathUtil;
import com.example.eventelevate.databinding.FragmentCreateBinding;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment extends Fragment {

    FragmentCreateBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE = 123;
    private ImageAdapter imageAdapter;
    ArrayList<String> eventtype = new ArrayList<>();
    private List<Uri> selectedImageUris = new ArrayList<>();
    String[] paymentTypes = new String[]{"Select Payment Type","Hourly", "Per day"};
    private ServiceModel serviceList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      binding = FragmentCreateBinding.inflate(getLayoutInflater());
      init();
      return binding.getRoot();

    }

    public void init(){
        //  getCurrentLocation();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
        }
        binding.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(binding.editTitile.getText().toString(),binding.editPrice.getText().toString(),binding.editDescription.getText().toString(),
                        binding.editTermsandcondition.getText().toString(),"Kitchener,On,CA");
            }
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        binding.btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showProgress(getActivity());
                openGallery();
            }
        });

        binding.imageViewCreateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showProgress(getActivity());
                openGallery();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, paymentTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.editPaymentType.setAdapter(adapter);

        AppManager.showProgress(getActivity());
        getListOfeventCategory();

    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photos"), REQUEST_CODE);
    }

    private void SetImageListinRecyclerview(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(selectedImageUris);
        binding.recyclerView.setAdapter(imageAdapter);

        if(selectedImageUris.size()!=0){
            binding.parentRecyclerview.setVisibility(View.VISIBLE);
            binding.btnAddImage.setVisibility(View.GONE);
        }else {
            binding.btnAddImage.setVisibility(View.VISIBLE);
            binding.parentRecyclerview.setVisibility(View.GONE);
        }

        if (imageAdapter != null) {
            int itemCount = imageAdapter.getItemCount();
            binding.horizontal.fullScroll(View.FOCUS_RIGHT);
            binding.txtTotal.setText("Photos: "+imageAdapter.getItemCount()+"/10 - Choose your photos for listing");
        }
    }

    private void sendData(String title, String price, String decription, String terms, String location) {
        List<MultipartBody.Part> imageParts = new ArrayList<>();


        for (Uri uri : selectedImageUris) {
            String realPath = RealPathUtil.getRealPath(getActivity(), uri);
            Log.e("positions",realPath);
            String fileName = new File(realPath).getName(); // Extract file name from the real path
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(realPath));
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image[]", fileName, requestFile);
            imageParts.add(imagePart);
        }

        RequestBody userID = RequestBody.create(MediaType.parse("multipart/form-data"), AppManager.user.getUserid().toString());
        RequestBody titleRB = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "service");
        RequestBody paymentTypeRB = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editPaymentType.getSelectedItem().toString());
        RequestBody priceRB = RequestBody.create(MediaType.parse("multipart/form-data"), price);
        RequestBody descriptionRB = RequestBody.create(MediaType.parse("multipart/form-data"), decription);
        RequestBody termsRB = RequestBody.create(MediaType.parse("multipart/form-data"), terms);
        RequestBody locationRB = RequestBody.create(MediaType.parse("multipart/form-data"), location);
        RequestBody servicenameRB = RequestBody.create(MediaType.parse("multipart/form-data"), getSelectedServiceId());

        AppManager.showProgress(getActivity());

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<SignupModel> call = apiInterface.CreatePost(userID, titleRB, paymentTypeRB, priceRB, descriptionRB, termsRB, locationRB, servicenameRB, type,imageParts);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body() != null && response.body().getStatusCode() == 200) {
                    AppManager.hideProgress();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,new CreateFragment()).commit();
                    AppManager.StatusDialog(getActivity(),true,"successfully service created");
                }
            }
            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                AppManager.hideProgress();
                Log.e("failure", t.getMessage());
            }
        });
    }

    private String getSelectedServiceId() {
        for(int i=0;i<=eventtype.size();i++){
            if(serviceList.getServicetype().get(i).getServiceName().equals(binding.editCategory.getSelectedItem().toString())){
                return String.valueOf(serviceList.getServicetype().get(i).getServiceId());
            }
        }
        return "1";
    }
    private void getListOfeventCategory() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<ServiceModel> call = apiInterface.GetServiceType();
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

                AppManager.hideProgress();
                eventtype.add("Select Service");
                if(response.body().getStatusCode()==200){
                    serviceList = response.body();
                    for(int i=0;i<response.body().getServicetype().size();i++){
                        eventtype.add(response.body().getServicetype().get(i).getServiceName());
                        Log.e("position",""+response.body().getServicetype().get(i).getServiceId());
                    }
                    ArrayAdapter<String> eventadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, eventtype);
                    eventadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.editCategory.setAdapter(eventadapter);

                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {
                AppManager.hideProgress();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            selectedImageUris.add(imageUri);
                        }
                    } else if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        selectedImageUris.add(imageUri);
                    }

                    AppManager.hideProgress();
                    // Notify the adapter that the data has changed
                    SetImageListinRecyclerview();
                }
            }
        }
    }
}