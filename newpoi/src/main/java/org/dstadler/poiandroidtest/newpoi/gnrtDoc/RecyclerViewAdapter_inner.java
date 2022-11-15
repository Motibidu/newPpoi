package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import static org.dstadler.poiandroidtest.newpoi.cls.customImageView.decodeSampledBitmapFromResource;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customImageView;

public class RecyclerViewAdapter_inner extends RecyclerView.Adapter {

    public static String PACKAGE_NAME;
    String []arr;
    String clsN;
    private Context mContext;


    private static final int WIDTH = 348;
    private static final int HEIGHT = 500;

    public RecyclerViewAdapter_inner(Context mContext, String[] arr, String clsN){
        this.arr = arr;
        this.mContext = mContext;
        this.clsN = clsN;
        PACKAGE_NAME = mContext.getPackageName();
    }

    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_inner, parent, false);
        return new CatHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        int drawableResourceId = res.getIdentifier(arr[position]+"_page0", "drawable", PACKAGE_NAME);
        ((CatHolder)holder).customImageView.setImageBitmap(decodeSampledBitmapFromResource(res, drawableResourceId, WIDTH, HEIGHT));
    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class CatHolder extends RecyclerView.ViewHolder{
        private customImageView customImageView;
        public CatHolder(@NonNull View itemView) {
            super(itemView);

            customImageView = itemView.findViewById(R.id.customImage);
            customImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    try {
                        Class<?> c =Class.forName(clsN);
                        intent = new Intent(v.getContext(), c);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Resources res = v.getContext().getResources();
                    Uri pagePath0 = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+res.getIdentifier(arr[getAdapterPosition()]+"_page0", "drawable", PACKAGE_NAME));
                    Uri pagePath1 = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+res.getIdentifier(arr[getAdapterPosition()]+"_page1", "drawable", PACKAGE_NAME));
                    Uri pagePath2 = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+res.getIdentifier(arr[getAdapterPosition()]+"_page2", "drawable", PACKAGE_NAME));

                    if (pagePath0 != null) {
                        intent.putExtra("pagePath0", pagePath0.toString());
                    }else{intent.putExtra("pagePath0", "");}

                    if (pagePath1 != null) {
                        intent.putExtra("pagePath1", pagePath1.toString());
                    }else{intent.putExtra("pagePath1", "");}

                    if (pagePath2 != null) {
                        intent.putExtra("pagePath2", pagePath2.toString());
                    }else{intent.putExtra("pagePath2", "");}


                    //Firebase Storage 내 문서 이름 : career_description0
                    intent.putExtra("docName",arr[getAdapterPosition()]);
                    v.getContext().startActivity(intent);
                }
            });
            customImageView.setColorFilter(Color.parseColor("#08000000"));


        }
    }
}
