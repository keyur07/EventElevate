package com.example.eventelevate.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventelevate.Activity.CreatePost;
import com.example.eventelevate.R;
import com.example.eventelevate.databinding.FragmentManageBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ManageFragment extends Fragment {

    private FragmentManageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManageBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBottomDialog();
            }
        });
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
                Intent intent = new Intent(getActivity(), CreatePost.class);
                intent.putExtra("types",0);
                startActivity(intent);
            }
        });

        bottom.findViewById(R.id.btn_packages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
                Intent intent = new Intent(getActivity(), CreatePost.class);
                intent.putExtra("types",1);
                startActivity(intent);
            }
        });
    }
}