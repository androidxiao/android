package com.duomizhibo.phonelive.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duomizhibo.phonelive.R;
import com.duomizhibo.phonelive.base.AbsFragment;

/**
 * Created by Time on 2018/5/27.
 */

public class TabChatFragment extends AbsFragment {


    private Context mContext;
    private View mRootView;
    TextView tvt_title;
    ImageView btn_mesg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.fragment_tab_chat, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
    }
    private void initView(Bundle savedInstanceState) {
        tvt_title= (TextView) mRootView.findViewById(R.id.tvt_title);
        btn_mesg= (ImageView) mRootView.findViewById(R.id.btn_mesg);
        tvt_title.setText("聊天");
    }
}
