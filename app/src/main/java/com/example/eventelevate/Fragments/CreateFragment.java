package com.example.eventelevate.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eventelevate.Activity.MainActivity;
import com.example.eventelevate.Adapter.ImageAdapter;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.FragmentCreateBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class CreateFragment extends Fragment {

    FragmentCreateBinding binding;
    private static final int REQUEST_CODE = 123;
    private ImageAdapter imageAdapter;
    private List<Uri> selectedImageUris = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      binding = FragmentCreateBinding.inflate(getLayoutInflater());
      init();
      return binding.getRoot();

    }

    public void init(){
        ShowBottomDialog();
      //  getCurrentLocation();

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
    }

    private void getCurrentLocation() {

        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                showSettingsDialog();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
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
    @SuppressLint("MissingInflatedId")
    private void ShowBottomDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialog);
        View bottom = LayoutInflater.from(getActivity()).inflate(R.layout.items_of_create_service_bootom_dialog, null);
        bottomSheetDialog.setContentView(bottom);
        bottomSheetDialog.show();

        bottom.findViewById(R.id.btn_Events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottom.findViewById(R.id.btn_packages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
            }
        });
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