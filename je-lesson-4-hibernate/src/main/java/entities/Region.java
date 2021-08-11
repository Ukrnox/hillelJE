package entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"country", "cities"})
@ToString(exclude = {"country", "cities"})
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<City> cities;

    @ManyToOne
    private Country country;

    public Region(String name) {
        this.name = name;
    }

    public Region(String name, List<City> cities, Country country) {
        this.name = name;
        this.cities = cities;
        this.country = country;
    }
}


