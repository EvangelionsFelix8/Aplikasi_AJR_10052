package com.ajr.atmajayarental.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.ajr.atmajayarental.screen.DetailRiwayatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.viewHolder> {

    private final Context context;
    private LayoutInflater layoutInflater;
    List<RiwayatTrans> riwayatTransList, filteredListRiwayat;

    public RiwayatAdapter(Context context, List<RiwayatTrans> riwayatTransList) {
        this.context = context;
        filteredListRiwayat = new ArrayList<>(riwayatTransList);
        this.riwayatTransList = riwayatTransList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.riwayat_item, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RiwayatTrans riwayatTrans = filteredListRiwayat.get(position);
        holder.textIdTransaksi.setText(riwayatTrans.getId_transaksi());
        holder.textTanggalTransaksi.setText(riwayatTrans.getTanggal_transaksi());
        holder.textTanggalMulai.setText(riwayatTrans.getTanggal_mulai() + " ");
        holder.textTanggalSelesai.setText(" " + riwayatTrans.getTanggal_selesai());
        holder.textStatusTrans.setText(riwayatTrans.getStatus_transaksi());

        if(riwayatTrans.getStatus_transaksi().equalsIgnoreCase("Sudah lunas (Selesai)")){
            holder.cardStatus.setCardBackgroundColor(Color.parseColor("#FFC8E6C9"));
            holder.textStatusTrans.setTextColor(Color.parseColor("#1B5E20"));
        }
        else if(riwayatTrans.getStatus_transaksi().equalsIgnoreCase("Batal")){
            holder.cardStatus.setCardBackgroundColor(Color.parseColor("#FFB71C1C"));
            holder.textStatusTrans.setTextColor(Color.parseColor("#FFFFCDD2"));
        }

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        holder.textTotalHarga.setText(riwayatTrans.getTotalKeseluruhan());

        holder.cardRiwayatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DetailRiwayat = new Intent(context, DetailRiwayatActivity.class);
//                Mengubah Objek pegawai menjadi format JSON string dengan GSON
                Gson gson = new Gson();
                String strRiwayatTrans = gson.toJson(riwayatTrans);

//                Menyisipkan data json string ke intent
                DetailRiwayat.putExtra("detailRiwayat", strRiwayatTrans);

                context.startActivity(DetailRiwayat);
            }
        });
    }

    public void setRiwayatTransListList(List<RiwayatTrans> riwayatTransList) {
        this.riwayatTransList =  riwayatTransList;
        filteredListRiwayat = new ArrayList<>(riwayatTransList);
    }

    @Override
    public int getItemCount() {
        return filteredListRiwayat.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView textIdTransaksi, textTanggalTransaksi, textTanggalMulai, textTanggalSelesai, textTotalHarga, textStatusTrans;
        private final MaterialCardView cardRiwayatView;
        private final CardView cardStatus;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textIdTransaksi = itemView.findViewById(R.id.textIdTransaksi);
            textTanggalTransaksi = itemView.findViewById(R.id.textTanggalTrans);
            textTanggalMulai = itemView.findViewById(R.id.textTanggalMulai);
            textTanggalSelesai = itemView.findViewById(R.id.textTanggalSelesai);
            textTotalHarga = itemView.findViewById(R.id.textTotalHarga);
            textStatusTrans = itemView.findViewById(R.id.textStatusTrans);
            cardRiwayatView = itemView.findViewById(R.id.cardRiwayatView);
            cardStatus = itemView.findViewById(R.id.cardStatus);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<RiwayatTrans> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(riwayatTransList);
                } else {
                    for (RiwayatTrans riwayatTrans : riwayatTransList) {
                        if (riwayatTrans.getId_transaksi().toLowerCase().contains(charSequenceString.toLowerCase()))
                            filtered.add(riwayatTrans);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredListRiwayat.clear();
                filteredListRiwayat.addAll((List<RiwayatTrans>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
