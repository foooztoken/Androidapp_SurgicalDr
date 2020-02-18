package lab.pak.com.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("id")
    @Expose
    public   String id;
    @SerializedName("refid")
    @Expose
    public String refid;
    @SerializedName("user_name")
    @Expose
    public   String username;
    @SerializedName("age")
    @Expose
    public   String age;
    @SerializedName("name")
    @Expose
    public   String name;
    @SerializedName("password")
    @Expose
    public   String password;
    @SerializedName("role_id")
    @Expose
    public   String role_id;
}
