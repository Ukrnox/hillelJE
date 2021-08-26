package nox.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"country", "region"})
@ToString(exclude = {"country", "region"})
@Table(name = "cities")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private CountryEntity country;

    @ManyToOne
    private RegionEntity region;

    public CityEntity(String name) {
        this.name = name;
    }

    public CityEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityEntity(String name, CountryEntity country, RegionEntity region) {
        this.name = name;
        this.country = country;
        this.region = region;
    }
}
