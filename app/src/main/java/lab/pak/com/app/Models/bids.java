package lab.pak.com.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bids {
    @SerializedName("country")
    @Expose
    public   String country;
    @SerializedName("nodays")
    @Expose
    public   String nodays;
    @SerializedName("icuinclude")
    @Expose
    public   String icuinclude;
    @SerializedName("bloodtransfer")
    @Expose
    public   String bloodtransfer;
    @SerializedName("roomtype")
    @Expose
    public   String roomtype;
    @SerializedName("time")
    @Expose
    public   String time;
    @SerializedName("id")
    @Expose
    public   String id;
    @SerializedName("providerid")
    @Expose
    public   String providerid;
    @SerializedName("userid")
    @Expose
    public   String userid;
    @SerializedName("budget")
    @Expose
    public   int budget;
    @SerializedName("seialno")
    @Expose
    public   String serialno;
    @SerializedName("description")
    @Expose
    public   String description;
    @SerializedName("state")
    @Expose
    public   String state;
    @SerializedName("city")
    @Expose
    public   String city;
    @SerializedName("specialization")
    @Expose
    public   String specialization;
    @SerializedName("status")
    @Expose
    public   String status;
    @SerializedName("bidid")
    @Expose
    public   String bidid;
    @SerializedName("jobid")
    @Expose
    public   String jobid;
}
