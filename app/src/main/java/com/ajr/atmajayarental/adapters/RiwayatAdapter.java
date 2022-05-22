package com.ajr.atmajayarental.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.models.Mobil;
import com.ajr.atmajayarental.models.Promo;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.ajr.atmajayarental.screen.DetailRiwayatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

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
        return new viewHolder(view);
    }

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
        holder.textTotalHarga.setText(kursIndonesia.format(riwayatTrans.getTotal_harga()));

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
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                View newLayout = LayoutInflater.from(builder.getContext()).inflate(R.layout.fragment_detail_riwayat, null);
//
////                Deklarasi Atribut dari Fragment
//                TextView textIdTransaksi, textTanggalTransaksi, textTanggalMulai, textTanggalSelesai, textStatusTrans, textDurasi, textTanggalKembali;
//                TextView textCs, textKodePromo, textBesarPromo, textMetodeBayar, textTotalMobil, textTotalDriver, textTotalHarga, textTotalDenda, textPotonganPromo;
//                MaterialButton btnBack;
//
////                Mendapatkan Id pada activity
//                textIdTransaksi = newLayout.findViewById(R.id.tvNamaTiket);
//                textTanggalTransaksi = newLayout.findViewById(R.id.tvNamaPemilikTiket);
//                textTanggalMulai = newLayout.findViewById(R.id.tvKodeBookingTiket);
//                textTanggalSelesai = newLayout.findViewById(R.id.tvSectionTiket);
//                textStatusTrans = newLayout.findViewById(R.id.tvTanggalTiket);
//                textDurasi = newLayout.findViewById(R.id.tvVenueTiket);
//                textTanggalKembali = newLayout.findViewById(R.id.tvSeatNumberTiket);
//                btnBack = newLayout.findViewById(R.id.btnBack);
//
////                Mengeset Tampilan TextView
//                tvNamaEvent.setText(riwayatTrans.getNamaEvent());
//                tvPemilikTiket.setText(riwayatTrans.getNamaPemesan());
//                tvKodeBooking.setText(String.valueOf(riwayatTrans.getKodeTiket()));
//                tvSection.setText(riwayatTrans.getSection());
//                tvTanggalWaktu.setText(riwayatTrans.getTanggalEvent() + "\n" +ticketEvent.getWaktuEvent());
//                tvVenue.setText(riwayatTrans.getVenueEvent());
//                tvSeat.setText(riwayatTrans.getSeatNumber());
//
////                menampilkan View builder dengan layout diolog
//                builder.setView(newLayout);
//
////                Show Dialog
//                AlertDialog popup = builder.create();
//                popup.show();
//
//                btnBack.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        popup.dismiss(); }
//                });
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
        private CardView cardStatus;
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
