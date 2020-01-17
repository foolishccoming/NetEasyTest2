package t3.henu.left_library.GYB_solve.Fragments;

import android.view.View;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import t3.henu.left_library.R;

import java.util.List;




public class RecyclerAdapter extends BaseMultiItemQuickAdapter<RecyclerData> {
    private final String  TAG="RecyclerAdapter";
    private List<RecyclerData>datas;
    public RecyclerAdapter(List<RecyclerData> data) {
        super(data);
        this.datas=data;
        addItemType(RecyclerData.STYLE, R.layout.recyclerviewitem);
        addItemType(RecyclerData.GEDAN, R.layout.gybrecyclerview_gedan);
        addItemType(RecyclerData.DIVIDER_CHILD, R.layout.gyb_recyclerview_gedan_item);
    }

    @Override
    protected int getDefItemViewType(int position) {
        //Log.e(TAG, "convert: "+ position );
        if (position < 5) {
            return RecyclerData.STYLE;
        }else {
            return RecyclerData.GEDAN;
        }
    }



    @Override
    protected void convert(final BaseViewHolder baseViewHolder, RecyclerData recyclerData) {
        switch (baseViewHolder.getItemViewType()) {
            case RecyclerData.STYLE:


                baseViewHolder.setImageResource(R.id.id_recycler_Allmusic_recyclerItem_iview1, recyclerData.getImage());
                baseViewHolder.setText(R.id.id_recycler_Allmusic_recyclerItem_tview1, recyclerData.getText());
                baseViewHolder.setText(R.id.id_recycler_Allmusic_recyclerItem_tview2, recyclerData.getNum());
                break;
            case RecyclerData.GEDAN:
                baseViewHolder.setText(R.id.id_recycler_Allmusic_recyclerItem_gedan_tview1,recyclerData.getText());
                baseViewHolder.setText(R.id.id_recycler_Allmusic_recyclerItem_gedan_tview2,recyclerData.getNum());

                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos=baseViewHolder.getAdapterPosition();
                       /* if(){
                            collapse(pos);
                        }else{
                            expand(pos);
                        }*/
                    }
                });
                break;

        }
    }
}
