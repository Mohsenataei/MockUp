package com.example.deathstroke.uniqueoff1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import Service.RetrofitClient;
import adapters.MarkedPostsAdapter;
import adapters.RegPostAdapter;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Service.CustomTypefaceSpan;
import Service.SaveSharedPreference;
import Service.SetTypefaces;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bottomsheetdialoges.ConfirmExitbottomSheet;
import butterknife.ButterKnife;
import entities.Post;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookMarkedPosts extends AppCompatActivity implements DrawerLayout.DrawerListener {

    private static final String API_TOKEN = "RMG4nhzyQbqEqTev9NmAtw3WVsxx1Ew6nU6QbLwCIcNSSavlkULb65ihISdc" ;
    private static final String TAG = "BookMarkedPosts";
    protected DrawerLayout drawerLayout;
    protected ConstraintLayout main;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    TextView appbar_tv,appname;
    Typeface yekanfont;
    private Button signup,signin, followed_centers, bookmarks,terms_off_service, frequently_asked_questions,contactus,share_with_friends,exit,edit;
    ImageButton drawer,backbtn;
    private RecyclerView recyclerView;
    private RegPostAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Post> postList = new ArrayList<>();
    private Spinner cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarked_posts);
        Typeface yekanfont = Typeface.createFromAsset(getAssets(), "fonts/B Yekan+.ttf");
        bottomNavigationView = findViewById(R.id.navigation);
        main = findViewById(R.id.bookmarked_page);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerElevation(0);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(this);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawebtn);

        View header_items = navigationView.getHeaderView(0);

        initilizeheaderbuttons(header_items);

        setHeaderitems();
        handleNavDrawerItemClick();
        SetTypefaces.setButtonTypefaces(yekanfont,signup,signin, followed_centers, bookmarks,terms_off_service, frequently_asked_questions,contactus,share_with_friends,exit,edit);
        drawer.setOnClickListener(view -> {

            drawerLayout.openDrawer(navigationView);
        });
        appbar_tv = findViewById(R.id.just_appbar_tv);
        appbar_tv.setText("تخفیف های نشان شده");
        appbar_tv.setTypeface(yekanfont);

        backbtn = findViewById(R.id.back_button);

        backbtn.setOnClickListener(view->{
            finish();
        });

