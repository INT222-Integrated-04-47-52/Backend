package sit.int222.mongkolthorn.models;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "colors")
public class Colors {
  @Id
  private long colorId;
  private String colorName;
  private String colorCode;
  @JsonBackReference
  @OneToMany(mappedBy = "colors")
  private List<ProductHasColors> productHasColors;
  @JsonBackReference
  @OneToMany(mappedBy = "color")
  private List<Closet> closet;


  public long getColorId() {
    return colorId;
  }

  public void setColorId(long colorId) {
    this.colorId = colorId;
  }


  public String getColorName() {
    return colorName;
  }

  public void setColorName(String colorName) {
    this.colorName = colorName;
  }


  public String getColorCode() {
    return colorCode;
  }

  public void setColorCode(String colorCode) {
    this.colorCode = colorCode;
  }

  public List<ProductHasColors> getProductHasColors() {
    return productHasColors;
  }

  public void setProductHasColors(List<ProductHasColors> productHasColors) {
    this.productHasColors = productHasColors;
  }

  public List<Closet> getCloset() {
    return closet;
  }

  public void setCloset(List<Closet> closet) {
    this.closet = closet;
  }
}
