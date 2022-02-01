package org.techtown.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class boardPersonAdapter extends RecyclerView.Adapter<boardPersonAdapter.PersonViewHolder> {
    private Context context;
    private List<boardPerson> list;
    private ItemClickListener itemClickListener;


    public boardPersonAdapter(Context context, List<boardPerson> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }




    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
        return new PersonViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
       boardPerson person = list.get(position);

        //클라이드

        holder.name_text.setText(person.getName());
        holder.hobby_text.setText(person.getHobby());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView name_text, hobby_text;
        ItemClickListener itemClickListener;
        public ImageView imageView;

        public PersonViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            name_text = view.findViewById(R.id.name_text);
            hobby_text = view.findViewById(R.id.hobby_text);


            this.itemClickListener = itemClickListener;
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
