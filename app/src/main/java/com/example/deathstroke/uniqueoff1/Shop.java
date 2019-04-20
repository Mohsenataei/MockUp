package com.example.deathstroke.uniqueoff1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Shop extends AppCompatActivity {

    RelativeLayout b1,b3,b2;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SliderAdapter sliderAdapter;
    boolean flag = false;
    boolean off_flag, map_flag, info_flag,notify_me_flag;
    private ImageView shop_location,shop_info,shop_offs;
    private TextView shop_location_text_view,shop_info_text_view,shop_offs_text_view;
    private Button notify_me_button;
    private int images[] = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        off_flag = map_flag = info_flag = notify_me_flag = false;
        shop_location = findViewById(R.id.shop_location_image_view);
        shop_location_text_view = findViewById(R.id.shop_loc_text);
        Typeface yekanFont = Typeface.createFromAsset(getAssets(), "fonts/B Yekan+.ttf");
        shop_location_text_view.setTypeface(yekanFont);
        shop_info_text_view = findViewById(R.id.shop_info_textView);
        shop_location_text_view.setTypeface(yekanFont);
        shop_info = findViewById(R.id.shop_info_image_view);
        shop_offs = findViewById(R.id.shop_offs_image_view);
        shop_offs_text_view = findViewById(R.id.shop_offs_textView);
        shop_offs_text_view.setTypeface(yekanFont);

        notify_me_button = findViewById(R.id.notify_me_button);
        notify_me_button.setTypeface(yekanFont);

        notify_me_button.setOnClickListener(v->{
            if (!flag) {
            doNotifyMeOnclick();
            flag = true;
            notify_me_flag = true;
            }else {
                undoNotifyMeOnClick();
                flag = false;
                notify_me_flag=false;
            }
        });


        tabLayout = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.shopViewPager);
        setViewPager();
        b1= findViewById(R.id.shop_info_button);
        b2= findViewById(R.id.shop_offs_button);
        b3= findViewById(R.id.shop_map_button);

        b1.setOnClickListener(view->{
            if (!info_flag) {
                doInfoOnClick();
                undoMapOnclick();
                undoOffsOOnclick();
                info_flag = true;
                shop_info_fragment info_fragment = new shop_info_fragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder, info_fragment);
                ft.commit();
            }
            else {
                undoInfoOnClick();
                info_flag = false;
            }
        });
        b2.setOnClickListener(view ->{
            if (!off_flag) {
                doOffsOnClick();
                undoMapOnclick();
                undoInfoOnClick();
                off_flag = true;
                shop_attr_fragment attr_fragment = new shop_attr_fragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder,attr_fragment);
                ft.commit();
            }else {
                undoOffsOOnclick();
                off_flag = false;
            }
        });

        b3.setOnClickListener(view->{
            if (!map_flag) {
                doMapOnClick();
                undoInfoOnClick();
                undoOffsOOnclick();
                map_flag = true;
                shop_map_fragment map_fragment = new shop_map_fragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder, map_fragment);
                ft.commit();
            } else {
                undoMapOnclick();
                map_flag = false;
            }

        });
    }

    private void doNotifyMeOnclick() {
        notify_me_button.setBackground(getDrawable(R.drawable.notify_me_button_on_click));
        notify_me_button.setTextColor(Color.WHITE);
    }
    private void undoNotifyMeOnClick() {
        notify_me_button.setBackground(getDrawable(R.drawable.notify_me_border));
        notify_me_button.setTextColor(getResources().getColor(R.color.grin_beam_color));
    }

    private void setViewPager () {
        sliderAdapter = new SliderAdapter(this, images);
        viewPager.setAdapter(sliderAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    private void undoMapOnclick(){

        b3.setBackground(getDrawable(R.drawable.shop_botton_style));
        shop_location.setBackgroundResource(R.drawable.ic_shop_map_marker_alt);
        shop_location_text_view.setTextColor(getResources().getColor(R.color.shop_icon_color));
    }

    private void doMapOnClick(){
        shop_location_text_view.setTextColor(Color.WHITE);
        shop_location.setBackgroundResource(R.drawable.ic_map_marker_alt_onclick);
        //b3.setBackgroundColor(getResources().getColor(R.color.shop_icon_color));
        b3.setBackground(getResources().getDrawable(R.drawable.shop_button_onclick_background));
    }

    private void doInfoOnClick() {
        shop_info_text_view.setTextColor(Color.WHITE);
        shop_info.setBackgroundResource(R.drawable.ic_info_circle_onclick);
        b1.setBackground(getResources().getDrawable(R.drawable.shop_button_onclick_background));
    }
    private void undoInfoOnClick(){
        shop_info_text_view.setTextColor(getResources().getColor(R.color.shop_icon_color));
        shop_info.setBackgroundResource(R.drawable.ic_info_circle);
        b1.setBackground(getDrawable(R.drawable.shop_botton_style));

    }
    private void doOffsOnClick() {
        shop_offs_text_view.setTextColor(Color.WHITE);
        shop_offs.setBackgroundResource(R.drawable.ic_percentage_onclick);
        b2.setBackground(getDrawable(R.drawable.shop_button_onclick_background));

    }
    private void undoOffsOOnclick() {
        shop_offs_text_view.setTextColor(getResources().getColor(R.color.shop_icon_color));
        shop_offs.setBackgroundResource(R.drawable.ic_percentage);
        b2.setBackground(getDrawable(R.drawable.shop_botton_style));
    }

}
