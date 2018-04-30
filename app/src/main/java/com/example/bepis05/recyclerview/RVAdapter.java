package com.example.bepis05.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    List<Person> persons;
    Context context;
    RVAdapter(List<Person> persons, Context c){
        this.persons = persons;
        this.context = c;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }



    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
      //  View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {

        holder.personName.setText(persons.get(position).name);
        holder.personAge.setText(persons.get(position).age);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), persons.get(position).photoBmp);
        roundedBitmapDrawable.setCornerRadius(persons.get(position).photoBmp.getHeight());

     //   holder.personPhoto.setImageBitmap(persons.get(position).photoBmp);
        holder.personPhoto.setImageDrawable(roundedBitmapDrawable);




     /* Drawable originalDrawable = context.getResources().getDrawable(persons.get(position).photoId,null);
        Bitmap originalBitmap = ((BitmapDrawable)originalDrawable).getBitmap();

        //drawable redondeado
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),
                originalBitmap);

        //asignamos el cornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        holder.personPhoto.setImageDrawable(roundedDrawable);*/

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv){
        super.onAttachedToRecyclerView(rv);
    }

}

