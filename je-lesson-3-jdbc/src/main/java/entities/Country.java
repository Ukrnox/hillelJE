package entities;

import tablecreator.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "countries")
public class Country {
    private long id;
    private String name;

    public Country(String name) {
        this.name = name;
    }
}
