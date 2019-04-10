package com.hclava.heidi.littlemonks.Adapter;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MySlideAdapter extends SliderAdapter {
    private List<String> imagelist;

    public MySlideAdapter(List<String> imagelist) {
        this.imagelist=imagelist;
    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(imagelist.get(position));
    }
}
