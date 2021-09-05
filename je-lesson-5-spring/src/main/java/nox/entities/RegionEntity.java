package nox.entities;

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
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CityEntity> cities;

    @ManyToOne
    private CountryEntity country;

    public RegionEntity(String name) {
        this.name = name;
    }

    public RegionEntity(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public RegionEntity(String name, List<CityEntity> cities, CountryEntity country) {
        this.name = name;
        this.cities = cities;
        this.country = country;
    }
}


