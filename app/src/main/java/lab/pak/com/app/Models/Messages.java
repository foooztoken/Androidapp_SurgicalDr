package lab.pak.com.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messages {
    @SerializedName("name")
    @Expose
    public   String name;
    @SerializedName("userid")
    @Expose
    public   String userid;
    @SerializedName("providerid")
    @Expose
    public   String providerid;
    @SerializedName("id")
    @Expose
    public   String id;
    @SerializedName("attachmenturl")
    @Expose
    public   String attachmenturl;
    @SerializedName("message")
    @Expose
    public   String message;
    @SerializedName("status")
    @Expose
    public   String status;
    @SerializedName("jobid")
    @Expose
    public   String jobid;
    @SerializedName("dates")
    @Expose
    public   String dates;
    @SerializedName("bywho")
    @Expose
    public   String bywho;
    @SerializedName("username")
    @Expose
    public   String username;
    @SerializedName("providername")
    @Expose
    public   String providername;
}
