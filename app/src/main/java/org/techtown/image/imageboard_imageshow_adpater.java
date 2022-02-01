package org.techtown.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class imageboard_imageshow_adpater extends RecyclerView.Adapter<imageboard_imageshow_adpater.showimageViewHolder> {
    private Context context;
    private List<image_data> lists;


    public imageboard_imageshow_adpater(Context context, List<image_data> lists) {
        this.context = context;
        this.lists = lists;

    }

    @NonNull
    @Override
    public showimageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.showimage, parent, false);
        return new showimageViewHolder(view);
    }

    @Override
    //홀더에 맞게 데이터를 세팅을 해주는 구간
    public void onBindViewHolder(@NonNull showimageViewHolder holder, int position) {
        image_data imagedata = lists.get(position);


//        holder.showimage.setImageURI(fileModel.getMessage());

        String name = imagedata.getMessage();
        Glide.with(context)
                .asBitmap()
                .load("http://52.79.180.89/image/" + name)
                .into(holder.showimage);

    }

    @Override
    //어댑터에서 관리하는 아이템의 갯수를 반환을 한다 반환을 해야 원래 사용하는 리사이클러뷰 박스에 다른 데이터를 담을수가 있다

    public int getItemCount() {
        return lists.size();
    }


    public class showimageViewHolder extends RecyclerView.ViewHolder {
        public ImageView showimage,mainimage;

        public showimageViewHolder(@NonNull View view) {
            super(view);

            showimage = view.findViewById(R.id.showimage);


        }
    }

}