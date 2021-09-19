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
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CityEntity> cities;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RegionEntity> regions;

    public CountryEntity(String name) {
        this.name = name;
    }

    public CountryEntity(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public CountryEntity(String name, List<CityEntity> cities, List<RegionEntity> regions) {
        this.name = name;
        this.cities = cities;
        this.regions = regions;
    }
}
