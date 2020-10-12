package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ResponseBodyForDelete {
    @Expose
    int code;
    @Expose
    String type;
    @Expose
    int message;

}
