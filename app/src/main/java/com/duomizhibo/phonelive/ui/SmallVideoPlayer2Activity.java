package com.duomizhibo.phonelive.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.duomizhibo.phonelive.AppConfig;
import com.duomizhibo.phonelive.AppContext;
import com.duomizhibo.phonelive.R;
import com.duomizhibo.phonelive.api.remote.ApiUtils;
import com.duomizhibo.phonelive.api.remote.PhoneLiveApi;
import com.duomizhibo.phonelive.bean.ActiveBean;
import com.duomizhibo.phonelive.bean.SimpleUserInfo;
import com.duomizhibo.phonelive.bean.UserInfo;
import com.duomizhibo.phonelive.fragment.CommentDialogFragment;
import com.duomizhibo.phonelive.fragment.CommentFragment;
import com.duomizhibo.phonelive.fragment.SmallVideoFragment;
import com.duomizhibo.phonelive.fragment.UserInfoDialogFragment;
import com.duomizhibo.phonelive.fragment.VideoShareFragment;
import com.duomizhibo.phonelive.utils.ShareUtils;
import com.duomizhibo.phonelive.utils.TDevice;
import com.duomizhibo.phonelive.utils.TLog;
import com.duomizhibo.phonelive.utils.UIHelper;
import com.duomizhibo.phonelive.widget.AvatarView;
import com.duomizhibo.phonelive.widget.LoadUrlImageView;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import okhttp3.Call;

/**
 * Created by chawei on 2018/5/27.
 */

public class SmallVideoPlayer2Activity extends AppCompatActivity implements  VideoShareFragment.deleteClick,View.OnClickListener,SmallVideoFragment.ILoadingListener{


    //    private SmallVideoFragment mSmallVideoFragment = SmallVideoFragment.newInstance();
    private SmallVideoFragment mSmallVideoFragment;
    private boolean mInit = false;
    private FragmentManager mFragmentManager;
    private RelativeLayout mRoomContainer;
    private FrameLayout mFragmentContainer;
//    private TXCloudVideoView mVideoView;
    private PagerAdapter mPagerAdapter;
    private int mCurrentItem=-1;
    private VerticalViewPager mViewPager;
    private int mRoomId = -1;
    private Bundle mBundle;
    private int mPagePosition;

    public final static String USER_INFO = "USER_INFO";

    protected TXCloudVideoView mTXCloudVideoView;
    //加载中的背景图
    protected LoadUrlImageView mIvLoadingBg;

    protected ImageView mIvAttention;

    protected AvatarView mAvEmcee;

    protected static TextView mTvCommentNum;

    protected TextView mTvLaudNum;

    protected ImageView mIvLaud;

    protected ImageView mIvGif;

    protected TextView mUName;

    protected TextView mTitle;

    protected ImageView mCai;

    protected static TextView mShareCount;//分享数

    private Button mBtnSend;

    private ArrayList<ActiveBean> mList;

    private UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    View mVideoLayout;
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;

    private TextView mTvVideoTitle;


    private void findView() {
//        mView.findViewById(R.id.rl_live_root).getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListenernew);
        mTXCloudVideoView = (TXCloudVideoView)findViewById(R.id.video_view);
        mTXCloudVideoView.setOnClickListener(mClickListener);
        mIvLoadingBg = (LoadUrlImageView)findViewById(R.id.iv_live_look_loading_bg);
        mIvAttention = (ImageView)findViewById(R.id.tv_attention);
        mAvEmcee = (AvatarView)findViewById(R.id.iv_live_emcee_head);
        mTvCommentNum = (TextView)findViewById(R.id.tv_video_commrntnum);
        mTvLaudNum = (TextView)findViewById(R.id.tv_video_laudnum);
        mIvLaud = (ImageView)findViewById(R.id.iv_video_laud);
        mIvGif = (ImageView)findViewById(R.id.iv_video_laudgif);
        mUName = (TextView) findViewById(R.id.tv_name);
        mTitle = (TextView) findViewById(R.id.title);
        mCai = (ImageView)findViewById(R.id.btn_cai);
        mShareCount = (TextView) findViewById(R.id.share_nums);
        mVideoView = (UniversalVideoView) findViewById(R.id.id_video_view);
        mVideoLayout = findViewById(R.id.video_layout);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        mTvVideoTitle = (TextView) findViewById(R.id.id_tv_video_title);

        findViewById(R.id.iv_video_comment).setOnClickListener(this);
        findViewById(R.id.btn_comment).setOnClickListener(this);
        findViewById(R.id.iv_video_more).setOnClickListener(this);
        findViewById(R.id.iv_video_close).setOnClickListener(this);
        findViewById(R.id.btn_cai).setOnClickListener(this);
        findViewById(R.id.iv_video_share).setOnClickListener(this);
        findViewById(R.id.ll_live_room_info).setOnClickListener(this);
        findViewById(R.id.iv_live_emcee_head).setOnClickListener(this);
        findViewById(R.id.id_video_view).setOnClickListener(mClickListener);
        findViewById(R.id.tv_attention).setOnClickListener(this);
        findViewById(R.id.iv_video_laud).setOnClickListener(this);
    }


