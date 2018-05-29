package com.duomizhibo.phonelive.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.duomizhibo.phonelive.R;
import com.duomizhibo.phonelive.bean.ActiveBean;
import com.duomizhibo.phonelive.fragment.SmallVideoFragment;
import com.duomizhibo.phonelive.utils.TLog;
import com.duomizhibo.phonelive.utils.UIHelper;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by chawei on 2018/5/27.
 */

public class SmallVideoPlayer2Activity extends AppCompatActivity {


    //    private SmallVideoFragment mSmallVideoFragment = SmallVideoFragment.newInstance();
    private SmallVideoFragment mSmallVideoFragment;
    private static ArrayList<ActiveBean> mList = new ArrayList<>();
    private boolean mInit = false;
    private FragmentManager mFragmentManager;
    private RelativeLayout mRoomContainer;
    private FrameLayout mFragmentContainer;
    private TXCloudVideoView mVideoView;
    private PagerAdapter mPagerAdapter;
    private int mCurrentItem;
    private VerticalViewPager mViewPager;
    private int mRoomId = -1;
    private Bundle mBundle;
    private int mPagePosition;

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


        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_room_container, null);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        mVideoView = (TXCloudVideoView) mRoomContainer.findViewById(R.id.texture_view);


        mFragmentManager = getSupportFragmentManager();
        mPagerAdapter = new PagerAdapter();
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
                }
            }
        });

        mRoomId = getPosition();
        mPagePosition = getPosition();
        TLog.log("mRoomId------>" + mRoomId);
        mViewPager.setAdapter(mPagerAdapter);

    }

    public ArrayList<ActiveBean> getListData() {
        mBundle = getIntent().getBundleExtra(SmallVideoFragment.USER_INFO);
        ArrayList<ActiveBean> mList = mBundle.getParcelableArrayList(SmallVideoFragment.USER_INFO);
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
        mList = mUserList;
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
    }

    private boolean isInit;

    private void addFragment() {
        mSmallVideoFragment = new SmallVideoFragment();
        mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mSmallVideoFragment).commitAllowingStateLoss();
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
        super.onDestroy();
    }
}
