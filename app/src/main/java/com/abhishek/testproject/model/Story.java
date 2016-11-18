package com.abhishek.testproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abhishekkumar on 17/11/16.
 */

public class Story implements Parcelable {

    /**
     * description : Celebrating Black Friday in this lovely black cut out romper and floral accessories. I love black and i think it is the easiest thing to wear when i am in doubt. So a black romper solves my dilemma of what to wear when i am short of time to decide and outfit. When you wear black add some fun accessories to keep the outfit fun and lively.
     * id : fa6d9bdf-eae3-4d6c-a668-9be94cfaf980
     * verb : created this story on 25 October
     * db : 238bb4ca-606d-4817-afad-78bee2898264
     * url : http://www.roposo.com/story/throwback-/fa6d9bdf-eae3-4d6c-a668-9be94cfaf980
     * si : http://img0.ropose.com/story/1445748867804_800_238bb4ca-606d-4817-afad-78bee2898264.jpeg
     * type : story
     * title : Throwback
     * like_flag : false
     * likes_count : 1099
     * comment_count : 10
     */

    private String description;
    private String id;
    private String verb;
    private String db;
    private String url;
    private String si;
    private String type;
    private String title;
    private boolean like_flag;
    private int likes_count;
    private int comment_count;

    public Story(Parcel in) {
        description = in.readString();
        id = in.readString();
        verb = in.readString();
        db = in.readString();
        url = in.readString();
        si = in.readString();
        type = in.readString();
        title = in.readString();
        likes_count = in.readInt();
        comment_count = in.readInt();

        like_flag = in.readByte() != 0;

    }

    public Story() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLike_flag() {
        return like_flag;
    }

    public void setLike_flag(boolean like_flag) {
        this.like_flag = like_flag;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    @Override
    public String toString() {
        return "Story{" +
                "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", verb='" + verb + '\'' +
                ", db='" + db + '\'' +
                ", url='" + url + '\'' +
                ", si='" + si + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", like_flag=" + like_flag +
                ", likes_count=" + likes_count +
                ", comment_count=" + comment_count +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(id);
        parcel.writeString(verb);
        parcel.writeString(db);
        parcel.writeString(url);
        parcel.writeString(si);
        parcel.writeString(type);
        parcel.writeString(title);
        parcel.writeInt(likes_count);
        parcel.writeInt(comment_count);

        parcel.writeByte((byte) (like_flag ? 1 : 0));

    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
