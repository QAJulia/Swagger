package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Pet {
    @Expose
    Category category;
    @Expose
    String name;
    @Expose
    String[] photoUrls;
    @Expose
    Tags[] tags;
    @Expose
    String status;
    int id;

}
