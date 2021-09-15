package sit.int222.mongkolthorn.models;

import javax.persistence.*;

@Entity
@Table(name = "set_size")
public class SetSize {
  @Id
  private long sizeId;
  private double proportion;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product productId;


  public long getSizeId() {
    return sizeId;
  }

  public void setSizeId(long sizeId) {
    this.sizeId = sizeId;
  }


  public double getProportion() {
    return proportion;
  }

  public void setProportion(double proportion) {
    this.proportion = proportion;
  }


  public Product getProductId() {
    return productId;
  }

  public void setProductId(Product productId) {
    this.productId = productId;
  }
}
