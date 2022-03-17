package model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "destination")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "details")
    private String details;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vacantion> vacantions;

    public Destination() {
    }

    public Destination(String name, String country, String details) {
        this.name = name;
        this.country = country;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Vacantion> getVacantions() {
        return vacantions;
    }

    public void setVacantions(List<Vacantion> vacantions) {
        this.vacantions = vacantions;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", details='" + details + '\'' +
                ", vacantions=" + vacantions +
                '}';
    }
}
