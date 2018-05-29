package com.duomizhibo.phonelive.model;

import java.util.List;

/**
 * Created by chawei on 2018/5/29.
 */

public class CommentModel {

    /**
     * ret : 200
     * data : {"code":0,"msg":"","info":[{"id":"1","site_url":"http://live.lottery163.com/api/public/","apk_ver":"2.2.4","apk_url":"https://xxx","apk_des":"1","sitename":"小视频","wx_siteurl":"http://live.lottery163.com/wxshare/index.php/Share/show?roomnum=","app_android":"http://live.lottery163.com/dmad","app_ios":"http://live.lottery163.com/dmios","qr_url":"http://zqcf-img.qiniudn.com/20180528_5b0bc65d9304d.jpg","ipa_ver":"11.4","ipa_url":"https://xxxx","ipa_des":"","site":"http://live.lottery163.com","live_width":"450","live_height":"800","keyframe":"15","fps":"30","quality":"95","more_img":"","pub_msg":"","lotterybase":"1000000000","topic_num":"5","ex_rate":"9","share_title":"我在这里直播，大家都来观看哦","share_des":"正在直播，快来一起看呀","ios_shelves":"1.1","name_coin":"钻石","name_votes":"RMB","enter_tip_level":"1232","maintain_switch":"0","maintain_tips":"维护通知：为了更好的为您服务，本站正在升级维护中，因此带来不便深表歉意\n","copyright":"小视频","live_time_coin":["1","2","3","4","5","6","7","8","9","10"],"login_type":[],"live_type":[["0","普通房间"],["1","密码房间"],["2","门票房间"],["3","计时房间"],["6","私播房间"]],"share_type":["qq","qzone","wx","wchat"],"video_share_title":"拍视频，有颜有料上热门","video_share_des":"发布了视频，快来一起看呀","sendmessage_level":"1","video_comment_list":["1. 这家技术太高，我们换一家喷。","2. 你的笔很听话，我的笔有自己的想法。 ","3. 欢迎收看一看就会，一做就废系列。","4. 你弄这么快，是怕我学会么","5. 明人不说暗话，我喜欢 那个小姐姐 。","6. 本以为是青铜，没想到是王者","7. 这是我在抖音想买的第多少件东西","8. 听最嗨的歌，喝最烈的酒，打最好的石膏。"],"privatelive_switch":"1"}]}
     * msg :
     */

    private int ret;
    private DataBean data;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * code : 0
         * msg :
         * info : [{"id":"1","site_url":"http://live.lottery163.com/api/public/","apk_ver":"2.2.4","apk_url":"https://xxx","apk_des":"1","sitename":"小视频","wx_siteurl":"http://live.lottery163.com/wxshare/index.php/Share/show?roomnum=","app_android":"http://live.lottery163.com/dmad","app_ios":"http://live.lottery163.com/dmios","qr_url":"http://zqcf-img.qiniudn.com/20180528_5b0bc65d9304d.jpg","ipa_ver":"11.4","ipa_url":"https://xxxx","ipa_des":"","site":"http://live.lottery163.com","live_width":"450","live_height":"800","keyframe":"15","fps":"30","quality":"95","more_img":"","pub_msg":"","lotterybase":"1000000000","topic_num":"5","ex_rate":"9","share_title":"我在这里直播，大家都来观看哦","share_des":"正在直播，快来一起看呀","ios_shelves":"1.1","name_coin":"钻石","name_votes":"RMB","enter_tip_level":"1232","maintain_switch":"0","maintain_tips":"维护通知：为了更好的为您服务，本站正在升级维护中，因此带来不便深表歉意\n","copyright":"小视频","live_time_coin":["1","2","3","4","5","6","7","8","9","10"],"login_type":[],"live_type":[["0","普通房间"],["1","密码房间"],["2","门票房间"],["3","计时房间"],["6","私播房间"]],"share_type":["qq","qzone","wx","wchat"],"video_share_title":"拍视频，有颜有料上热门","video_share_des":"发布了视频，快来一起看呀","sendmessage_level":"1","video_comment_list":["1. 这家技术太高，我们换一家喷。","2. 你的笔很听话，我的笔有自己的想法。 ","3. 欢迎收看一看就会，一做就废系列。","4. 你弄这么快，是怕我学会么","5. 明人不说暗话，我喜欢 那个小姐姐 。","6. 本以为是青铜，没想到是王者","7. 这是我在抖音想买的第多少件东西","8. 听最嗨的歌，喝最烈的酒，打最好的石膏。"],"privatelive_switch":"1"}]
         */