//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(0);
//        menuItem.setChecked(true);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,true);
        CustomTypefaceSpan typefaceSpan = new CustomTypefaceSpan("", yekanfont);
        for (int i=0;i<bottomNavigationView.getMenu().size();i++) {
            MenuItem mMenuitem = bottomNavigationView.getMenu().getItem(i);
            SpannableStringBuilder spannableTitle = new SpannableStringBuilder(mMenuitem.getTitle());
            spannableTitle.setSpan(typefaceSpan, 0, spannableTitle.length(), 0);
            mMenuitem.setTitle(spannableTitle);
            mMenuitem.setChecked(false);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home :
                    startActivity(new Intent(BookMarkedPosts.this,MainActivity.class));
                    break;
                case R.id.navigation_nearest_off :
                    startActivity(new Intent(BookMarkedPosts.this,Map.class));
                    break;
                case R.id.navigation_my_codes :
                    startActivity(new Intent(BookMarkedPosts.this,MyCodes.class));
                    break;
                case R.id.classification:
                    startActivity(new Intent(BookMarkedPosts.this,Classification.class));
                    break;
            }
            return false;
        });

        recyclerView = findViewById(R.id.bookmarked_posts_recyclerView);
        loadBooksMarkPosts();


        cities = header_items.findViewById(R.id.cities_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cities, R.layout.spinner_text_view_1);
        adapter.setDropDownViewResource(R.layout.spinner_text_view);
        cities.setAdapter(adapter);

        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = parent.getItemAtPosition(position).toString();
                SaveSharedPreference.setCity(BookMarkedPosts.this,city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private void initilizeheaderbuttons(View header_items) {
        ButterKnife.bind(header_items);
        signup = header_items.findViewById(R.id.header_sign_up);
        signin = header_items.findViewById(R.id.header_sign_in);
        bookmarks = header_items.findViewById(R.id.bookmark_centers);
        followed_centers = header_items.findViewById(R.id.followed_centers);
        frequently_asked_questions = header_items.findViewById(R.id.header_faq);
        terms_off_service = header_items.findViewById(R.id.terms);
        contactus = header_items.findViewById(R.id.contact_us);
        share_with_friends = header_items.findViewById(R.id.share_us);
        exit = header_items.findViewById(R.id.exit);
        edit = header_items.findViewById(R.id.edit);
        appname = header_items.findViewById(R.id.header_app_name);
    }
    private void handleNavDrawerItemClick(){
        signup.setOnClickListener(view->{
            startActivity(new Intent(BookMarkedPosts.this,SignUpActivity.class));
        });
        signin.setOnClickListener(view->{
            startActivity(new Intent(BookMarkedPosts.this,SingInActivity.class));
        });

        bookmarks.setOnClickListener(view -> {
            //startActivity(new Intent(BookMarkedPosts.this,BookMarkedPosts.class));
            drawerLayout.closeDrawer(navigationView);
        });

        followed_centers.setOnClickListener(view -> {
            startActivity(new Intent(BookMarkedPosts.this,FollowedShops.class));
            //drawerLayout.closeDrawer(navigationView);
        });

        frequently_asked_questions.setOnClickListener(view -> {
            startActivity(new Intent(BookMarkedPosts.this,FAQ.class));
        });

//        terms_off_service.setOnClickListener(view->{
//            startActivity(new Intent(MyCodes.this,.class));
//        });

        contactus.setOnClickListener(view->{
            //startActivity(new Intent(contact_us.this,contact_us.class));
            drawerLayout.closeDrawer(navigationView);
        });

        share_with_friends.setOnClickListener(view -> {
            //startActivity(new Intent(MyCodes.this,BookMarkedPosts.class));
            Toast.makeText(this, "پیشنهاد به دوستان", Toast.LENGTH_SHORT).show();
        });

        exit.setOnClickListener(view ->{
            //finish();
            //System.exit(0);
            ConfirmExitbottomSheet confirmExitbottomSheet = new ConfirmExitbottomSheet();
            confirmExitbottomSheet.show(getSupportFragmentManager(),"ConfirmExit");
        });

        edit.setOnClickListener(view->{
            //Toast.makeText(this, "this part is yet to be complete", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,EditProfie.class));
        });


    }
    private void setHeaderitems() {
        if(SaveSharedPreference.getAPITOKEN(BookMarkedPosts.this).length() > 0  ){
            signin.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.INVISIBLE);
            appname.setVisibility(View.VISIBLE);
            appname.setText(R.string.title_activity_test_navigation_drawer);
            appname.setTypeface(yekanfont);
        }
    }
    @Override
    public void onDrawerOpened(View arg0) {
        //write your code
    }

    @Override
    public void onDrawerClosed(View arg0) {
        //write your code
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        //write your code
        // getActionBar().setTitle("is it working ?");
        float slideX = drawerView.getWidth() * slideOffset;
        main.setTranslationX(-slideX);
    }

    @Override
    public void onDrawerStateChanged(int arg0) {
        //write your code
    }

    private void loadBooksMarkPosts(){
        Call<List<Post>> call = RetrofitClient.getmInstance().getApi().getMarkedPosts(SaveSharedPreference.getAPITOKEN(BookMarkedPosts.this));
        Log.d(TAG, "loadBooksMarkPosts: " + SaveSharedPreference.getAPITOKEN(BookMarkedPosts.this));
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.d(TAG, "onResponse: response is successful");
                    Toast.makeText(BookMarkedPosts.this, "onResponse: response is successful", Toast.LENGTH_SHORT).show();


                    if (postList.isEmpty()){

                        postList.clear();
                    }
                    postList = response.body();
                    if (!postList.isEmpty()){
                        adapter = new RegPostAdapter(postList,BookMarkedPosts.this);
                        layoutManager = new LinearLayoutManager(BookMarkedPosts.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }else{
                        //Snackbar snackbar = Snackbar.make(coordinator)
                        Toast.makeText(BookMarkedPosts.this, "شما هیچ پستی را نشان نکرده اید", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(BookMarkedPosts.this, "is it working ?", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(BookMarkedPosts.this, "onResponse: response is not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Log.d(TAG, "onFailure: why falied ?",t);
                Toast.makeText(BookMarkedPosts.this, "onResponse: connection failed", Toast.LENGTH_SHORT).show();
            }
        });
        

    }

}
