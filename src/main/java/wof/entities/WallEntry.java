package wof.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@ToString
@EqualsAndHashCode
public class WallEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "WALL_ENTRY_ID")
    @Getter
    @Setter
    private int wallEntryId;

    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String headline;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "WALLENTRY_CATEGORY",
            joinColumns = {@JoinColumn(name = "WALL_ENTRY_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    /*,
            uniqueConstraints = {@UniqueConstraint(
                    columnNames = {"CATEGORY_ID", "wall_entry_id"})})*/
    @Setter
    @Getter
    private Set<Category> categories;


    public WallEntry() {
    }

    public WallEntry(String headline, String description) {
        this.headline = headline;
        this.description = description;
    }
}
