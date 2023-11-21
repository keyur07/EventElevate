package com.example.eventelevate.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventelevate.Interfaces.APIInterface;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.DocumentsUploadModel;
import com.example.eventelevate.Model.SignupModel;
import com.example.eventelevate.R;
import com.example.eventelevate.Service.RetrofitClient;
import com.example.eventelevate.Utils.RealPathUtil;
import com.example.eventelevate.databinding.ActivityCameraBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {

    private ActivityCameraBinding binding;
    private  Uri selectedImageUri;
    private   AlertDialog dialog;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {
        Intent intent = getIntent();
        if (intent.getStringExtra("position").equals("1")) {
            dispatchTakePictureIntent();
        } else {
            showOptionDialog();
        }
        AppManager.changeStatusBarandBottomColor(CameraActivity.this);
        binding.retakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra("position").equals("1")) {
                    dispatchTakePictureIntent();
                } else {
                    showOptionDialog();
                }
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra("position").equals("1")) {
                    UploadDocuments("photo");
                }else {
                    UploadDocuments("photoid");
                }

            }
        });
    }

    private void UploadDocuments(String docs){
        AppManager.showProgress(CameraActivity.this);
        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), AppManager.user.getUserId().toString()); // Replace "12345" with the actual user ID
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), docs);
        File file = new File(RealPathUtil.getRealPath(CameraActivity.this,selectedImageUri)); // Replace with the actual file path
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        APIInterface apiInterface = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        Call<DocumentsUploadModel> call = apiInterface.UploadDocuments(userID,imagePart,title);

        call.enqueue(new Callback<DocumentsUploadModel>() {
            @Override
            public void onResponse(Call<DocumentsUploadModel> call, Response<DocumentsUploadModel> response) {
                if(response.body().getStatusCode()==200){
                    Toast.makeText(CameraActivity.this, "Documents Uploaded", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CameraActivity.this,DocumentsActivity.class));
                    finish();
                }
                Log.e("errroror",""+response.body().getStatusCode());
                Log.e("errroror",""+response.body().getMessage());
            }

            @Override
            public void onFailure(Call<DocumentsUploadModel> call, Throwable t) {
                Log.e("errroror",t.getMessage());
            }
        });
    }


    private void showOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.camera_custome_dialog_layout, null);

        ImageView cameraImage = dialogView.findViewById(R.id.cameraImage);
        TextView cameraText = dialogView.findViewById(R.id.cameraText);
        ImageView galleryImage = dialogView.findViewById(R.id.galleryImage);
        TextView galleryText = dialogView.findViewById(R.id.galleryText);
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        builder.setView(dialogView);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                selectedImageUri = data.getData();
                binding.preview.setImageURI(selectedImageUri);
                dialog.dismiss();
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (data != null && data.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    selectedImageUri = getImageUri(this, bitmap);
                    binding.preview.setImageURI(selectedImageUri);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
                }
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}