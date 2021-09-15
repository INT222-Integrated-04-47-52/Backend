package sit.int222.mongkolthorn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Gender {
  @Id
  private long genderId;
  private String genderName;
  @JsonBackReference
  @OneToMany(mappedBy = "gender")
  private List<Product> products;


  public long getGenderId() {
    return genderId;
  }

  public void setGenderId(long genderId) {
    this.genderId = genderId;
  }


  public String getGenderName() {
    return genderName;
  }

  public void setGenderName(String genderName) {
    this.genderName = genderName;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }
}
