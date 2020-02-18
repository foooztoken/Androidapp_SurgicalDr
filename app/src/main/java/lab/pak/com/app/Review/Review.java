package lab.pak.com.app.Review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("review")
    @Expose
    public   String review;
    @SerializedName("name")
    @Expose
    public   String name;
    @SerializedName("id")
    @Expose
    public   String id;
    @SerializedName("employeeid")
    @Expose
    public   String employeeid;
    @SerializedName("serialno")
    @Expose
    public   String serialno;
    @SerializedName("stars")
    @Expose
    public   String stars;
    @SerializedName("tims")
    @Expose
    public   String tims;
    @SerializedName("providerid")
    @Expose
    public   String providerid;
}
