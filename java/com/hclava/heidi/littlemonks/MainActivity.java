package com.hclava.heidi.littlemonks;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hclava.heidi.littlemonks.Adapter.MyComicAdapter;
import com.hclava.heidi.littlemonks.Adapter.MySlideAdapter;
import com.hclava.heidi.littlemonks.Common1.Common1;
import com.hclava.heidi.littlemonks.Interface.IBannerLoadDone;
import com.hclava.heidi.littlemonks.Interface.IComicLoadDone;
import com.hclava.heidi.littlemonks.Model.comic;
import com.hclava.heidi.littlemonks.Service.PicassoLodingService;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;


public class MainActivity extends AppCompatActivity implements IBannerLoadDone, IComicLoadDone {
    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;
    //database
    DatabaseReference banners,comics;

   //listener
   IBannerLoadDone bannerListener;
   IComicLoadDone comicListener;
    android.app.AlertDialog alertDialog;
    RecyclerView recycler_comic;
    TextView txt_comic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init database
     banners= FirebaseDatabase.getInstance().getReference("Banners");
     comics= FirebaseDatabase.getInstance().getReference("Comic");
// init listener
        bannerListener=this;
        comicListener=this;

        slider=(Slider) findViewById(R.id.slider);
        slider.init(new PicassoLodingService()) ;
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
      swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
          @Override
          public void onRefresh() {
              loadBanner();
              loadComic();
          }
      });
      swipeRefreshLayout.post(new Runnable() {
          @Override
          public void run() {
              loadBanner();
              loadComic();
          }
      });
      recycler_comic=(RecyclerView) findViewById(R.id.recycler_comic);
      recycler_comic.setHasFixedSize(true);
      recycler_comic.setLayoutManager(new GridLayoutManager(this,2));

       txt_comic=(TextView)findViewById(R.id.txt_comic);

    }




    private void loadComic() {
        //alert dialogs
         alertDialog=new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setMessage("please wait..")
                .build();

          if(!swipeRefreshLayout.isRefreshing()){
            alertDialog.show();}

        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            List<comic> comic_load=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot comicnapshot:dataSnapshot.getChildren()) {
                 comic comicl=comicnapshot.getValue(comic.class);
                 comic_load.add(comicl);
                }
                  comicListener.onComicLoadDoneListener(comic_load);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadBanner(){
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList= new ArrayList<>();
                for(DataSnapshot bannersnapshot:dataSnapshot.getChildren())
                {
                    String image=bannersnapshot.getValue(String.class);
                    bannerList.add(image);

                }
                //call listener
                bannerListener.onBannerLoadDoneListener(bannerList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MainActivity.this, ""+databaseError.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onBannerLoadDoneListener(List<String>banners) {
        slider.setAdapter(new MySlideAdapter(banners));

    }

    @Override
    public void onComicLoadDoneListener(List<comic> comicList) {
        Common1.comicList=comicList;
        recycler_comic.setAdapter(new MyComicAdapter(getBaseContext(),comicList));
        txt_comic.setText(new StringBuilder("NEW COMIC (")
                .append(comicList.size())
                .append(")"));
        if(!swipeRefreshLayout.isRefreshing()){
            alertDialog.dismiss();
        }

    }
}
