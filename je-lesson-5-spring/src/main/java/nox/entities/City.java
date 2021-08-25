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
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Country country;

    @ManyToOne
    private Region region;

    public City(String name) {
        this.name = name;
    }

    public City(String name, Country country, Region region) {
        this.name = name;
        this.country = country;
        this.region = region;
    }
}
