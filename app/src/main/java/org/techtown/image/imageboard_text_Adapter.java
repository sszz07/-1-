package org.techtown.image;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class imageboard_text_Adapter extends RecyclerView.Adapter<imageboard_text_Adapter.PersonViewHolder> {
    private Context context;
    private List<imageboard_text_data> lists;
    private ItemClickListener itemClickListener;
    List<image_data> imagelist;

    public imageboard_text_Adapter(Context context, List<imageboard_text_data> lists, ItemClickListener itemClickListener) {
        this.context = context;
        this.lists = lists;
        this.itemClickListener = itemClickListener;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_imageboard_text_item, parent, false);
        return new PersonViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        imageboard_text_data imageboardtextdata = lists.get(position);
        holder.nick_text.setText(imageboardtextdata.getNick());
        holder.name_text.setText(imageboardtextdata.getName());
        holder.hobby_text.setText(imageboardtextdata.getHobby());


        String like_name = imageboardtextdata.getName();
        String like_hobby = imageboardtextdata.getHobby();
        String like_nick = imageboardtextdata.getNick();

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,imageboardtextdata.getNick().toString(),Toast.LENGTH_SHORT).show();
                insert_like();
            }

            private void insert_like() {
                imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
                Call<imageboard_text_data> call = imageboard_text_ApiInterface.insert_like(like_name, like_hobby,like_nick);
                call.enqueue(new Callback<imageboard_text_data>() {
                    @Override
                    public void onResponse(@NonNull Call<imageboard_text_data> call, @NonNull Response<imageboard_text_data> response) {
                        if (response.isSuccessful() && response.body() != null) {

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<imageboard_text_data> call, @NonNull Throwable t) {
                        Log.e("insertPerson()", "에러 : " + t.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView name_text, hobby_text, nick_text;
        ItemClickListener itemClickListener;
        Button like;


        public PersonViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            name_text = view.findViewById(R.id.name_text);
            hobby_text = view.findViewById(R.id.hobby_text);
            nick_text = view.findViewById(R.id.nick_text);
            like = view.findViewById(R.id.like);


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