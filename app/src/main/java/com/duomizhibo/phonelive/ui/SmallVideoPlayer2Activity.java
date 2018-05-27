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
import com.duomizhibo.phonelive.utils.UIHelper;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by chawei on 2018/5/27.
 */

public class SmallVideoPlayer2Activity  extends AppCompatActivity{


    private SmallVideoFragment mSmallVideoFragment = SmallVideoFragment.newInstance();
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

    protected boolean hasActionBar() {
        return true;
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
//                TLog.log("mCurrentId == " + position + ", positionOffset == " + positionOffset +
//                        ", positionOffsetPixels == " + positionOffsetPixels);
                mCurrentItem = position;
            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;
//                TLog.log( "page.id == " + page.getId() + ", position == " + position);

                if ((position < 0 && viewGroup.getId() != mCurrentItem)) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }
                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                if (viewGroup.getId() == mCurrentItem && position == 0 && mCurrentItem != mRoomId) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup, mCurrentItem);
                }
            }
        });
        mViewPager.setAdapter(mPagerAdapter);


        Bundle bundle = getIntent().getBundleExtra(SmallVideoFragment.USER_INFO);
        ArrayList<ActiveBean> mList= bundle.getParcelableArrayList(SmallVideoFragment.USER_INFO);
        bundle.putParcelableArrayList(SmallVideoFragment.USER_INFO,mList);
        mSmallVideoFragment.setArguments(bundle);
    }

    public static void startSmallVideoPlayerActivity(final Context context, final ArrayList<ActiveBean> mUserList) {
        mList=mUserList;
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
        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mSmallVideoFragment).commitAllowingStateLoss();
            mSmallVideoFragment.initData(currentItem);
            mInit = true;
        }
        viewGroup.addView(mRoomContainer);
        mRoomId = currentItem;
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
}
