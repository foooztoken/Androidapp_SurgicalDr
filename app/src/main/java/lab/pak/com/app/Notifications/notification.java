package lab.pak.com.app.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class notification {
    @SerializedName("conversationstatus")
    @Expose
    public   String conversationstatus;
    @SerializedName("seen")
    @Expose
    public   String seen;
    @SerializedName("id")
    @Expose
    public   String id;
    @SerializedName("userid")
    @Expose
    public   String userid;
    @SerializedName("times")
    @Expose
    public   String times;

    @SerializedName("message")
    @Expose
    public   String message;
    @SerializedName("providerid")
    @Expose
    public   String providerid;
    @SerializedName("orderid")
    @Expose
    public   String orderid;
}