        private int code;
        private String msg;
        private List<InfoBean> info;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * id : 1
             * site_url : http://live.lottery163.com/api/public/
             * apk_ver : 2.2.4
             * apk_url : https://xxx
             * apk_des : 1
             * sitename : 小视频
             * wx_siteurl : http://live.lottery163.com/wxshare/index.php/Share/show?roomnum=
             * app_android : http://live.lottery163.com/dmad
             * app_ios : http://live.lottery163.com/dmios
             * qr_url : http://zqcf-img.qiniudn.com/20180528_5b0bc65d9304d.jpg
             * ipa_ver : 11.4
             * ipa_url : https://xxxx
             * ipa_des :
             * site : http://live.lottery163.com
             * live_width : 450
             * live_height : 800
             * keyframe : 15
             * fps : 30
             * quality : 95
             * more_img :
             * pub_msg :
             * lotterybase : 1000000000
             * topic_num : 5
             * ex_rate : 9
             * share_title : 我在这里直播，大家都来观看哦
             * share_des : 正在直播，快来一起看呀
             * ios_shelves : 1.1
             * name_coin : 钻石
             * name_votes : RMB
             * enter_tip_level : 1232
             * maintain_switch : 0
             * maintain_tips : 维护通知：为了更好的为您服务，本站正在升级维护中，因此带来不便深表歉意

             * copyright : 小视频
             * live_time_coin : ["1","2","3","4","5","6","7","8","9","10"]
             * login_type : []
             * live_type : [["0","普通房间"],["1","密码房间"],["2","门票房间"],["3","计时房间"],["6","私播房间"]]
             * share_type : ["qq","qzone","wx","wchat"]
             * video_share_title : 拍视频，有颜有料上热门
             * video_share_des : 发布了视频，快来一起看呀
             * sendmessage_level : 1
             * video_comment_list : ["1. 这家技术太高，我们换一家喷。","2. 你的笔很听话，我的笔有自己的想法。 ","3. 欢迎收看一看就会，一做就废系列。","4. 你弄这么快，是怕我学会么","5. 明人不说暗话，我喜欢 那个小姐姐 。","6. 本以为是青铜，没想到是王者","7. 这是我在抖音想买的第多少件东西","8. 听最嗨的歌，喝最烈的酒，打最好的石膏。"]
             * privatelive_switch : 1
             */

            private String id;
            private String site_url;
            private String apk_ver;
            private String apk_url;
            private String apk_des;
            private String sitename;
            private String wx_siteurl;
            private String app_android;
            private String app_ios;
            private String qr_url;
            private String ipa_ver;
            private String ipa_url;
            private String ipa_des;
            private String site;
            private String live_width;
            private String live_height;
            private String keyframe;
            private String fps;
            private String quality;
            private String more_img;
            private String pub_msg;
            private String lotterybase;
            private String topic_num;
            private String ex_rate;
            private String share_title;
            private String share_des;
            private String ios_shelves;
            private String name_coin;
            private String name_votes;
            private String enter_tip_level;
            private String maintain_switch;
            private String maintain_tips;
            private String copyright;
            private String video_share_title;
            private String video_share_des;
            private String sendmessage_level;
            private String privatelive_switch;
            private List<String> live_time_coin;
            private List<?> login_type;
            private List<List<String>> live_type;
            private List<String> share_type;
            private List<String> video_comment_list;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSite_url() {
                return site_url;
            }

            public void setSite_url(String site_url) {
                this.site_url = site_url;
            }

            public String getApk_ver() {
                return apk_ver;
            }

            public void setApk_ver(String apk_ver) {
                this.apk_ver = apk_ver;
            }

            public String getApk_url() {
                return apk_url;
            }

            public void setApk_url(String apk_url) {
                this.apk_url = apk_url;
            }

            public String getApk_des() {
                return apk_des;
            }

            public void setApk_des(String apk_des) {
                this.apk_des = apk_des;
            }

            public String getSitename() {
                return sitename;
            }

            public void setSitename(String sitename) {
                this.sitename = sitename;
            }

            public String getWx_siteurl() {
                return wx_siteurl;
            }

            public void setWx_siteurl(String wx_siteurl) {
                this.wx_siteurl = wx_siteurl;
            }

            public String getApp_android() {
                return app_android;
            }

            public void setApp_android(String app_android) {
                this.app_android = app_android;
            }

            public String getApp_ios() {
                return app_ios;
            }

            public void setApp_ios(String app_ios) {
                this.app_ios = app_ios;
            }

            public String getQr_url() {
                return qr_url;
            }

            public void setQr_url(String qr_url) {
                this.qr_url = qr_url;
            }

            public String getIpa_ver() {
                return ipa_ver;
            }

            public void setIpa_ver(String ipa_ver) {
                this.ipa_ver = ipa_ver;
            }

