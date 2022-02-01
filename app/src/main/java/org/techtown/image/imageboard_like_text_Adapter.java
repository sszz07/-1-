package org.techtown.image;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class imageboard_like_text_Adapter extends RecyclerView.Adapter<imageboard_like_text_Adapter.likeViewHolder> {
    private Context context;
    private List<imageboard_text_data> lists;
    private ItemClickListener itemClickListener;


    public imageboard_like_text_Adapter(Context context, List<imageboard_text_data> lists, ItemClickListener itemClickListener) {
        this.context = context;
        this.lists = lists;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public likeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_imageboard_like_item, parent, false);
        return new likeViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull imageboard_like_text_Adapter.likeViewHolder holder, int position) {
        imageboard_text_data imageboardtextdata = lists.get(position);
        holder.like_name.setText(imageboardtextdata.getLike_name());
        holder.like_hobby.setText(imageboardtextdata.getLike_hobby());


        String nickname = imageboardtextdata.getLike_nick();


        holder.like_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("삭제");
                builder.setMessage("해당 항목을 삭제하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deletelike();
                                lists.remove(position);
                                notifyItemRemoved(position);


                            }
                            private void deletelike() {
                                imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
                                Call<imageboard_text_data> call = imageboard_text_ApiInterface.deletelike(nickname);
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
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();

            }
        });




    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class likeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView like_name, like_hobby;
        ItemClickListener itemClickListener;
        Button like_delete;


        public likeViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.like_linear_layout);
            like_name = view.findViewById(R.id.like_name_text);
            like_hobby = view.findViewById(R.id.like_hobby_text);
            like_delete = view.findViewById(R.id.like_delete);

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