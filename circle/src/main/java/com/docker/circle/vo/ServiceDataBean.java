package com.docker.circle.vo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServiceDataBean extends BaseObservable implements Serializable {



    private String country;
    private String province;
    private String city;
    private String district;
    public String autotrophy;   //1 自营


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    private String dynamicid;
    private String memberid;
    private String uuid;
    private String circleid;
    private String utid;
    private String type;


    @Bindable
    private int favourNum = 0;
    private String commentNum;
    private String viewNum;
    private String istop;
    private String inputtime;
    private String classid;
    private String classid2;
    private String dataid;
    private String avatar;
    private String nickname;
    private String circleName;
    private ExtDataBean extData;

    public String area1;
    public String area2;
    public String area3;

    private String employeeNum;

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    @Bindable
    private int isFav;
    private ShareBean share;
    private List<FavsUsersBean> favsUsers;
    private List<CommentUsersBean> commentUsers;
    private String content;

    private List<ResourceBean> resource;

    public List<ResourceBean> getResource() {
        return resource;
    }

    public void setResource(List<ResourceBean> resource) {
        this.resource = resource;
    }

    /**
     * 图书列表
     * <p>
     * bookid : 1
     * name : 好的好多话没有
     * thumb : /var/upload/image/2018/08/2018081516114554050_250x149.png
     * publishing : jfjdj
     * publish_time : 1534320658.251608
     * pricing : 100.00
     * discount : 9.9
     * companyName : 好的好的好
     */

    private String bookid;
    private String name;
    private String thumb;
    private String publishing;
    private String publish_time;
    private String pricing;
    private String discount;
    private String companyName;


    // 动态详情 -------------------------------------------
    private String seeNum = "";//浏览量
    private String cityName;

    @Bindable
    private int isFocus;


    @Bindable
    private int isCollect;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Bindable
    public int getIsCollect() {

        return isCollect;
    }

    @Bindable
    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(String seeNum) {
        this.seeNum = seeNum;
    }


    @Bindable
    public int getIsFocus() {
        return isFocus;
    }

    @Bindable
    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
    //------------------------------------------------------


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bindable
    public int getFavourNum() {
        return favourNum;
    }

    @Bindable
    public void setFavourNum(int favourNum) {
        this.favourNum = favourNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getIstop() {
        return istop;
    }

    public void setIstop(String istop) {
        this.istop = istop;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid2() {
        return classid2;
    }

    public void setClassid2(String classid2) {
        this.classid2 = classid2;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public ExtDataBean getExtData() {
        return extData;
    }

    public void setExtData(ExtDataBean extData) {
        extData.setParentid(this.dynamicid);
        this.extData = extData;
    }

    public int getIsFav() {
        return isFav;
    }

    @Bindable
    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public List<FavsUsersBean> getFavsUsers() {
        return favsUsers;
    }

    public void setFavsUsers(List<FavsUsersBean> favsUsers) {
        this.favsUsers = favsUsers;
    }

    public List<CommentUsersBean> getCommentUsers() {
        return commentUsers;
    }

    public void setCommentUsers(List<CommentUsersBean> commentUsers) {
        this.commentUsers = commentUsers;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static class ExtDataBean extends BaseObservable implements Serializable {

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        /*
                * "shi":"3428",
        "ting":"3436",
        "wei":"3441",
        "ru_time":"111",
        "zu_type":"3447",
        "zu_date":"3458",
        "sheshi":"3461,3464",
                * */
        public String location;
        public String mianji;
        public String huxing;
        public String shi;
        public String ting;
        public String wei;
        public String ru_time;
        public String zu_type;
        public String zu_date;
        public String sheshi;


        // product
        /*
        * "paytype":"1",
"price":"184",
"point":"100",
"admin_img":"/var/upload/image/2019/08/2019082611295947902_173x173.png",
"classid1":"3412",
"classid2":"",
"classid":"3412",
"name":"太极服",
        * */

        public String paytype;
        public String price;
        public String point;
        public String admin_img;
        public String classid1;
        public String classid2;
        public String classid;
        public String postage;
//        public String name;


        public String getPostage() {
            return postage;
        }

        public void setPostage(String postage) {
            this.postage = postage;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        // project
        public String linkman;

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        //定制报告
        public String option_type;  // 1日报 2 周报 3 月报

        public String file_path; //pdf

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
        // 警报推送

        public String countryid_name;
        public String warn_level_name;
        public String messageType_name;


        //问答
        private String audio;
        private String audio_duration;


        private String out_url;

        public String getOut_url() {
            return out_url;
        }

        public void setOut_url(String out_url) {
            this.out_url = out_url;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

        public String getAudio_duration() {
            return audio_duration;
        }

        public void setAudio_duration(String audio_duration) {
            this.audio_duration = audio_duration;
        }

        private String parentid;  // dynamicid

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        /**
         * memberid : 3056
         * uuid : b8879eb1f38d7b6c0df9fec18b05e26b
         * title : 终极教师
         * thumb : /var/upload/image/2018/08/2018080719310067823_400x237.jpg
         * imgs : array (
         * 0 =>
         * array (
         * \'sort\' => \'0\',
         * \'url\' => \'/var/upload/image/2018/08/2018080719310067823_400x237.jpg\',
         * ),
         * )
         * description : 说你呢
         * updatetime : 1533641460
         * inputtime : 1533641460
         * tags : [{"keyname":"原价","value":"是男是女","sort":"0","tagid":3},{"keyname":"折扣","value":"逗你的呢","sort":"1","tagid":4},{"keyname":"种类","value":"点开看","sort":"2","tagid":5},{"keyname":"自己在家","value":"新裤裤","sort":"3","tagid":6}]
         * wapContent : null
         */


        private String memberid;
        private String uuid;
        private String title;
        private String thumb;
        private List<ImgDataBean> imgs;
        private String description;
        private String content;
        private String new_content;
        private List<String> newsImgs;

        public List<String> getNewsImgs() {
            return newsImgs;
        }

        public void setNewsImgs(List<String> newsImgs) {
            this.newsImgs = newsImgs;
        }

        private int updatetime;
        private int inputtime;


        private List<TagsBean> tags;
        private List<ResourceBean> resource;

//        private Map<String, ResourceBean> resource;

        public List<ResourceBean> getResourceList() {
//            List<ResourceBean> resourceList = new ArrayList<>();
//            if (this.getResource() == null || this.getResource().size() == 0) {
//                return resourceList;
//            } else {
//
//                for (String key : this.getResource().keySet()) {
//                    resourceList.add(this.getResource().get(key));
//                }
//                return resourceList;
//            }


            return this.resource;
        }


        public String getResourceImg() {
            try {
                if (getResourceList().size() > 0) {
                    return "";
                } else {
                    return "";
                }
            } catch (Exception e) {
                return "";
            }

        }

        private String ISBN;
        private String author;
        private String binding;
        private String bookclass;
        private String bookclass2;
        private String circleid;
        private String color;
        private String contact;
        private String controlg;
        private String discount;
        private String format;
        private String keyword;
        private String language;
        private String name;
        private String pagenum;
        private String paper;
        private String pricing;
        private String publish_time;
        private String publishing;
        private String setnumber;
        private String utid;
        private int audit;
        /**
         * imgs : array (
         * 0 =>
         * array (
         * \'sort\' => \'0\',
         * \'url\' => \'/var/upload/image/2018/08/2018082209424382133_400x238.jpg\',
         * ),
         * )
         * companyName : 哈哈
         * region : 781
         * region1 : 10
         * region2 : 45
         * region3 : 781
         * wapContent : [{"type":"2","sort":"0","imgs":[{"url":"/var/upload/image/2018/08/2018082209422952997_220x304.jpg","sort":"0"}]},{"type":"1","text":"挖啊挖","sort":"1"}]
         */

        private String companyName;
        private String region;
        private String region1;
        private String region2;
        private String region3;
        @SerializedName("wapContent")
        private List<WapContentBean> wapContent;
        /**
         * purchase_date : 1535472000
         * company : 湖南新华发行（集团）有限责任公司
         * department : 大中专教材
         * documentMaker : 李欢
         * contact_way : 0731-84428446
         * consignee : 梁颖涛
         * receiving_telephone : 0731-84014067
         * shipping_address : 湖南省长沙市芙蓉区五一大道826号
         * company_name : 湖南省新华发行（集团）有限责任公司
         * bank : 建设银行芙蓉区总行
         * dut_paragraph : 91350000158141604T
         * account_number : 53123570010210014069
         * address : 湖南省长沙市芙蓉区五一大道826号
         * order_number : CGDD15356849012092
         * status : 0
         * circleid : 3101
         * memberid : 3078
         * num : 100
         * sum : 10000
         * goods_list : [{"ISBN":"123","name":"高等学校英语应用能力B级全真模拟","author":"王亚伦 王丽","publishing":"东北师范大学出版社","num":100,"price":"100.00","sum":0}]
         */

        private List<GoodsListBean> goods_list;

        public List<WapContentBean> getWapContent() {
            return wapContent;
        }

        public String getNew_content() {
            return new_content;
        }

        public void setNew_content(String new_content) {
            this.new_content = new_content;
        }

        public List<ImgDataBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgDataBean> imgs) {
            this.imgs = imgs;
        }

        public List<ResourceBean> getResource() {
            return resource;
        }

        public void setResource(List<ResourceBean> resource) {
//            if (resource != null && resource.size() > 0) {
//                for (int i = 0; i < resource.size(); i++) {
//                    resource.get(i).setParentid(ExtDataBean.this.parentid);
//                }
//            }
//            ResourceItems.addAll(resource);
            this.resource = resource;
        }


//        public Map<String, ResourceBean> getResource() {
//            return resource;
//        }
//
//        public void setResource(Map<String, ResourceBean> resource) {
//            this.resource = resource;
//
//        }


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }

        public int getInputtime() {
            return inputtime;
        }

        public void setInputtime(int inputtime) {
            this.inputtime = inputtime;
        }


        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public String getISBN() {
            return ISBN;
        }

        public void setISBN(String ISBN) {
            this.ISBN = ISBN;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBinding() {
            return binding;
        }

        public void setBinding(String binding) {
            this.binding = binding;
        }

        public String getBookclass() {
            return bookclass;
        }

        public void setBookclass(String bookclass) {
            this.bookclass = bookclass;
        }

        public String getBookclass2() {
            return bookclass2;
        }

        public void setBookclass2(String bookclass2) {
            this.bookclass2 = bookclass2;
        }

        public String getCircleid() {
            return circleid;
        }

        public void setCircleid(String circleid) {
            this.circleid = circleid;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getControlg() {
            return controlg;
        }

        public void setControlg(String controlg) {
            this.controlg = controlg;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPagenum() {
            return pagenum;
        }

        public void setPagenum(String pagenum) {
            this.pagenum = pagenum;
        }

        public String getPaper() {
            return paper;
        }

        public void setPaper(String paper) {
            this.paper = paper;
        }

        public String getPricing() {
            return pricing;
        }

        public void setPricing(String pricing) {
            this.pricing = pricing;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getPublishing() {
            return publishing;
        }

        public void setPublishing(String publishing) {
            this.publishing = publishing;
        }

        public String getSetnumber() {
            return setnumber;
        }

        public void setSetnumber(String setnumber) {
            this.setnumber = setnumber;
        }

        public String getUtid() {
            return utid;
        }

        public void setUtid(String utid) {
            this.utid = utid;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegion1() {
            return region1;
        }

        public void setRegion1(String region1) {
            this.region1 = region1;
        }

        public String getRegion2() {
            return region2;
        }

        public void setRegion2(String region2) {
            this.region2 = region2;
        }

        public String getRegion3() {
            return region3;
        }

        public void setRegion3(String region3) {
            this.region3 = region3;
        }

        public void setWapContent(List<WapContentBean> wapContent) {
            this.wapContent = wapContent;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }


        public static class TagsBean extends BaseObservable implements Serializable {
            /**
             * keyname : 原价
             * value : 是男是女
             * sort : 0
             * tagid : 3
             */

            private String keyname;
            private String value;
            private String sort;
            private int tagid;

            public String getKeyname() {
                return keyname;
            }

            public void setKeyname(String keyname) {
                this.keyname = keyname;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public int getTagid() {
                return tagid;
            }

            public void setTagid(int tagid) {
                this.tagid = tagid;
            }
        }

        public static class WapContentBean extends BaseObservable implements Serializable {
            /**
             * type : 2
             * sort : 0
             * imgs : [{"url":"/var/upload/image/2018/08/2018082209422952997_220x304.jpg","sort":"0"}]
             * text : 挖啊挖
             */

            private String type;
            private String sort;
            private String text;
            @SerializedName("imgs")
            private List<ResourceBean> imgs;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public List<ResourceBean> getImgs() {
                return imgs;
            }

            public void setImgsX(List<ResourceBean> imgs) {
                this.imgs = imgs;
            }
        }

        public static class GoodsListBean extends BaseObservable implements Serializable {
            /**
             * ISBN : 123
             * name : 高等学校英语应用能力B级全真模拟
             * author : 王亚伦 王丽
             * publishing : 东北师范大学出版社
             * num : 100
             * price : 100.00
             * sum : 0
             */

            private String ISBN;
            private String name;
            private String author;
            private String publishing;
            private int num;
            private String price;
            private int sum;

            public String getISBN() {
                return ISBN;
            }

            public void setISBN(String ISBN) {
                this.ISBN = ISBN;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getPublishing() {
                return publishing;
            }

            public void setPublishing(String publishing) {
                this.publishing = publishing;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }
        }
    }

    public static class ShareBean extends BaseObservable implements Serializable {
        /**
         * shortUrl : https://bookhome360.com/h5.php?m=circle.dynamicDetail&id=UCldpQz0lCzJhA&shareuuid=b8879eb1f38d7b6c0df9fec18b05e26b&time=1533810103&shareid=1deb7447f074098f6c0e557d00b480ab
         * shareUrl : https://bookhome360.com/h5.php?m=circle.dynamicDetail&id=UCldpQz0lCzJhA&shareuuid=b8879eb1f38d7b6c0df9fec18b05e26b&time=1533810103&shareid=1deb7447f074098f6c0e557d00b480ab
         * shareTit : 王老妖向你分享了7777的圈子动态。
         * shareDesc : 来自“7777”圈子的动态
         * shareImg : https://oss.bookhome360.com/static/var/upload/image/2018/08/2018080615404671899_364x364.jpg
         */

        private String shortUrl;
        private String shareUrl;
        private String shareTit;
        private String shareDesc;
        private String shareImg;

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getShareTit() {
            return shareTit;
        }

        public void setShareTit(String shareTit) {
            this.shareTit = shareTit;
        }

        public String getShareDesc() {
            return shareDesc;
        }

        public void setShareDesc(String shareDesc) {
            this.shareDesc = shareDesc;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }
    }


    public static class FavsUsersBean extends BaseObservable implements Serializable {
        /**
         * nickname : 小海
         */

        private String nickname;

        private String avatar;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }


    public static class CommentUsersBean extends BaseObservable implements Serializable {
        /**
         * commentid : 1
         * content : 恩恩 又打雷下雨的
         * memberid : 2
         * uuid :
         * nickname : 张三
         */

        private String commentid;
        private String content;
        private String memberid;
        private String uuid;
        private String nickname;

        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }


    public static class ResourceBean extends BaseObservable implements Serializable {

        @Override
        public String toString() {
            return "{" +
                    "t:'" + t + '\'' +
                    ", img:'" + img + '\'' +
                    ", url:'" + url + '\'' +
                    ", sort:'" + sort + '\'' +
                    '}';
        }

        private String parentid;  // dynamicID

        private int t;
        private String img;
        private String url;
        private String sort;


        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }


    }


    public static class ImgDataBean extends BaseObservable implements Serializable {
        private String sort;
        private String url;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}
