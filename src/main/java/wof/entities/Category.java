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


@ToString
@EqualsAndHashCode
@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CATEGORY_ID")
    private int categoryId;

    @Column(name = "CATEGORY_NAME", unique = true)

    private String categoryName;

    //@ManyToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
 /*   @Setter
    @Getter
    private Set<WallEntry> wallEntries = new HashSet<>(0);
*/
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category() {
    }

}
