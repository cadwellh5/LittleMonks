package com.hclava.heidi.littlemonks.Interface;

import com.hclava.heidi.littlemonks.Model.comic;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<comic> comicList);
}
