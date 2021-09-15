package sit.int222.mongkolthorn.models;

import javax.persistence.*;

@Entity
@Table(name = "account_has_product")
public class AccountHasProduct {
  @Id
  private long hasProductId;
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account accountId;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product productId;
  @OneToOne
  @JoinColumn(name = "closet_id")
  private Closet closetId;

  public long getHasProductId() {
    return hasProductId;
  }

  public void setHasProductId(long hasProductId) {
    this.hasProductId = hasProductId;
  }

  public Account getAccountId() {
    return accountId;
  }

  public void setAccountId(Account accountId) {
    this.accountId = accountId;
  }

  public Product getProductId() {
    return productId;
  }

  public void setProductId(Product productId) {
    this.productId = productId;
  }

  public Closet getClosetId() {
    return closetId;
  }

  public void setClosetId(Closet closetId) {
    this.closetId = closetId;
  }
}