            public String getIpa_url() {
                return ipa_url;
            }

            public void setIpa_url(String ipa_url) {
                this.ipa_url = ipa_url;
            }

            public String getIpa_des() {
                return ipa_des;
            }

            public void setIpa_des(String ipa_des) {
                this.ipa_des = ipa_des;
            }

            public String getSite() {
                return site;
            }

            public void setSite(String site) {
                this.site = site;
            }

            public String getLive_width() {
                return live_width;
            }

            public void setLive_width(String live_width) {
                this.live_width = live_width;
            }

            public String getLive_height() {
                return live_height;
            }

            public void setLive_height(String live_height) {
                this.live_height = live_height;
            }

            public String getKeyframe() {
                return keyframe;
            }

            public void setKeyframe(String keyframe) {
                this.keyframe = keyframe;
            }

            public String getFps() {
                return fps;
            }

            public void setFps(String fps) {
                this.fps = fps;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public String getMore_img() {
                return more_img;
            }

            public void setMore_img(String more_img) {
                this.more_img = more_img;
            }

            public String getPub_msg() {
                return pub_msg;
            }

            public void setPub_msg(String pub_msg) {
                this.pub_msg = pub_msg;
            }

            public String getLotterybase() {
                return lotterybase;
            }

            public void setLotterybase(String lotterybase) {
                this.lotterybase = lotterybase;
            }

            public String getTopic_num() {
                return topic_num;
            }

            public void setTopic_num(String topic_num) {
                this.topic_num = topic_num;
            }

            public String getEx_rate() {
                return ex_rate;
            }

            public void setEx_rate(String ex_rate) {
                this.ex_rate = ex_rate;
            }

            public String getShare_title() {
                return share_title;
            }

            public void setShare_title(String share_title) {
                this.share_title = share_title;
            }

            public String getShare_des() {
                return share_des;
            }

            public void setShare_des(String share_des) {
                this.share_des = share_des;
            }

            public String getIos_shelves() {
                return ios_shelves;
            }

            public void setIos_shelves(String ios_shelves) {
                this.ios_shelves = ios_shelves;
            }

            public String getName_coin() {
                return name_coin;
            }

            public void setName_coin(String name_coin) {
                this.name_coin = name_coin;
            }

            public String getName_votes() {
                return name_votes;
            }

            public void setName_votes(String name_votes) {
                this.name_votes = name_votes;
            }

            public String getEnter_tip_level() {
                return enter_tip_level;
            }

            public void setEnter_tip_level(String enter_tip_level) {
                this.enter_tip_level = enter_tip_level;
            }

            public String getMaintain_switch() {
                return maintain_switch;
            }

            public void setMaintain_switch(String maintain_switch) {
                this.maintain_switch = maintain_switch;
            }

            public String getMaintain_tips() {
                return maintain_tips;
            }

            public void setMaintain_tips(String maintain_tips) {
                this.maintain_tips = maintain_tips;
            }

            public String getCopyright() {
                return copyright;
            }

            public void setCopyright(String copyright) {
                this.copyright = copyright;
            }

            public String getVideo_share_title() {
                return video_share_title;
            }

            public void setVideo_share_title(String video_share_title) {
                this.video_share_title = video_share_title;
            }

            public String getVideo_share_des() {
                return video_share_des;
            }

            public void setVideo_share_des(String video_share_des) {
                this.video_share_des = video_share_des;
            }

            public String getSendmessage_level() {
                return sendmessage_level;
            }

            public void setSendmessage_level(String sendmessage_level) {
                this.sendmessage_level = sendmessage_level;
            }

            public String getPrivatelive_switch() {
                return privatelive_switch;
            }

            public void setPrivatelive_switch(String privatelive_switch) {
                this.privatelive_switch = privatelive_switch;
            }

            public List<String> getLive_time_coin() {
                return live_time_coin;
            }

            public void setLive_time_coin(List<String> live_time_coin) {
                this.live_time_coin = live_time_coin;
            }

            public List<?> getLogin_type() {
                return login_type;
            }

            public void setLogin_type(List<?> login_type) {
                this.login_type = login_type;
            }

            public List<List<String>> getLive_type() {
                return live_type;
            }

            public void setLive_type(List<List<String>> live_type) {
                this.live_type = live_type;
            }

            public List<String> getShare_type() {
                return share_type;
            }

            public void setShare_type(List<String> share_type) {
                this.share_type = share_type;
            }

            public List<String> getVideo_comment_list() {
                return video_comment_list;
            }

            public void setVideo_comment_list(List<String> video_comment_list) {
                this.video_comment_list = video_comment_list;
            }
        }
    }
}
