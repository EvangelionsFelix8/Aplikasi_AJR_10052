package com.ajr.atmajayarental.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ajr.atmajayarental.R;

public class ContactUsFragment extends Fragment {

    private ImageButton btnDownMisi, btnDownVisi;
    private LinearLayout hiddenVisi, hiddenMisi;
    private CardView cardMisi, cardVisi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us, container,false);
        btnDownMisi = view.findViewById(R.id.btnDownMisi);
        btnDownVisi = view.findViewById(R.id.btnDownVisi);
        hiddenVisi = view.findViewById(R.id.hiddenVisi);
        hiddenMisi = view.findViewById(R.id.hiddenMisi);
        cardVisi = view.findViewById(R.id.cardVisi);
        cardMisi = view.findViewById(R.id.cardMisi);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDownVisi.setOnClickListener(view12 -> {
            if(hiddenVisi.getVisibility() == View.VISIBLE){
                changeVisibility(View.GONE, R.drawable.ic_baseline_keyboard_arrow_down_24, true);
            }
            else{
                changeVisibility(View.VISIBLE, R.drawable.ic_baseline_keyboard_arrow_up_24, true);
            }
        });

        btnDownMisi.setOnClickListener(view1 -> {
            if(hiddenMisi.getVisibility() == View.VISIBLE){
                changeVisibility(View.GONE, R.drawable.ic_baseline_keyboard_arrow_down_24, false);
            }
            else{
                changeVisibility(View.VISIBLE, R.drawable.ic_baseline_keyboard_arrow_up_24, false);
            }
        });
    }

    private void changeVisibility(int visibility, int icon, boolean isVisi) {
        if(isVisi){
            TransitionManager.beginDelayedTransition(cardVisi, new AutoTransition());
            hiddenVisi.setVisibility(visibility);
            btnDownVisi.setImageResource(icon);
        }
        else{
            TransitionManager.beginDelayedTransition(cardMisi, new AutoTransition());
            hiddenMisi.setVisibility(visibility);
            btnDownMisi.setImageResource(icon);
        }
    }
}