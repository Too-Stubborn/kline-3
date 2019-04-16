package com.huobi.klinelib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huobi.klinelib.R;
import com.huobi.klinelib.entity.IDepth;
import com.huobi.klinelib.utils.DiffUtilCallBAck;
import com.huobi.klinelib.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.adapter
 * @FileName     : DepthRecycleViewAdapter.java
 * @Author       : chao
 * @Date         : 2019/1/11
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public class DepthRecycleViewAdapter<T extends IDepth> extends RecyclerView.Adapter<DepthRecycleViewAdapter.DepthViewHolder> {

    private float rightMax;
    private float leftMax;
    private List<T> leftDatas = new ArrayList<>();
    private List<T> rightDatas = new ArrayList<>();

    private Context context;

    public DepthRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<T> leftDatas, List<T> rightDatas, float leftMax, float rightMax) {
//        this.leftDatas = leftDatas;
//        this.rightDatas = rightDatas;
        this.leftMax = leftMax;
        this.rightMax = rightMax;

        if (this.leftDatas.size() != 0 || this.rightDatas.size() != 0) {
            this.leftDatas.clear();
            this.rightDatas.clear();
        }
        this.leftDatas.addAll(leftDatas);
        this.rightDatas.addAll(rightDatas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DepthRecycleViewAdapter.DepthViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_horizental_depth, viewGroup, false);
        return new DepthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DepthRecycleViewAdapter.DepthViewHolder viewHolder, int i) {
        T left = leftDatas.get(leftDatas.size() - i - 1);
        T right = rightDatas.get(i);
        String index = String.valueOf(i + 1);

        viewHolder.textViewLeftIndex.setText(index);
        viewHolder.textViewRightIndex.setText(index);

        viewHolder.textViewLeftPrice.setText(String.format("%.2f", left.getPrice()));
        viewHolder.textViewRightPrice.setText(String.format("%.2f", right.getPrice()));

        viewHolder.textViewLeftAmount.setText(String.format("%.2f", left.getVol()));
        viewHolder.textViewRightAmount.setText(String.format("%.2f", right.getVol()));

        int progress = 100 - (int) (left.getVol() / leftMax * 100 + 0.5);
        viewHolder.progressLeft.setProgress(progress);
        viewHolder.progressRight.setProgress((int) (right.getVol() / rightMax * 100 + 0.5));


    }


    @Override
    public int getItemCount() {
        int size = rightDatas.size();
        return /*size > 20 ? 20 :*/ size;
    }

    public class DepthViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewLeftIndex;
        private TextView textViewLeftAmount;
        private TextView textViewLeftPrice;
        private TextView textViewRightIndex;
        private TextView textViewRightAmount;
        private TextView textViewRightPrice;
        private ProgressBar progressLeft;
        private ProgressBar progressRight;

        public DepthViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLeftIndex = itemView.findViewById(R.id.text_view_left_index);
            textViewLeftAmount = itemView.findViewById(R.id.text_view_left_amount);
            textViewLeftPrice = itemView.findViewById(R.id.text_view_left_price);
            textViewRightIndex = itemView.findViewById(R.id.text_view_right_index);
            textViewRightAmount = itemView.findViewById(R.id.text_view_right_amount);
            textViewRightPrice = itemView.findViewById(R.id.text_view_right_price);
            progressLeft = itemView.findViewById(R.id.progress_left);
            progressRight = itemView.findViewById(R.id.progress_right);

            progressLeft.setProgressDrawable(context.getResources().getDrawable(R.drawable.depth_progress_bar_left));
        }

    }

}