    protected boolean mPausing = false;

    public boolean mIsRegisted;

    public boolean mIsConnected;

    boolean isRequst;

    private TXLivePlayer mTXLivePlayer;

    private TXLivePlayConfig mTXPlayConfig = new TXLivePlayConfig();

    private String mPlayUrl = "http://2527.vod.myqcloud.com/2527_000007d04afea41591336f60841b5774dcfd0001.f0.flv";

    private boolean mPlaying = false;

    private int mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;

    protected boolean mIsLivePlay = true;


    private CommentFragment mCommentFragment;


    private UserInfo mUserInfo;

    ActiveBean videoBean = new ActiveBean();

    String uid;

    private Handler mHandler;

    boolean isShowGif = true;

    private UserInfoDialogFragment mUserInfoDialog;

    private int mScreenHeight;

    private static EMChatManager mChatManager;

    SimpleUserInfo mEmceeInfo = new SimpleUserInfo();

    private CommentDialogFragment mDialogFragment;

    private int mIsLike = -1;

    private MediaMetadataRetriever mmr;
    protected boolean hasActionBar() {
        return true;
    }

    private void setPagePosition() {
        if (mPagePosition <= 0) {
            mPagePosition = 2;
        }
        if (mPagePosition >= mList.size() - 1) {
            mPagePosition = 0;
        }
        mCurrentItem = mPagePosition = mPagePosition - 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_video_player2_layout);
        mViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);
        findView();


        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_room_container, null);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);

        mList=getListData();

//        initData(getPosition());

        mFragmentManager = getSupportFragmentManager();
        mPagerAdapter = new PagerAdapter();
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(mSmallVideoFragment!=null) {
                    mSmallVideoFragment.setTitleMediaController();
                }
                mCurrentItem=position;

            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;
                if ((position < 0 && viewGroup.getId() != mCurrentItem)) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }

                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                TLog.log("getId---->" + viewGroup.getId() + "  mCurrId--->" + mCurrentItem + "  mRoomId----->" + mRoomId);
                if (viewGroup.getId() == mCurrentItem && mCurrentItem != mRoomId) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup, mCurrentItem);
                }else if(!mInit&& !isInit){
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup, mCurrentItem);
                }
            }
        });

        mPagePosition = getPosition();
        mRoomId = getPosition();
        TLog.log("mRoomId------>" + mRoomId);
        mViewPager.setAdapter(mPagerAdapter);


    }

    public ArrayList<ActiveBean> getListData() {
        mBundle = getIntent().getBundleExtra(SmallVideoFragment.USER_INFO);
        ArrayList<ActiveBean> mList = mBundle.getParcelableArrayList(SmallVideoFragment.USER_INFO);
        TLog.log("接收的长度------>"+mList.size());
        return mList;
    }


    public int getPosition() {
        int position = getIntent().getIntExtra("position", 1);
        TLog.log("接收的position--->" + position);
        return position;
    }

    public int getCurrPosition() {
        return mCurrentItem;
    }

    public boolean getIsInit() {
        return mInit;
    }

    public static void startSmallVideoPlayerActivity(final Context context, final ArrayList<ActiveBean> mUserList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("USER_INFO", mUserList);
        UIHelper.showSmallLookLiveActivity2(context, bundle);
    }

    /**
     * @param viewGroup
     * @param currentItem
     */
    private void loadVideoAndChatRoom(ViewGroup viewGroup, int currentItem) {
//        mSubscription = AppObservable.bindActivity(this, ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
        //聊天室的fragment只加载一次，以后复用
        if (!mInit && !isInit) {
            addFragment();
            mInit = true;
            isInit = true;
            mCurrentItem = getPosition();
            TLog.log("第一次加载----->" + currentItem);
        } else {
            TLog.log("第二次加载------->" + currentItem);
            mInit = false;
//            if(getPosition()==mList.size()-1){
//                int position=getPosition();
//                mCurrentItem=position-1;
//            }else{
//            }
            mCurrentItem = currentItem;
            addFragment();
        }

        TLog.log("滑动视频------>");

        viewGroup.addView(mRoomContainer);
        mRoomId = mCurrentItem;


        initData(mCurrentItem);

    }

    private boolean isInit;

    private void addFragment() {
        mSmallVideoFragment = new SmallVideoFragment();
        mSmallVideoFragment.setListener(this);
        mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mSmallVideoFragment).commitAllowingStateLoss();
    }

    @Override
    public void delete() {
        mSmallVideoFragment.delete();
    }

    @Override
    public void loadingListener() {
        if (mIvLoadingBg != null) {
            mIvLoadingBg.setVisibility(View.GONE);
        }
    }


    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_room_item, null);
            view.setId(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(container.findViewById(position));
        }
    }

    @Override
    protected void onDestroy() {
        mInit = false;
        isInit = false;
        //解除广播
        OkHttpUtils.getInstance().cancelTag("initRoomInfo");
        ButterKnife.reset(this);

        super.onDestroy();
    }



    private void initData(int currItem) {

//        mList = getArguments().getParcelableArrayList(USER_INFO);
        videoBean = mList.get(currItem);
        if (videoBean == null) {
            return;
        }

        mUserInfo = videoBean.getUserinfo();

        mTvVideoTitle.setVisibility(View.VISIBLE);
        mTvVideoTitle.setText(videoBean.getTitle());

        mEmceeInfo.id = videoBean.getUid();

        mEmceeInfo.user_nicename = videoBean.getUserinfo().getUser_nicename();


        mUName.setText(mEmceeInfo.user_nicename);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (mIvGif != null && mIvGif.getVisibility() == View.VISIBLE) {
                    mIvGif.setVisibility(View.GONE);
                }
            }
        };

        uid = AppContext.getInstance().getLoginUid();

        mChatManager = EMClient.getInstance().chatManager();

