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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customImageView;

import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter_outter extends RecyclerView.Adapter {

    public static String PACKAGE_NAME;
    private Map< String, String[] > arrMap;
    private Context mContext;

    private static final int WIDTH = 348;
    private static final int HEIGHT = 500;

    public RecyclerViewAdapter_outter(Context mContext, Map<String, String[]> arrMap){
        this.arrMap = arrMap;
        this.mContext = mContext;
        PACKAGE_NAME = mContext.getPackageName();
    }

    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_outter, parent, false);
        return new CatHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        int drawableResourceId = res.getIdentifier(arrMap.get("res")[position]+"_page0", "drawable", PACKAGE_NAME);
        ((CatHolder)holder).customImage.setImageBitmap(decodeSampledBitmapFromResource(res, drawableResourceId, WIDTH, HEIGHT));
        ((CatHolder)holder).text.setText(arrMap.get("title")[position]);
    }

    @Override
    public int getItemCount() {
        return arrMap.get("res").length;
    }

    public class CatHolder extends RecyclerView.ViewHolder{
        private customImageView customImage;
        private TextView text;
        public CatHolder(@NonNull View itemView) {
            super(itemView);

            customImage = itemView.findViewById(R.id.customImage);
            text = itemView.findViewById(R.id.text);
            customImage.setColorFilter(Color.parseColor("#6F000000"));
            customImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //careerDescription_expanded_screen으로 이동
                    Intent intent = null;
                    try {
                        Class<?> c =Class.forName(arrMap.get("clsN")[getAdapterPosition()]);
                        intent = new Intent(v.getContext(), c);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
