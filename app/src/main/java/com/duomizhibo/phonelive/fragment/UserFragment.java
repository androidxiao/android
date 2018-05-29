package com.duomizhibo.phonelive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duomizhibo.phonelive.AppConfig;
import com.duomizhibo.phonelive.AppContext;
import com.duomizhibo.phonelive.R;
import com.duomizhibo.phonelive.api.remote.ApiUtils;
import com.duomizhibo.phonelive.api.remote.PhoneLiveApi;
import com.duomizhibo.phonelive.base.BaseFragment;
import com.duomizhibo.phonelive.bean.UserBean;
import com.duomizhibo.phonelive.utils.LiveUtils;
import com.duomizhibo.phonelive.utils.StringUtils;
import com.duomizhibo.phonelive.utils.UIHelper;
import com.duomizhibo.phonelive.widget.AvatarView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by chawei on 2018/5/26.
 */

public class UserFragment extends BaseFragment {


    //头像
    @InjectView(R.id.iv_avatar)
    AvatarView mIvAvatar;
    //昵称
    @InjectView(R.id.tv_name)
    TextView mTvName;

    @InjectView(R.id.ll_user_container)
    View mUserContainer;


    //关注
    @InjectView(R.id.tv_info_u_follow_num)
    TextView mFollowNum;

    //粉丝
    @InjectView(R.id.tv_info_u_fans_num)
    TextView mFansNum;

    @InjectView(R.id.id_tv_source_account)
    TextView tvSourceAccount;


    @InjectView(R.id.iv_sex)
    ImageView mIvSex;

    @InjectView(R.id.iv_level)
    ImageView mIvLevel;

    @InjectView(R.id.iv_anchorlevel)
    ImageView mIvAnchorLevel;

    @InjectView(R.id.tvt_title)
     TextView tvt_title;

    @InjectView(R.id.btn_mesg)
     ImageView btn_mesg;

    private UserBean mInfo;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_layout,
                container, false);
        ButterKnife.inject(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        tvt_title.setText("个人中心");
        btn_mesg.setVisibility(View.VISIBLE);
        mInfo = AppContext.getInstance().getLoginUser();
        fillUI();
    }

    private void fillUI() {
        if (mInfo == null)
            return;

        tvSourceAccount.setText(mInfo.id);
        mIvAvatar.setAvatarUrl(mInfo.avatar);
        //昵称
        mTvName.setText(mInfo.user_nicename);


        mIvSex.setImageResource(LiveUtils.getSexRes(mInfo.sex));
        mIvLevel.setImageResource(LiveUtils.getLevelRes(mInfo.level));
        mIvAnchorLevel.setImageResource(LiveUtils.getAnchorLevelRes(mInfo.level_anchor));
    }

    @Override
    public void onResume() {
        super.onResume();
        sendRequestData();
    }

    private void sendRequestData() {

        PhoneLiveApi.getMyUserInfo(AppContext.getInstance().getLoginUid(),
                AppContext.getInstance().getToken(), stringCallback);
    }

    private StringCallback stringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String s, int id) {
            JSONArray res = ApiUtils.checkIsSuccess(s);
            if (res != null) {
                try {
                    JSONObject object = res.getJSONObject(0);
                    mInfo = new Gson().fromJson(object.toString(), UserBean.class);
                    AppContext.getInstance().updateUserInfo(mInfo);

                    mFollowNum.setText(object.getString("follows"));
                    mFansNum.setText(object.getString("fans"));
                    int goodnum = StringUtils.toInt(object.getJSONObject("liang").getString("name"));
//                    if (goodnum != 0) {
//                        mUId.setText("靓:" + goodnum);
//                    } else {
//                        mUId.setText("ID:" + mInfo.id);
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    };

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        view.findViewById(R.id.tv_edit_info).setOnClickListener(this);
        view.findViewById(R.id.ll_following).setOnClickListener(this);
        view.findViewById(R.id.ll_fans).setOnClickListener(this);
        view.findViewById(R.id.ll_video).setOnClickListener(this);
        view.findViewById(R.id.ll_sign_in).setOnClickListener(this);
        view.findViewById(R.id.ll_invite_code).setOnClickListener(this);
        view.findViewById(R.id.ll_message).setOnClickListener(this);
        view.findViewById(R.id.ll_concat).setOnClickListener(this);
        view.findViewById(R.id.ll_setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            //编辑资料
            case R.id.tv_edit_info:
                UIHelper.showMyInfoDetailActivity(getContext());
                break;
            case R.id.ll_following:
                UIHelper.showAttentionActivity(getActivity(), mInfo.id);
                break;
            case R.id.ll_fans:
                UIHelper.showFansActivity(getActivity(), mInfo.id);
                break;
            case R.id.ll_video://我的视频
                UIHelper.shoMyVideoActivity(getContext());
                break;
            case R.id.ll_sign_in://签到
                UIHelper.showWebView(getContext(), AppConfig.MAIN_URL + "/index.php?g=Appapi&m=Member&a=signin&uid=" + mInfo.id + "&token=" + AppContext.getInstance().getToken(), "");
                break;
            case R.id.ll_invite_code://我的邀请码
                UIHelper.showWebView(getContext(), AppConfig.MAIN_URL + "/index.php?g=Appapi&m=Member&a=invite&uid=" + mInfo.id + "&token=" + AppContext.getInstance().getToken()
                        , "");
                break;
            case R.id.ll_message://消息中心
                UIHelper.showWebView(getContext(), AppConfig.MAIN_URL + "/index.php?g=Appapi&m=Member&a=message&uid=" + mInfo.id + "&token=" + AppContext.getInstance().getToken(), "");
                break;
            case R.id.ll_concat://联系我们
                UIHelper.showWebView(getContext(), AppConfig.MAIN_URL + "/index.php?g=Appapi&m=Member&a=contactus&uid=" + mInfo.id + "&token=" + AppContext.getInstance().getToken(), "");
                break;
            case R.id.ll_setting://设置
                UIHelper.showSetting(getActivity());
                break;

        }
    }
}
