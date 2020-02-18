package lab.pak.com.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class conversationmodel {
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("time")
    @Expose
    String time;
    @SerializedName("message")
    @Expose
           String message;
}
