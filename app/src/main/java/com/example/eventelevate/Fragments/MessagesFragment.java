package com.example.eventelevate.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eventelevate.Adapter.FirebaseMessageListAdapter;
import com.example.eventelevate.Manager.AppManager;
import com.example.eventelevate.Model.MessageModel;
import com.example.eventelevate.databinding.FragmentMessagesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {

    int userId, clientId;
    private ArrayList<MessageModel> clientList;
    private FragmentMessagesBinding binding;
    FirebaseMessageListAdapter messageListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        init();
        binding.referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });

        return binding.getRoot();
    }

    private void init() {
        getDataFromIntent();
    userId = AppManager.user.getUserid();
        clientList = new ArrayList<>();
        messageListAdapter = new FirebaseMessageListAdapter(clientList, getActivity(), userId, clientId, binding);
        binding.rvMsgList.setAdapter(messageListAdapter);
        binding.rvMsgList.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("message");

        databaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot mainKeyChild : dataSnapshot.getChildren()) {
                        String mainKey = mainKeyChild.getKey();
                        Log.d("TAG", "MainKey: " + mainKey);
                        String[] parts = mainKey.split("\\|");
                        Log.d("TAG", "length: " + parts.length);
                        if (parts.length==2) {
                            int part0 = Integer.parseInt(parts[0]);
                            int part1 = Integer.parseInt(parts[1]);
                            if (part0 == userId) {
                                Log.d("TAG", "length: " + parts.length);
                                clientList.add(new MessageModel(parts[0], parts[1], "User"));
                            }
                            if (part1 == userId) {
                                clientList.add(new MessageModel(parts[0], parts[1], "Client"));
                            }
                        } else {
                            Log.e("TAG", "Invalid data format for MainKey: " + mainKey);
                        }
                    }
                    messageListAdapter.notifyDataSetChanged();
                    Log.d("TAG", "Total clientList size: " + clientList.size());
                } else {
                    Log.d("TAG", "No data exists at the 'message' node.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "Firebase Database Error: " + databaseError.getMessage());
                // Handle errors
            }
        });
    }

    private void getDataFromIntent() {
        userId = AppManager.user.getUserId();
        clientId = 0; // You can set the clientId as needed
    }
}
