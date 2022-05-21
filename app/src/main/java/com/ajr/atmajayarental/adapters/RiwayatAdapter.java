package com.ajr.atmajayarental.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.models.Mobil;
import com.ajr.atmajayarental.models.Promo;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.viewHolder> {

    private Context context;
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
        return new RiwayatAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RiwayatTrans riwayatTrans = filteredListRiwayat.get(position);
        holder.textIdTransaksi.setText(riwayatTrans.getId_transaksi());
        holder.textTanggalTransaksi.setText(riwayatTrans.getTanggal_transaksi());
        holder.textTanggalMulai.setText(riwayatTrans.getTanggal_mulai());
        holder.textTanggalSelesai.setText(riwayatTrans.getTanggal_selesai());
        holder.textStatusTrans.setText(riwayatTrans.getStatus_transaksi());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        holder.textTotalHarga.setText(kursIndonesia.format(riwayatTrans.getTotal_harga()));

        holder.cardRiwayatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "INI NANTI KE DIALOG", Toast.LENGTH_SHORT).show();
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

    public class viewHolder extends RecyclerView.ViewHolder {
        private final TextView textIdTransaksi, textTanggalTransaksi, textTanggalMulai, textTanggalSelesai, textTotalHarga, textStatusTrans;
        private MaterialCardView cardRiwayatView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textIdTransaksi = itemView.findViewById(R.id.textJenis);
            textTanggalTransaksi = itemView.findViewById(R.id.textKode);
            textTanggalMulai = itemView.findViewById(R.id.textBesarDiskon);
            textTanggalSelesai = itemView.findViewById(R.id.textKeterangan);
            textTotalHarga = itemView.findViewById(R.id.textTotalHarga);
            textStatusTrans = itemView.findViewById(R.id.textStatusTrans);
            cardRiwayatView = itemView.findViewById(R.id.cardRiwayatView);
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
