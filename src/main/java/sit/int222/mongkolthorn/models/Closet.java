package sit.int222.mongkolthorn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "closet")
public class Closet {
  @Id
  private long closetId;
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  @ManyToOne
  @JoinColumn(name = "color_id")
  private Colors color;
  @OneToMany(mappedBy = "closet")
  private List<Size> size;
  private java.sql.Date pickUpDate;

  public Closet() {
  }

  public Closet(long closetId, Account account, Product product, Colors color, List<Size> size, Date pickUpDate) {
    this.closetId = closetId;
    this.account = account;
    this.product = product;
    this.color = color;
    this.size = size;
    this.pickUpDate = pickUpDate;
  }

  public long getClosetId() {
    return closetId;
  }

  public void setClosetId(long closetId) {
    this.closetId = closetId;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Colors getColor() {
    return color;
  }

  public void setColor(Colors color) {
    this.color = color;
  }

  public Date getPickUpDate() {
    return pickUpDate;
  }

  public void setPickUpDate(Date pickUpDate) {
    this.pickUpDate = pickUpDate;
  }

  public List<Size> getSize() {
    return size;
  }

  public void setSize(List<Size> size) {
    this.size = size;
  }
}
