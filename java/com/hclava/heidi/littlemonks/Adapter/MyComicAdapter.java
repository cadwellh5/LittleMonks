package com.hclava.heidi.littlemonks.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import  android .view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hclava.heidi.littlemonks.Model.comic;
import com.hclava.heidi.littlemonks.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicAdapter extends RecyclerView.Adapter<MyComicAdapter.MyViewHolder> {


    Context context;
    List<comic> comicList;
    LayoutInflater inflater;

    public MyComicAdapter(Context context,List<comic> comicList)
    {
        this.context=context;
        this.comicList=comicList;
        inflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView= inflater.inflate(R.layout.comic_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Picasso.get().load(comicList.get(position).Image).into(myViewHolder.comic_image);
        myViewHolder.comic_name.setText(comicList.get(position).Name);

    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder

    {
        TextView comic_name;
        ImageView comic_image;
        public MyViewHolder(@NonNull View itemView){
        super(itemView);
        comic_image=(ImageView) itemView.findViewById(R.id.image_comic);
        comic_name=(TextView) itemView.findViewById(R.id.comic_name);
}
    }
}
