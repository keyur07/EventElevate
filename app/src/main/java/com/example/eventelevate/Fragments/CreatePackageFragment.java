package com.example.eventelevate.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.eventelevate.Adapter.ImageAdapter;
import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.EventtypeModel;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.Utils.RealPathUtil;
import com.example.eventelevate.databinding.FragmentCreatePackageBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
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

public class CreatePackageFragment extends Fragment {

    private FragmentCreatePackageBinding binding;
    private ImageAdapter imageAdapter;
    private List<Uri> selectedImageUris = new ArrayList<>();
    String[] paymentTypes = new String[]{"Select Payment Type","Hourly", "Per day"};
    ArrayList<String> eventtype = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePackageBinding.inflate(getLayoutInflater());
        init();
        return binding.getRoot();

    }
    public void init(){
        //  getCurrentLocation();

        binding.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(binding.editTitile.getText().toString(),binding.editPrice.getText().toString(),binding.editDescription.getText().toString(),
                        binding.editTermsandcondition.getText().toString(),"Kitchener,On,CA");
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

    private void sendData(String title, String price, String decription, String terms, String location) {
        List<MultipartBody.Part> imageParts = new ArrayList<>();

        for (Uri uri : selectedImageUris) {
            String realPath = RealPathUtil.getRealPath(getActivity(), uri);
            String fileName = new File(realPath).getName(); // Extract file name from the real path
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(realPath));
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image[]", fileName, requestFile);
            imageParts.add(imagePart);
        }

        RequestBody userID = RequestBody.create(MediaType.parse("multipart/form-data"), AppManager.user.getUserId().toString());
        RequestBody titleRB = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody paymentTypeRB = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editPaymentType.getSelectedItem().toString());
        RequestBody priceRB = RequestBody.create(MediaType.parse("multipart/form-data"), price);
        RequestBody descriptionRB = RequestBody.create(MediaType.parse("multipart/form-data"), decription);
        RequestBody termsRB = RequestBody.create(MediaType.parse("multipart/form-data"), terms);
        RequestBody locationRB = RequestBody.create(MediaType.parse("multipart/form-data"), location);
        RequestBody servicenameRB = RequestBody.create(MediaType.parse("multipart/form-data"), binding.editCategory.getSelectedItem().toString());

        AppManager.showProgress(getActivity());

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<SignupModel> call = apiInterface.CreatePackage(userID, titleRB, paymentTypeRB, priceRB, descriptionRB, termsRB, locationRB, servicenameRB, imageParts);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body() != null && response.body().getStatusCode() == 200) {
                    AppManager.hideProgress();
                    Log.e("success", response.body().getMessage());

                }else {
                    Log.e("success", ""+response.body().getStatusCode());
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                AppManager.hideProgress();
                Log.e("failure", t.getMessage());
            }
        });
    }


    private void getListOfeventCategory() {
        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<EventtypeModel> call = apiInterface.GetEventType();
        call.enqueue(new Callback<EventtypeModel>() {
            @Override
            public void onResponse(Call<EventtypeModel> call, Response<EventtypeModel> response) {

                AppManager.hideProgress();
                eventtype.add("Select event");
                if(response.body().getStatusCode()==200){
                   for(int i=0;i<response.body().getEventTypes().size();i++){
                       eventtype.add(response.body().getEventTypes().get(i).getEventTypeName());
                   }
                    ArrayAdapter<String> eventadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, eventtype);
                    eventadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.editCategory.setAdapter(eventadapter);

                }else if(response.body().getStatusCode()==201){
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<EventtypeModel> call, Throwable t) {
                AppManager.hideProgress();
            }
        });
    }

    private void getCurrentLocation() {

        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photos"), 1001);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
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