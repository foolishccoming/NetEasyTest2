package t3.henu.left_library.GYB_solve.Fragments;

import com.chad.library.adapter.base.entity.MultiItemEntity;



public class RecyclerData extends MultiItemEntity {
    public static final int STYLE=1;
    public static final int GEDAN=2;
    public static final int DIVIDER=3;
    public static final int DIVIDER_CHILD=4;
    private int Image;
    private String text;
    private String num;

    public RecyclerData(int image, String text, String num) {
        Image = image;
        this.text = text;
        this.num = num;
    }

    public RecyclerData(int image) {
        Image = image;
        this.text="a";
        this.num="2";
    }

    public String getNum() {
        return "("+num+")";
    }

    public void setNum(String num) {
        this.num=num;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
