package com.duomizhibo.phonelive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.duomizhibo.phonelive.ui.MainActivity;
import com.duomizhibo.phonelive.utils.SharedPreUtil;
import com.hyphenate.chat.EMClient;
import com.duomizhibo.phonelive.utils.UIHelper;


import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * 应用启动界面
 *
 */
public class AppStart extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkAPP(this);

        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        // SystemTool.gc(this); //针对性能好的手机使用，加快应用相应速度

        final View view = View.inflate(this, R.layout.app_start, null);
        setContentView(view);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(800);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation) {}
        });
    }

    //字体库
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {

        if(!AppContext.getInstance().isLogin()){

            UIHelper.showLoginSelectActivity(this);
            finish();
            return;
        }

        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    int checkAPP(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];

            int hashcode = sign.hashCode();
            Log.i("myhashcode", "hashCode : " + hashcode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
