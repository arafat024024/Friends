package com.arafat.friends.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arafat.friends.R;
import com.arafat.friends.mode.Profile;
import com.arafat.friends.utils.ImageDownloader;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Viewholder> {

    private Context context;
    private ArrayList<Profile> profileArrayList;

    public ListAdapter(Context context, ArrayList<Profile> profileArrayList) {
        this.context = context;
        this.profileArrayList = profileArrayList;
    }

    @NonNull
    @Override
    public ListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Profile model = profileArrayList.get(position);
        holder.fullNameTV.setText(model.getFullName());
        holder.countryTV.setText("" + model.getCountry());
        new ImageDownloader(holder.portraitIV).execute(model.getPortrait());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.detail_layout, null);
                dialogBuilder.setView(dialogView);

                ((TextView) dialogView.findViewById(R.id.textViewFullName)).setText("Full Name: "+model.getFullName());
                ((TextView) dialogView.findViewById(R.id.textViewAddress)).setText("Address: "+model.getAddress());
                ((TextView) dialogView.findViewById(R.id.textViewCSC)).setText("City: "+model.getCity()+", "+
                        "State: "+model.getState()+", "+ "Country: "+model.getCountry());
                ((TextView) dialogView.findViewById(R.id.textViewEmail)).setText("Email: "+model.getEmail());
                ((TextView) dialogView.findViewById(R.id.textViewEmail))
                        .setPaintFlags(((TextView) dialogView.findViewById(R.id.textViewEmail))
                                .getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                ((TextView) dialogView.findViewById(R.id.textViewEmail)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+model.getEmail()));
                        ((Activity)context).startActivity(intent);
                    }
                });
                ((TextView) dialogView.findViewById(R.id.textViewCellphone)).setText("Cellphone: "+model.getCellPhone());
                new ImageDownloader((ImageView) dialogView.findViewById(R.id.imageViewDetail)).execute(model.getFullPortrait());
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return profileArrayList.size();
    }

    // View holder class for initializing of
    // the views
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView portraitIV;
        private TextView fullNameTV, countryTV;
        private LinearLayout linearLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            portraitIV = itemView.findViewById(R.id.imageViewPortrait);
            fullNameTV = itemView.findViewById(R.id.textViewFullName);
            countryTV = itemView.findViewById(R.id.textViewCountry);
            linearLayout = itemView.findViewById(R.id.singleItemLayout);

        }
    }
}
