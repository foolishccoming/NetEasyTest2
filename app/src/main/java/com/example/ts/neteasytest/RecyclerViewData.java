package com.example.ts.neteasytest;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by ts on 20-1-13.
 */

public class RecyclerViewData extends MultiItemEntity {
    public final static int TYPERECYCLE=1;
    public final static int TYPERECANGE=2;
    public final static int TYPEOVAL=3;
    public int Image;
    public String Text;
    public RecyclerViewData(int image,String text){
        this.Image = image;
        this.Text = text;
    }
}
