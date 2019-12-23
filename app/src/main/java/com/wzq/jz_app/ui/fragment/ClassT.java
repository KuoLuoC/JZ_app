package com.wzq.jz_app.ui.fragment;

import java.util.List;

/**
 * 作者：wzq on 2019/4/19.
 * 邮箱：wang_love152@163.com
 */


public class ClassT {


    /**
     * liveInfo : null
     * tcount : 73
     * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.ws.126.net/2019/04/03/bc7e41d38bc541bdb7e1c5e9790df728.png","height":null}]
     * docid : EBRG1M4P00097U7T
     * videoInfo : null
     * channel : tech
     * link : https://3g.163.com/all/article/EBRG1M4P00097U7T.html
     * source : 观察者网
     * title : 苹果向三星采购5G基带芯片 后者拒绝:产能不足
     * type : doc
     * imgsrcFrom : null
     * imgsrc3gtype : 1
     * unlikeReason : 重复、旧闻/6,内容质量差/6
     * isTop : null
     * digest : 【文/观察者网徐乾昂】一边是决定信号质量的基带芯片，另一边是
     * typeid :
     * addata : null
     * tag :
     * category : 科技
     * ptime : 2019-04-03 13:44:59
     */

    private Object liveInfo;
    private int tcount;
    private String docid;
    private Object videoInfo;
    private String channel;
    private String link;
    private String source;
    private String title;
    private String type;
    private Object imgsrcFrom;
    private int imgsrc3gtype;
    private String unlikeReason;
    private Object isTop;
    private String digest;
    private String typeid;
    private Object addata;
    private String tag;
    private String category;
    private String ptime;
    private List<PicInfoBean> picInfo;

    public Object getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(Object liveInfo) {
        this.liveInfo = liveInfo;
    }

    public int getTcount() {
        return tcount;
    }

    public void setTcount(int tcount) {
        this.tcount = tcount;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public Object getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(Object videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getImgsrcFrom() {
        return imgsrcFrom;
    }

    public void setImgsrcFrom(Object imgsrcFrom) {
        this.imgsrcFrom = imgsrcFrom;
    }

    public int getImgsrc3gtype() {
        return imgsrc3gtype;
    }

    public void setImgsrc3gtype(int imgsrc3gtype) {
        this.imgsrc3gtype = imgsrc3gtype;
    }

    public String getUnlikeReason() {
        return unlikeReason;
    }

    public void setUnlikeReason(String unlikeReason) {
        this.unlikeReason = unlikeReason;
    }

    public Object getIsTop() {
        return isTop;
    }

    public void setIsTop(Object isTop) {
        this.isTop = isTop;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public Object getAddata() {
        return addata;
    }

    public void setAddata(Object addata) {
        this.addata = addata;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<PicInfoBean> getPicInfo() {
        return picInfo;
    }

    public void setPicInfo(List<PicInfoBean> picInfo) {
        this.picInfo = picInfo;
    }

    public static class PicInfoBean {
        /**
         * ref : null
         * width : null
         * url : http://cms-bucket.ws.126.net/2019/04/03/bc7e41d38bc541bdb7e1c5e9790df728.png
         * height : null
         */

        private Object ref;
        private Object width;
        private String url;
        private Object height;

        public Object getRef() {
            return ref;
        }

        public void setRef(Object ref) {
            this.ref = ref;
        }

        public Object getWidth() {
            return width;
        }

        public void setWidth(Object width) {
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getHeight() {
            return height;
        }

        public void setHeight(Object height) {
            this.height = height;
        }
    }
}
