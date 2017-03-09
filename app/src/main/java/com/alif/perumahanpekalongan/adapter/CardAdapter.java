package com.alif.perumahanpekalongan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alif.perumahanpekalongan.MyData;
import com.alif.perumahanpekalongan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alif on 17/11/2016.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private List<MyData> myDatas;
    private ClickListener clickListener;

    public CardAdapter(Context context, List<MyData> myDatas) {
        this.context = context;
        this.myDatas = myDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_view,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtPerum.setText(myDatas.get(position).getNmperum());
        holder.txtKel.setText(myDatas.get(position).getKelurahan());
        holder.txtKec.setText(myDatas.get(position).getKecamatan());
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return myDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtPerum, txtKel, txtKec;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPerum    = (TextView) itemView.findViewById(R.id.txt_perum);
            txtKel      = (TextView) itemView.findViewById(R.id.txt_kelurahan);
            txtKec      = (TextView) itemView.findViewById(R.id.txt_kecamatan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener!=null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public interface ClickListener {
        void itemClicked(View v, int position);
    }

    public void setFilter(ArrayList<MyData> newList) {
        myDatas = new ArrayList<>();
        myDatas.addAll(newList);
        notifyDataSetChanged();
    }

    public MyData getItem(int position) {
        return myDatas.get(position);
    }
}
