package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Pets {
    @Expose
    Pet[] pets;//Или всё таки лист?
}
