package com.duomizhibo.phonelive.ui;

import android.view.View;

import com.duomizhibo.phonelive.AppContext;
import com.duomizhibo.phonelive.R;
import com.duomizhibo.phonelive.api.remote.ApiUtils;
import com.duomizhibo.phonelive.api.remote.PhoneLiveApi;
import com.duomizhibo.phonelive.base.ToolBarBaseActivity;
import com.duomizhibo.phonelive.bean.UserBean;
import com.duomizhibo.phonelive.ui.customviews.ActivityTitle;
import com.duomizhibo.phonelive.utils.LoginUtils;
import com.duomizhibo.phonelive.utils.TDevice;
import com.duomizhibo.phonelive.utils.TLog;
import com.duomizhibo.phonelive.widget.BlackButton;
import com.duomizhibo.phonelive.widget.BlackEditText;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by chawei on 2018/5/26.
 */

public class BindAccountActivity extends ToolBarBaseActivity {

    @InjectView(R.id.view_title)
    ActivityTitle mActivityTitle;


    @InjectView(R.id.et_password)
    BlackEditText mEtUserPassword;

    @InjectView(R.id.btn_dologin)
    BlackButton btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_account_layout;
    }

    @Override
    public void initView() {
        mActivityTitle.setReturnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @OnClick(R.id.btn_dologin)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_dologin) {

            if (prepareForLogin()) {
                return;
            }
            String mPassword = mEtUserPassword.getText().toString();

            showWaitDialog(R.string.loading);

            PhoneLiveApi.getBindAccount(mPassword,callback);

        }
    }

    //登录回调
    private final StringCallback callback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            hideWaitDialog();
            AppContext.showToast("网络请求出错!");
        }
        @Override
        public void onResponse(String s, int id) {
            TLog.log("绑定成功--->"+s);
            hideWaitDialog();

                PhoneLiveApi.getBind2Login(loginCallback);
        }
    };


    private final StringCallback loginCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            hideWaitDialog();
            AppContext.showToast("网络请求出错!");
        }
        @Override
        public void onResponse(String s, int id) {
            hideWaitDialog();
            TLog.log("登录成功--->"+s);
            JSONArray requestRes = ApiUtils.checkIsSuccess(s);
            if (requestRes != null) {

                Gson gson = new Gson();
                try {
                    UserBean user = gson.fromJson(requestRes.getString(0), UserBean.class);

                    AppContext.getInstance().saveUserInfo(user);

                    LoginUtils.getInstance().OtherInit(BindAccountActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };




    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return true;
        }

        if (mEtUserPassword.length() == 0) {
            mEtUserPassword.setError("请输入密码");
            mEtUserPassword.requestFocus();
            return true;
        }


        return false;
    }
}
