package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "")
    private List<Member> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
