package t3.henu.left_library.GYB_solve;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class All_View{
    private TextView singer,song;
    private ImageView imageView;
    private ImageButton imageButton;

    public All_View(TextView singer, TextView song, ImageView imageView) {
        this.singer = singer;
        this.song = song;
        this.imageView = imageView;
    }

    public All_View(TextView singer, TextView song, ImageView imageView, ImageButton imageButton) {
        this.singer = singer;
        this.song = song;
        this.imageView = imageView;
        this.imageButton = imageButton;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public TextView getSinger() {
        return singer;
    }

    public void setSinger(TextView singer) {
        this.singer = singer;
    }

    public TextView getSong() {
        return song;
    }

    public void setSong(TextView song) {
        this.song = song;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
