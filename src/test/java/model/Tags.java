package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Tags {
    @Expose
    int id;
    @Expose
    String name;
}
