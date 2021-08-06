package entities;

import tablecreator.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "cities")
public class City {
    private long id;
    private String name;

    public City(String name) {
        this.name = name;
    }
}
