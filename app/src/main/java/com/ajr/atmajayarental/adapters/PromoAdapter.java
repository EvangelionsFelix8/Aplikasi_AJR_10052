package com.ajr.atmajayarental.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.models.Promo;

import java.util.ArrayList;
import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.viewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    List<Promo> listPromo, filteredPromoList;

    public PromoAdapter(Context context, List<Promo> listPromo) {
        this.context = context;
        filteredPromoList = new ArrayList<>(listPromo);
        this.listPromo = listPromo;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.promo_item, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Promo promo = filteredPromoList.get(position);
        holder.textJenis.setText(promo.getJenis_promo()+ "");
        holder.textKode.setText("("+promo.getKode_promo()+ ")");
        holder.textBesarDiskon.setText(promo.getBesar_diskon_promo()+"%");
        holder.textKeterangan.setText(promo.getKeterangan());
        holder.btnShowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.hiddenlayout.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.hiddenlayout.setVisibility(View.GONE);
                    holder.btnShowDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
                else{
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.hiddenlayout.setVisibility(View.VISIBLE);
                    holder.btnShowDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredPromoList.size();
    }

    public void setPromoList(List<Promo> listPromo) {
        this.listPromo =  listPromo;
        filteredPromoList = new ArrayList<>(listPromo);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private final TextView textJenis, textKode, textBesarDiskon, textKeterangan;
        private LinearLayout hiddenlayout;
        private ImageButton btnShowDown;
        private CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.textJenis = itemView.findViewById(R.id.textJenis);
            this.textKode = itemView.findViewById(R.id.textKode);
            this.textBesarDiskon = itemView.findViewById(R.id.textBesarDiskon);
            this.textKeterangan = itemView.findViewById(R.id.textKeterangan);
            hiddenlayout = itemView.findViewById(R.id.expand_layout);
            btnShowDown = itemView.findViewById(R.id.btnDown);
            cardView = itemView.findViewById(R.id.carditem);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Promo> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(listPromo);
                } else {
                    for (Promo promo : listPromo) {
                        if (promo.getJenis_promo().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(promo);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredPromoList.clear();
                filteredPromoList.addAll((List<Promo>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
