package com.example.indah2018rpl2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.UserViewHolder> {

    private ArrayList<Model> dataList;
    View view;

    public Adapter(ArrayList<Model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.txtusername.setText(dataList.get(position).getUsername());
        holder.txtnamalengkap.setText(dataList.get(position).getNamalengkap());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtusername, txtnamalengkap;
        CardView cardview;

        UserViewHolder(View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardku);
            txtusername = itemView.findViewById(R.id.txtusername);
            txtnamalengkap = itemView.findViewById(R.id.txtnamalengkap);
        }
    }

}