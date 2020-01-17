package com.example.ts.neteasytest.Adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ts.neteasytest.R;
import com.example.ts.neteasytest.RecyclerViewData;

import java.util.List;

import t3.henu.left_library.GYB_solve.Fragments.RecyclerAdapter;
import t3.henu.left_library.GYB_solve.Fragments.RecyclerData;

/**
 * Created by ts on 20-1-14.
 */

public class RecyclerviewAdapter extends BaseMultiItemQuickAdapter<RecyclerViewData> {
    public RecyclerviewAdapter(List<RecyclerViewData> data) {
        super(data);
        addItemType(RecyclerViewData.TYPERECYCLE, R.layout.item_menu);
        addItemType(RecyclerViewData.TYPERECANGE,R.layout.item_divider);
        addItemType(RecyclerViewData.TYPEOVAL,R.layout.activity_main_drawerlayout_left_header);
    }
    public int getItemViewType(int posision){
        if (posision == 0){
            return RecyclerViewData.TYPEOVAL;
        }
        if (posision == 5 || posision == 8){
            return RecyclerViewData.TYPERECANGE;
        }
        else {
            return RecyclerViewData.TYPERECYCLE;
        }
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, RecyclerViewData recyclerViewData) {
        switch (baseViewHolder.getItemViewType()){
            case RecyclerViewData.TYPERECYCLE: {
                baseViewHolder.setImageResource(R.id.id_left_item_imageView, recyclerViewData.Image);
                baseViewHolder.setText(R.id.id_left_item_textView, recyclerViewData.Text);
                break;
            }
            case RecyclerViewData.TYPEOVAL:{
                baseViewHolder.setOnClickListener(R.id.id_appmain_drawelayout_left_btn_signin,new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.id_user_roundedImageView,new OnItemChildClickListener());
                break;
            }
        }
    }
}
