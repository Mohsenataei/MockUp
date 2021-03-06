package com.example.deathstroke.uniqueoff1;

import android.content.Context;
import android.os.Bundle;

import Service.RetrofitClient;
import adapters.RegPostAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import entities.Post;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link shop_offs_fragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link shop_offs_fragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class shop_offs_fragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Post> shopPosts = new ArrayList<>();
    private String shopid;

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
    //
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public shop_offs_fragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment shop_offs_fragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static shop_offs_fragment newInstance(String param1, String param2) {
//        shop_offs_fragment fragment = new shop_offs_fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutInflater = inflater.inflate(R.layout.fragment_shop_offs_fragment, container, false);
        recyclerView = layoutInflater.findViewById(R.id.shop_offs_recycler_view);


       // mAdapter = new RecyclerViewAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        getshopPosts();
        return layoutInflater;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getshopPosts();
    }

    private void getshopPosts(){

        Call<List<Post>> call = RetrofitClient.getmInstance().getApi().getShopPosts(getShopid());

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.d("fragment", "in shop post onResponse: working");

                    if (shopPosts.isEmpty()) shopPosts.clear();

                    shopPosts = response.body();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    RegPostAdapter regPostAdapter = new RegPostAdapter(shopPosts,getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(regPostAdapter);
                    //regPostAdapter.
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onShopOffsFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onShopOffsFragmentInteraction(Uri uri);
//    }

}
