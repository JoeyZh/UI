package adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joey.ui.example.R;

import java.util.Random;


/**
 * Created by Administrator on 2016/10/11.
 * 国币商城，RecyclerAdapter
 */
public class GuoBiMarketAdapter extends RecyclerView.Adapter<GuoBiMarketAdapter.ViewHolderSale> {

    private int layoutId = 1;
    private String[] result;
    private Context mContext;

    private int color[] = {Color.RED,Color.YELLOW,Color.BLUE,Color.CYAN,Color.DKGRAY};

    public void setViewType(int layoutId) {
        this.layoutId = layoutId;
    }

    public GuoBiMarketAdapter(Context context, String[] result) {
        this.mContext = context;
        this.result = result;

    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    @Override
    public ViewHolderSale onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = null;

        viewItem = LayoutInflater.from(
                mContext).inflate(R.layout.item_test, parent,
                false);

        return new ViewHolderSale(viewItem);
    }


    @Override
    public void onBindViewHolder(ViewHolderSale holder, final int position) {
        holder.mTv_priSpe.setText(result[position]);
        Random ran = new Random();
        int index= ran.nextInt(100)%color.length;
        holder.root.setBackgroundColor(color[index]);
    }


    @Override
    public int getItemCount() {
        return result.length;
    }

    class ViewHolderSale extends RecyclerView.ViewHolder {
        private TextView mTv_priSpe;
        private View root;

        public ViewHolderSale(View view) {
            super(view);
            mTv_priSpe = (TextView) view.findViewById(R.id.tv_test);
            root = view.findViewById(R.id.ll_test);
        }
    }
}
