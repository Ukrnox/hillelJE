package nox.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cities", "regions"})
@ToString(exclude = {"cities", "regions"})
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<City> cities;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Region> regions;

    public Country(String name) {
        this.name = name;
    }

    public Country(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Country(String name, List<City> cities, List<Region> regions) {
        this.name = name;
        this.cities = cities;
        this.regions = regions;
    }
}
