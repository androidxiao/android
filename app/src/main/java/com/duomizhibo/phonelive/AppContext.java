package com.duomizhibo.phonelive;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;
import com.duomizhibo.phonelive.base.BaseApplication;
import com.duomizhibo.phonelive.bean.UserBean;
import com.duomizhibo.phonelive.cache.DataCleanManager;
import com.duomizhibo.phonelive.event.Event;
import com.duomizhibo.phonelive.utils.StringUtils;
import com.duomizhibo.phonelive.utils.TLog;
import com.duomizhibo.phonelive.utils.UIHelper;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.NetUtils;
import com.duomizhibo.phonelive.utils.MethodsCompat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.greenrobot.eventbus.EventBus;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @version 1.0
 */
public class AppContext extends BaseApplication {


    private static AppContext instance;

    private String loginUid;
    private String Token;
    private boolean login = false;
    public static String address = "好像在火星";
    public static String province = "";
    //HHH 2016-09-09
    public static String lng = "1";
    public static String lat = "1";


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
        initLogin();
        UIHelper.sendBroadcastForNotice(this);

    }


    private void init() {


        //环信初始化
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(false);
        setGlobalListeners();


        //初始化jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


        //网络请求初始化
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .cache(null)
                .retryOnConnectionFailure(true)
                .addInterceptor(new LoggerInterceptor("weipeng"))
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

//        //字体库
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("tqht.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }


    protected void setGlobalListeners() {
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }


    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            if (error == EMError.USER_REMOVED) {
                // 显示帐号已经被移除
                TLog.log("显示帐号已经被移除");
            } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                // 显示帐号在其他设备登陆
                TLog.log("显示帐号在其他设备登陆");
            } else {
                if (NetUtils.hasNetwork(AppContext.getInstance())) {
                    //连接不到聊天服务器
                    TLog.log("连接不到聊天服务器");
                } else {
                    //当前网络不可用，请检查网络设置
                    TLog.log("当前网络不可用，请检查网络设置");
                    Event.CommonEvent event = new Event.CommonEvent();
                    event.action = 1;
                    EventBus.getDefault().post(event);
                }

            }
        }
    }

    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            TLog.error("--------收到消息:" + messages);
            Intent broadcastIntent = new Intent("com.duomizhibo.phonelive");
            broadcastIntent.putExtra("cmd_value", messages.get(0));
            sendBroadcast(broadcastIntent, null);
            //收到消息
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            TLog.error("----------收到透传消息:" + messages);
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            TLog.error("----------onMessageRead:" + messages);
        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {
            TLog.error("----------onMessageDelivered:" + messages);
        }


        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            TLog.log("----------消息状态变动:" + message);
        }
    };

    private void initLogin() {
        UserBean user = getLoginUser();
        if (null != user && StringUtils.toInt(user.id) > 0) {
            login = true;
            loginUid = user.id;
            Token = user.token;
        } else {
            this.cleanLoginInfo();
        }
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 保存登录信息
     *
     * @param user 用户信息
     */
    @SuppressWarnings("serial")
    public void saveUserInfo(final UserBean user) {
        this.loginUid = user.id;
        this.Token = user.token;
        this.login = true;
        setProperties(new Properties() {
            {
                setProperty("user.uid", user.id);
                setProperty("user.name", user.user_nicename);
                setProperty("user.token", user.token);
                setProperty("user.sign", user.signature);
                setProperty("user.avatar", user.avatar);

                setProperty("user.coin", user.coin);
                setProperty("user.sex", user.sex);
                setProperty("user.signature", user.signature);
                setProperty("user.avatar_thumb", user.avatar_thumb);
                setProperty("user.level", user.level);
                setProperty("user.level_anchor", user.level_anchor);
                setProperty("user.isreg", user.isreg== null ? "" :user.isreg);

            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    @SuppressWarnings("serial")
    public void updateUserInfo(final UserBean user) {
        setProperties(new Properties() {
            {
                setProperty("user.uid", user.id);
                setProperty("user.name", user.user_nicename);
                setProperty("user.sign", user.signature);
                setProperty("user.avatar", user.avatar);
                setProperty("user.city", user.city == null ? "" : user.city);
                setProperty("user.coin", user.coin);
                setProperty("user.sex", user.sex);
                setProperty("user.signature", user.signature);
                setProperty("user.avatar_thumb", user.avatar_thumb);
                setProperty("user.level", user.level);
                setProperty("user.level_anchor", user.level_anchor);
                setProperty("user.isreg", user.isreg== null ? "" :user.isreg);
            }
        });
    }

    /**
     * 获得登录用户的信息
     *
     * @return
     */
    public UserBean getLoginUser() {
        UserBean user = new UserBean();
        user.id = getProperty("user.uid");
        user.avatar = getProperty("user.avatar");
        user.user_nicename = getProperty("user.name");
        user.signature = getProperty("user.sign");
        user.token = getProperty("user.token");
        user.votes = getProperty("user.votes");
        user.city = getProperty("user.city");
        user.coin = getProperty("user.coin");
        user.sex = getProperty("user.sex");
        user.signature = getProperty("user.signature");
        user.avatar = getProperty("user.avatar");
        user.level = getProperty("user.level");
        user.level_anchor = getProperty("user.level_anchor");
        user.avatar_thumb = getProperty("user.avatar_thumb");
        user.birthday = getProperty("user.birthday");
        user.isreg      = getProperty("user.isreg");
        return user;
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.loginUid = "0";
        this.login = false;
        removeProperty("user.isreg","user.birthday", "user.avatar_thumb", "user.uid", "user.token", "user.name", "user.pwd", "user.avatar", "user.sign", "user.city", "user.coin", "user.sex", "user.signature", "user.signature", "user.avatar", "user.level", "user.level_anchor");
    }

    public String getLoginUid() {
        return loginUid;
    }

    public String getToken() {
        return Token;
    }

    public boolean isLogin() {
        return login;
    }

    /**
     * 用户注销
     */
    public void Logout() {
        cleanLoginInfo();

        this.login = false;
        this.loginUid = "0";
        this.Token = "";

    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
    }


    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static String getTweetDraft() {
        return getPreferences().getString(
                AppConfig.KEY_TWEET_DRAFT + getInstance().getLoginUid(), "");
    }

    public static void setTweetDraft(String draft) {
        set(AppConfig.KEY_TWEET_DRAFT + getInstance().getLoginUid(), draft);
    }

    public static String getNoteDraft() {
        return getPreferences().getString(
                AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), "");
    }

    public static void setNoteDraft(String draft) {
        set(AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), draft);
    }

    public static boolean isFristStart() {
        return getPreferences().getBoolean(AppConfig.KEY_FRITST_START, true);
    }

    public static void setFristStart(boolean frist) {
        set(AppConfig.KEY_FRITST_START, frist);
    }

    //夜间模式
    public static boolean getNightModeSwitch() {
        return getPreferences().getBoolean(AppConfig.KEY_NIGHT_MODE_SWITCH, false);
    }

    // 设置夜间模式
    public static void setNightModeSwitch(boolean on) {
        set(AppConfig.KEY_NIGHT_MODE_SWITCH, on);
    }

    public static void showToastAppMsg(Context context, String msg) {
        if (context == null) return;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void toast(String msg) {
        Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
    }

}
