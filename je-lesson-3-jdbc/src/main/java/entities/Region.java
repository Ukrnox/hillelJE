package entities;

import tablecreator.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "regions")
public class Region {
    private long id;
    private String name;

    public Region(String name) {
        this.name = name;
    }
}
