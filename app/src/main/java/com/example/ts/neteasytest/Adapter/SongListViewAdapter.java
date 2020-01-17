package com.example.ts.neteasytest.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ts.neteasytest.Fragments.SelectItems;
import com.example.ts.neteasytest.R;

import java.util.List;

/**
 * Created by ts on 20-1-16.
 */

public class SongListViewAdapter extends RecyclerView.Adapter<SongListViewAdapter.ViewHolder> {
    private List<SelectItems> mSelectItems;
    public SongListViewAdapter(List<SelectItems> selectItemses){mSelectItems = selectItemses;}

    @Override
    public SongListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_type, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了:" + si.getmItemTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.mLinearLayoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了" + si.getmTextBeauty(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.mLinearLayoutMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了" + si.getmTextBorder(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.mLinearLayoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了" + si.getmTextUnbraller(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }


    @Override
    public int getItemCount() {
        return mSelectItems.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        View mSelectView;
        LinearLayout mLinearLayoutLeft, mLinearLayoutMid, mLinearLayoutRight;
        TextView tv_name, mTextViewLeft, mTextViewMid, mTextViewRight;
        ImageView imgLeft, imgMid, imgRight;

        public ViewHolder(View view) {
            super(view);
            mSelectView = view;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            mTextViewLeft = (TextView) view.findViewById(R.id.tv1);
            mTextViewMid = (TextView) view.findViewById(R.id.tv2);
            mTextViewRight = (TextView) view.findViewById(R.id.tv3);
            imgLeft = (ImageView) view.findViewById(R.id.img1);
            imgMid = (ImageView) view.findViewById(R.id.img2);
            imgRight = (ImageView) view.findViewById(R.id.img3);
            mLinearLayoutLeft = (LinearLayout) view.findViewById(R.id.ll1);
            mLinearLayoutMid = (LinearLayout) view.findViewById(R.id.ll2);
            mLinearLayoutRight = (LinearLayout) view.findViewById(R.id.ll3);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SelectItems selectItems = mSelectItems.get(position);
        holder.tv_name.setText(selectItems.getmItemTitle());
        holder.mTextViewLeft.setText(selectItems.getmTextBeauty());
        holder.mTextViewMid.setText(selectItems.getmTextBorder());
        holder.mTextViewRight.setText(selectItems.getmTextUnbraller());
        holder.imgLeft.setImageResource(selectItems.getmImageBeauty());
        holder.imgMid.setImageResource(selectItems.getmImageBorder());
        holder.imgRight.setImageResource(selectItems.getmImageUnbraller());
    }
}
