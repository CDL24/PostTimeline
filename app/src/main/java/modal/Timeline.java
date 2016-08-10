package modal;

/**
 * Created by C.limbachiya on 8/5/2016.
 */
public class Timeline {

    String postLatitude;
    String postLongitude;
    String postPlaceName;
    String postText;
    String postTime;
    String postMapUrl;

    public Timeline(String postLatitude, String postLongitude, String postPlaceName, String postText, String postTime, String postMapUrl) {
        this.postLatitude = postLatitude;
        this.postLongitude = postLongitude;
        this.postPlaceName = postPlaceName;
        this.postText = postText;
        this.postTime = postTime;
        this.postMapUrl = postMapUrl;
    }

    public String getPostLatitude() {
        return postLatitude;
    }

    public void setPostLatitude(String postLatitude) {
        this.postLatitude = postLatitude;
    }

    public String getPostLongitude() {
        return postLongitude;
    }

    public void setPostLongitude(String postLongitude) {
        this.postLongitude = postLongitude;
    }

    public String getPostPlaceName() {
        return postPlaceName;
    }

    public void setPostPlaceName(String postPlaceName) {
        this.postPlaceName = postPlaceName;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostMapUrl() {
        return postMapUrl;
    }

    public void setPostMapUrl(String postMapUrl) {
        this.postMapUrl = postMapUrl;
    }
}
