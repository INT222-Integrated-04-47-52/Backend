package sit.int222.mongkolthorn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "kind")
public class Kind {
  @Id
  private long kindId;
  private String kindName;
  @JsonBackReference
  @OneToMany(mappedBy = "kind")
  private List<Product> products;

  public long getKindId() {
    return kindId;
  }

  public void setKindId(long kindId) {
    this.kindId = kindId;
  }


  public String getKindName() {
    return kindName;
  }

  public void setKindName(String kindName) {
    this.kindName = kindName;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }
}
