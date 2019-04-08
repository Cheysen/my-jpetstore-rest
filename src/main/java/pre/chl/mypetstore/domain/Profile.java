package pre.chl.mypetstore.domain;

public class Profile {
    private String userid;
    private String langpref;
    private String favcategory;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLangpref() {
        return langpref;
    }

    public void setLangpref(String langpref) {
        this.langpref = langpref;
    }

    public String getFavcategory() {
        return favcategory;
    }

    public void setFavcategory(String favcategory) {
        this.favcategory = favcategory;
    }

    public Integer getBanneropt() {
        return banneropt;
    }

    public void setBanneropt(Integer banneropt) {
        this.banneropt = banneropt;
    }

    private Integer banneropt;
}
