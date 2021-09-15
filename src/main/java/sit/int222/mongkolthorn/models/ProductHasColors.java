package sit.int222.mongkolthorn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "product_has_colors")
public class ProductHasColors {
  @Id
  private long hasColorsId;
  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  @ManyToOne
  @JoinColumn(name = "color_id")
  private Colors colors;


  public long getHasColorsId() {
    return hasColorsId;
  }

  public void setHasColorsId(long hasColorsId) {
    this.hasColorsId = hasColorsId;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Colors getColors() {
    return colors;
  }

  public void setColors(Colors colors) {
    this.colors = colors;
  }
}
