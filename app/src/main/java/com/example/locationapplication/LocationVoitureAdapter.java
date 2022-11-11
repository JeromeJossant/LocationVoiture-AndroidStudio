package com.example.locationapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationapplication.models.LocationVoiture;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class LocationVoitureAdapter extends FirestoreRecyclerAdapter<LocationVoiture, LocationVoitureAdapter.LocationVoitureViewHolder>  {

    Context context;

    public LocationVoitureAdapter(@NonNull FirestoreRecyclerOptions<LocationVoiture> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LocationVoitureViewHolder holder, int position, @NonNull LocationVoiture locationVoiture) {

        holder.marqueTextView.setText(locationVoiture.marque);
        holder.modelTextView.setText(locationVoiture.modele);
        holder.prixHoraireTextView.setText( " Prix Horaire : "+ locationVoiture.prixHoraire + "euros");
        holder.timestampTextView.setText(Utility.timestampToString(locationVoiture.getTimestamp()));
    }

    @NonNull
    @Override
    public LocationVoitureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_locationvoiture_item, parent, false);

       return new LocationVoitureViewHolder(view);
    }

    class LocationVoitureViewHolder extends RecyclerView.ViewHolder {

        TextView marqueTextView, modelTextView, prixHoraireTextView, timestampTextView;

        public LocationVoitureViewHolder(@NonNull View itemView) {
            super(itemView);
            marqueTextView = itemView.findViewById(R.id.marque_text_view);
            modelTextView = itemView.findViewById(R.id.model_text_view);
            prixHoraireTextView = itemView.findViewById(R.id.prixHoraire_text_view);
            timestampTextView = itemView.findViewById(R.id.locationVoiture_timestamp_text_view);

        }
    }

}
