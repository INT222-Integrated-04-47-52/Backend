package sit.int222.mongkolthorn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
  @Id
  private long productId;
  @Column(name= "pname")
  private String name;
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
//  @JsonBackReference
//  @OneToMany(mappedBy = "product")
//  private List<Closet> closet;

  public Product() {
  }

  public Product(long productId, String name, String image, String description, Kind kind, Gender gender, Type type) {
    this.productId = productId;
    this.name = name;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

//  public List<Closet> getCloset() {
//    return closet;
//  }
//
//  public void setCloset(List<Closet> closet) {
//    this.closet = closet;
//  }
}
