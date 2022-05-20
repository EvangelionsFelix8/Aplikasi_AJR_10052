package com.ajr.atmajayarental.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.api.MobilApi;
import com.ajr.atmajayarental.models.Mobil;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class MobilAdapter extends RecyclerView.Adapter<MobilAdapter.viewHolder>{
    private Context context;
    private LayoutInflater layoutInflater;
    List<Mobil> listMobil, filteredListMobil;

    public MobilAdapter(Context context, List<Mobil> listMobil) {
        this.context = context;
        filteredListMobil = new ArrayList<>(listMobil);
        this.listMobil = listMobil;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mobil_item, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Mobil mobil = filteredListMobil.get(position);
        Glide.with(context)
                .load(MobilApi.BASE_URL_FOTO + "storage/"+ mobil.getUrl_foto_mobil())
                .placeholder(R.drawable.no_image)
                .into(holder.fotoMobil);

        holder.textNamaMobil.setText(" " + mobil.getNama_mobil() + " ");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        holder.textHargaSewa.setText(kursIndonesia.format(mobil.getHarga_sewa_mobil()) +"/hari");
        holder.textJenisTransmisi.setText(mobil.getJenis_transmisi());
        holder.textBahanBakar.setText(mobil.getJenis_bahan_bakar());
        holder.textTipeMobil.setText(" " + mobil.getTipe_mobil() + " ");
        holder.textKapasitas.setText(mobil.getKapasitas_penumpang()+ " orang");
        holder.textFasilitas.setText("Fasilitas : " + mobil.getFasilitas());
    }

    public void setMobilList(List<Mobil> listMobil) {
        this.listMobil =  listMobil;
        filteredListMobil = new ArrayList<>(listMobil);
    }

    @Override
    public int getItemCount() {
        return filteredListMobil.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private final TextView textNamaMobil, textHargaSewa, textKapasitas, textFasilitas, textTipeMobil, textBahanBakar;
        private final TextView textJenisTransmisi;
        private final ImageView fotoMobil;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.textNamaMobil = itemView.findViewById(R.id.textNamaMobil);
            this.textHargaSewa = itemView.findViewById(R.id.textHargaSewa);
            this.textKapasitas = itemView.findViewById(R.id.textKapasitas);
            this.textFasilitas = itemView.findViewById(R.id.textFasilitas);
            this.fotoMobil = itemView.findViewById(R.id.fotoMobil);
            textTipeMobil = itemView.findViewById(R.id.textTipeMobil);
            textBahanBakar = itemView.findViewById(R.id.textBahanBakar);
            textJenisTransmisi = itemView.findViewById(R.id.textJenisTransmisi);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Mobil> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(listMobil);
                } else {
                    for (Mobil mobil : listMobil) {
                        if (mobil.getNama_mobil().toLowerCase().contains(charSequenceString.toLowerCase()))
                            filtered.add(mobil);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredListMobil.clear();
                filteredListMobil.addAll((List<Mobil>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
