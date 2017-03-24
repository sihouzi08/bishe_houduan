package hss.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Foreveross on 2017/1/17.
 * 种类实体类 方便操作 映射数据库表category
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "category")
    private String category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
