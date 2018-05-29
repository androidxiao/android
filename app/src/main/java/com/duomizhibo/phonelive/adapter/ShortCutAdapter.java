package com.duomizhibo.phonelive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duomizhibo.phonelive.R;

import java.util.List;

/**
 * Created by chawei on 2018/5/29.
 *
 * 评论使用快捷语
 */

public class ShortCutAdapter extends RecyclerView.Adapter<ShortCutAdapter.ShortCutHolder>{

    private Context mContext;

    private List<String> mCommentList;

    public ShortCutAdapter(Context context) {
        mContext=context;
    }

    public void appendList(List<String> commentList){
        mCommentList=commentList;
        notifyDataSetChanged();
    }


    @Override
    public ShortCutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShortCutHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shortcut_view,parent,false));
    }

    @Override
    public void onBindViewHolder(ShortCutHolder holder, int position) {
        holder.tvContent.setText(mCommentList.get(position));
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mCommentList==null?0:mCommentList.size();
    }

    class ShortCutHolder extends RecyclerView.ViewHolder{

        TextView tvContent;
        int position;

        public ShortCutHolder(View itemView) {
            super(itemView);
            tvContent= (TextView) itemView.findViewById(R.id.id_tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.item(position);
                    }
                }
            });
        }
        public void setPosition(int position){
            this.position=position;
        }
    }

    private IOnItemClickListener mListener;

    public void setListener(IOnItemClickListener listener){
        mListener=listener;
    }

    public interface IOnItemClickListener{
        void item(int position);
    }

}
