package com.kit.megaphone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kit.megaphone.R;
import com.kit.megaphone.databinding.ViewMainBinding;
import com.kit.megaphone.datas.ArticleData;
import com.kit.megaphone.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<ArticleData> data = new ArrayList<>();
    private DatabaseReference database;

    public MainAdapter() {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("article").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    data.add(snapshot.getValue(ArticleData.class));
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_main, viewGroup, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        ArticleData articleData = data.get(i);
        if (articleData != null) {
            mainViewHolder.binding.name.setText(articleData.getName());
            mainViewHolder.binding.title.setText(articleData.getTitle());
            mainViewHolder.binding.content.setText(articleData.getContent());
            mainViewHolder.binding.date.setText(articleData.getDate() + " ~ " + Util.getDate7Day());
            mainViewHolder.binding.topic.setText(articleData.getTopic());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        private ViewMainBinding binding;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
