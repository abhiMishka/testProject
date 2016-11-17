package com.abhishek.testproject.model;

/**
 * Created by abhishekkumar on 17/11/16.
 */

public class User {

    /**
     * about : Mother, actor, entrepreneur, fitness enthusiast and an eternal positive thinker
     * id : 238bb4ca-606d-4817-afad-78bee2898264
     * username : Shilpa shetty kundra
     * followers : 35215
     * following : 5
     * image : http://img.ropose.com/userImages/13632253306661657730401415143533525274225757_circle.png
     * url : http://www.roposo.com/profile/shilpa-shetty-kundra-/238bb4ca-606d-4817-afad-78bee2898264
     * handle : @shilpashettykundra
     * is_following : false
     * createdOn : 1439530320545
     */

    private String about;
    private String id;
    private String username;
    private int followers;
    private int following;
    private String image;
    private String url;
    private String handle;
    private boolean is_following;
    private long createdOn;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public boolean isIs_following() {
        return is_following;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "User{" +
                "about='" + about + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", handle='" + handle + '\'' +
                ", is_following=" + is_following +
                ", createdOn=" + createdOn +
                '}';
    }
}
