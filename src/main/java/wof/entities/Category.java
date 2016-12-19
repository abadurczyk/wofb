package wof.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;


@ToString
@EqualsAndHashCode
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CATEGORY_ID")
    @Getter
    @Setter
    private int categoryId;

    @Column(name = "CATEGORY_NAME")//, unique = true)
    @Getter
    @Setter
    private String categoryName;

    //@ManyToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
    @Setter
    @Getter
    private Set<WallEntry> wallEntries = new HashSet<>(0);

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category() {
    }

}
