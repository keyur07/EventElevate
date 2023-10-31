    package com.example.eventelevate.Fragments;

    import android.content.Intent;
    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import com.example.eventelevate.Adapter.FirebaseMessageListAdapter;
    import com.example.eventelevate.Manager.AppManager;
    import com.example.eventelevate.Model.MessageModel;
    import com.example.eventelevate.R;
    import com.example.eventelevate.databinding.FragmentManageBinding;
    import com.example.eventelevate.databinding.FragmentMessagesBinding;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;

    public class MessagesFragment extends Fragment {

        int userId,clientId;
        String msg;
        private ArrayList<MessageModel> clientList;
        private FragmentMessagesBinding binding;
        FirebaseMessageListAdapter messageListAdapter;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            binding = FragmentMessagesBinding.inflate(getLayoutInflater());
            // Inflate the layout for this fragment
            getDataFromIntent();

            clientList = new ArrayList<>();
            messageListAdapter = new FirebaseMessageListAdapter(clientList,getActivity(),userId,clientId);
            binding.rvMsgList.setAdapter(messageListAdapter);
            binding.rvMsgList.setLayoutManager(new LinearLayoutManager(getActivity()));


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("message");

            databaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot mainKeyChild : dataSnapshot.getChildren()) {
                            String mainKey = mainKeyChild.getKey();
                            Log.d("TAG", "onDataChange: ............................1............."+mainKey);
                            System.out.println("MainKey: " + mainKey);
                            String[] parts = mainKey.split("\\|");
                            if (Integer.parseInt(parts[0])==userId){
                                clientList.add(new MessageModel(parts[0],parts[1],"User"));
                            }
                            if (Integer.parseInt(parts[1])==userId){
                                clientList.add(new MessageModel(parts[0],parts[1],"Client"));
                            }

    /*
                            // Iterate through the sub-keys (key1, key2) of each MainKey
                            for (DataSnapshot subKeyChild : mainKeyChild.getChildren()) {
                                String subKey = subKeyChild.getKey();
                                System.out.println("SubKey: " + subKey);
                                //Log.d("TAG", "onDataChange: ............................2............."+subKey);
                            }*/
                        }
                        Log.d("TAG", "onDataChange: ............................2............."+clientList.size());
                        messageListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });

            return binding.getRoot();

        }


        private void getDataFromIntent() {
            msg = "Hello";
            userId = AppManager.user.getId();
            clientId = 0;
        }
    }