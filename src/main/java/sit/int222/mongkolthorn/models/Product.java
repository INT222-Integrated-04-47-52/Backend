package sit.int222.mongkolthorn.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
  @Id
  private long productId;
  private String pname;
  private String image;
  private String description;
  @ManyToOne
  @JoinColumn(name = "kind_id")
  private Kind kind;
  @ManyToOne
  @JoinColumn(name = "gender_id")
  private Gender gender;
  @ManyToOne
  @JoinColumn(name = "type_id")
  private Type type;
  @OneToMany(mappedBy = "product")
  private List<ProductHasColors> productHasColors;

  public Product() {
  }

  public Product(long productId, String pname, String image, String description, Kind kind, Gender gender, Type type) {
    this.productId = productId;
    this.pname = pname;
    this.image = image;
    this.description = description;
    this.kind = kind;
    this.gender = gender;
    this.type = type;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Kind getKind() {
    return kind;
  }

  public void setKind(Kind kind) {
    this.kind = kind;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public List<ProductHasColors> getProductHasColors() {
    return productHasColors;
  }

  public void setProductHasColors(List<ProductHasColors> productHasColors) {
    this.productHasColors = productHasColors;
  }
}
