package org.techtown.image;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class imageboard_Adapter extends RecyclerView.Adapter<imageboard_Adapter.ItemViewHolder> {
    public ArrayList<Uri> albumImgList;
    public Context mContext;

    //생성자 정의
    public imageboard_Adapter(ArrayList<Uri> albumImgList, Context mContext){
        this.albumImgList = albumImgList;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_image,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //앨범에서 가져온 이미지 표시
        holder.imageView.setImageURI(albumImgList.get(position));
    }



    @Override
    public int getItemCount() {
        return albumImgList.size();
    }





    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
