package sit.int222.mongkolthorn.models;

import javax.persistence.*;

@Entity
@Table(name = "save_size")
public class SaveSize {
  @Id
  private long saveSizeId;
  private double proportion;
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account accountId;


  public long getSaveSizeId() {
    return saveSizeId;
  }

  public void setSaveSizeId(long saveSizeId) {
    this.saveSizeId = saveSizeId;
  }


  public double getProportion() {
    return proportion;
  }

  public void setProportion(double proportion) {
    this.proportion = proportion;
  }


  public Account getAccountId() {
    return accountId;
  }

  public void setAccountId(Account accountId) {
    this.accountId = accountId;
  }
}