//        ((SmallVideoPlayer2Activity) mActivity).setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //初始化房间信息
        initRoomInfo();
        mUserInfoDialog = new UserInfoDialogFragment();

        PhoneLiveApi.getVideoInfo(videoBean.getId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("200".equals(obj.getString("ret"))) {
                        JSONObject data = obj.getJSONObject("data");
                        if (0 == data.getInt("code")) {
                            JSONObject info0 = data.getJSONArray("info").getJSONObject(0);
                            mIsLike = info0.getInt("islike");
                            if (1 == mIsLike) {
                                mIvLaud.setBackgroundResource(R.drawable.lauded);
                            }
                            if (info0.getInt("isattent") == 1) {
                                mIvAttention.setVisibility(View.GONE);
                            }
                            String comments = info0.getString("comments");
                            String shares = info0.getString("shares");
                            String likes = info0.getString("likes");
                            mTvLaudNum.setText(likes);
                            mTvCommentNum.setText(comments);
                            mShareCount.setText(shares);
                        }
                    } else {
                        AppContext.toast("获取数据失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setShareCount(String s) {
        mShareCount.setText(s);
    }

    public static void setCommentNum(String s) {
        mTvCommentNum.setText(s);
    }

    private void initRoomInfo() {

        //设置背景图
        mIvLoadingBg.setVisibility(View.VISIBLE);
        TLog.log("背景图片url---->"+videoBean.getThumb());
        mIvLoadingBg.setImageLoadUrl(videoBean.getThumb());

        mAvEmcee.setAvatarUrl(mUserInfo.getAvatar());

        // mTvCommentNum.setText(videoBean.getComments());

        //  mTvLaudNum.setText(videoBean.getLikes());

        // mShareCount.setText(videoBean.getShares());

        mTitle.setText(videoBean.getTitle());

        if (!uid.equals(videoBean.getUid())) {
            if ("1".equals(videoBean.getIslike())) {
                mIvLaud.setBackgroundResource(R.drawable.lauded);
            }

            if ("1".equals(videoBean.getIsattent())) {
                mIvAttention.setVisibility(View.GONE);
            }
            if (1 == videoBean.getIsstep()) {
                mCai.setImageResource(R.drawable.icon_video_cai_selected);
            }
        } else {
            mIvAttention.setVisibility(View.GONE);
            mCai.setVisibility(View.GONE);
        }
        mPlayUrl = videoBean.getHref();

    }


    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mIsLike == 0) {
                addLikes();
            }
            showLaudGif();
        }
    };

    private void showLaudGif() {
        if (mIvGif.getVisibility() == View.GONE) {
            mIvGif.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.laud_gif).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvGif);
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    public void titleVisible(int type) {
        if(type==1) {
            int visibility = mTvVideoTitle.getVisibility();
            if (visibility == View.VISIBLE) {
                mTvVideoTitle.setVisibility(View.GONE);
            } else {
                mTvVideoTitle.setVisibility(View.VISIBLE);
            }
        }else{
            mTvVideoTitle.setVisibility(View.GONE);
        }
    }

    private void addLikes() {
        PhoneLiveApi.addLike(uid, videoBean.getId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                JSONArray res = ApiUtils.checkIsSuccess(response);
                if (res != null) {
                    try {
                        if (videoBean != null) {
                            videoBean.setIslike(res.getJSONObject(0).getString("islike"));
                            videoBean.setLikes(res.getJSONObject(0).getString("likes"));
                        }
                        if (mTvLaudNum != null) {
                            mTvLaudNum.setText(videoBean.getLikes());
                        }
                        if (mIvLaud != null) {
                            mIsLike = res.getJSONObject(0).getInt("islike");
                            if (mIsLike == 1) {
                                mIvLaud.setBackgroundResource(R.drawable.lauded);
                            } else {
                                mIvLaud.setBackgroundResource(R.drawable.nolaud);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //评论
    private void showCommentDialog() {
        mCommentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean", videoBean);
        mCommentFragment.setArguments(bundle);
        mCommentFragment.show(getSupportFragmentManager(), "CommentFragment");
    }


    //评论
    private void showCommentDialog2() {
        if (mDialogFragment == null) {
            mDialogFragment = new CommentDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("bean", videoBean);
            mDialogFragment.setArguments(bundle);
            TLog.log("弹窗1111111111111111");
        }
        TLog.log("弹框昵称---->"+videoBean.getUserinfo().getUser_nicename());
        mDialogFragment.show(getSupportFragmentManager(), "CommentDialogFragment");
    }

    //视频结束释放资源
    private void videoPlayerEnd() {
        if (mTXLivePlayer != null) {
            mTXLivePlayer.setPlayListener(null);
            mTXLivePlayer.stopPlay(true);
            mTXLivePlayer = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView = null;
        }
        if (mIvLoadingBg != null) {
            mIvLoadingBg = null;
        }
        if (mCommentFragment != null) {
            mCommentFragment = null;
        }


    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListenernew = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            //获取当前界面可视部分
//            ((SmallVideoPlayer2Activity)mActivity).getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            //获取屏幕的高度
            if (mScreenHeight == 0) {
                mScreenHeight = r.height();
            }
            int visibleHeight = r.height();
            if (visibleHeight == mScreenHeight) {
                if (mCommentFragment != null) {
                    mCommentFragment.onSoftInputHide();
                }
            } else {
                if (mCommentFragment != null) {
                    mCommentFragment.onSoftInputShow(visibleHeight);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_live_emcee_head://左上角点击主播信息
                UIHelper.showHomePageActivity(SmallVideoPlayer2Activity.this, videoBean.getUid());
                break;
            case R.id.ll_live_room_info:
                break;
            case R.id.tv_attention:
                //关注主播
                PhoneLiveApi.showFollow(AppContext.getInstance().getLoginUid(), videoBean.getUid(), AppContext.getInstance().getToken(), new PhoneLiveApi.AttentionCallback() {
                    @Override
                    public void callback(boolean isAttention) {
                        mIvAttention.setVisibility(View.GONE);
                        Toast.makeText(SmallVideoPlayer2Activity.this, "关注成功", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.iv_video_comment:
                showCommentDialog();
                break;
            case R.id.btn_comment:
                showCommentDialog2();
                break;
            case R.id.iv_video_share:
            case R.id.iv_video_more:
                // showSharePopWindow(SmallVideoPlayerActivity.this, v, mEmceeInfo);
                showSharePopWindow2();
                break;
            case R.id.iv_video_laud:
                if (mIsLike == 0) {
                    showLaudGif();
                }
                addLikes();
                break;
            case R.id.iv_video_close:
                finish();
                break;
            case R.id.btn_cai:
                cai();
                break;
        }
    }

    private void cai() {
        if (videoBean.getIsstep() == 1) {
            return;
        }

        PhoneLiveApi.addVideoStep(videoBean.getId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                TLog.log("踩失败了----->"+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                TLog.log("踩成功了------>"+response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if ("200".equals(obj.getString("ret"))) {
                        JSONObject data = obj.getJSONObject("data");
                        if (0 == data.getInt("code")) {
                            int isstep = data.getJSONArray("info").getJSONObject(0).getInt("isstep");
                            if (isstep == 1) {
                                videoBean.setIsstep(1);
                                mCai.setImageResource(R.drawable.icon_video_cai_selected);
                            }
                        }
                        AppContext.toast(data.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *
     * @param context
     * @param live
     */
    public static void startSmallVideoPlayerActivity(final Context context, final ActiveBean live) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER_INFO", live);
        UIHelper.showSmallLookLiveActivity(context, bundle);
    }

    /**
     *
     * @param isfollow
     * @param content
     * @param touid
     */
    public static void sendEMMessage(String isfollow, String content, String touid) {
        EMMessage message = EMMessage.createTxtSendMessage(content, touid);
        message.setAttribute("isfollow", isfollow);
        mChatManager.sendMessage(message);
    }


    private void showReportDialog() {
        final Dialog dialog = new Dialog(SmallVideoPlayer2Activity.this, R.style.dialog_no_background);
        dialog.setContentView(R.layout.dialog_report);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width = (int) getResources().getDisplayMetrics().widthPixels - (int) TDevice.dpToPixel(15); // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
        dialogWindow.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout mLlReport = (LinearLayout) dialog.findViewById(R.id.ll_video_report);
        TextView mTvReport = (TextView) dialog.findViewById(R.id.tv_type);
        if (uid.equals(videoBean.getUid())) {
            mTvReport.setText("删除视频");
        } else {
            mTvReport.setText("举报该视频");
        }
        LinearLayout mLlCancel = (LinearLayout) dialog.findViewById(R.id.ll_viedo_cancel);
        mLlReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid.equals(videoBean.getUid())) {
                    PhoneLiveApi.setVideoRel(uid, AppContext.getInstance().getToken(), videoBean.getId(), new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            JSONArray res = ApiUtils.checkIsSuccess(response);
                            if (res != null) {
                                dialog.dismiss();
                                Toast.makeText(SmallVideoPlayer2Activity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    PhoneLiveApi.setVideoReport(uid, AppContext.getInstance().getToken(), videoBean.getId(), new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            JSONArray res = ApiUtils.checkIsSuccess(response);
                            if (res != null) {
                                Toast.makeText(SmallVideoPlayer2Activity.this, "感谢您的举报,我们会尽快做出处理...", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
        mLlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //分享pop弹窗
    private void showSharePopWindow(final Context context, View v, final SimpleUserInfo mUser) {

        View view = LayoutInflater.from(context).inflate(R.layout.pop_view_share, null);
        PopupWindow p = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        p.setBackgroundDrawable(new BitmapDrawable());
        p.setOutsideTouchable(true);
        LinearLayout mLlShare = (LinearLayout) view.findViewById(R.id.ll_live_shar);
        for (int i = 0; i < AppConfig.SHARE_TYPE.length(); i++) {
            final ImageView im = new ImageView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) TDevice.dpToPixel(40), (int) TDevice.dpToPixel(60));
            if (i > 0)
                lp.setMargins((int) TDevice.dpToPixel(15), 0, 0, 0);
            im.setLayoutParams(lp);
            try {
                im.setImageResource(context.getResources().getIdentifier(AppConfig.SHARE_TYPE.getString(i) + "_share", "drawable", "com.duomizhibo.phonelive"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mLlShare.addView(im);
            final int finalI = i;
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtils.share((Activity) context, finalI, mUser);
                }
            });
        }

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //p.showAtLocation(v, Gravity.NO_GRAVITY,location[0] + v.getWidth()/2 - view.getMeasuredWidth()/2,location[1]- view.getMeasuredHeight());
        p.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    private void showSharePopWindow2() {
        VideoShareFragment f = new VideoShareFragment(this);
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean", videoBean);
        f.setArguments(bundle);
        f.show(getSupportFragmentManager(), "VideoShareFragment");
    }

    private Dialog mLoadingDialog;



}
