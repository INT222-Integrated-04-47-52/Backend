package sit.int222.mongkolthorn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "size")
public class Size {
  @Id
  private long sizeId;
  private String sizeName;
  private double proportion;
  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "closet_id")
  private Closet closet;

  public Size() {
  }

  public Size(long sizeId, String sizeName, double proportion, Closet closet) {
    this.sizeId = sizeId;
    this.sizeName = sizeName;
    this.proportion = proportion;
    this.closet = closet;
  }

  public long getSizeId() {
    return sizeId;
  }

  public void setSizeId(long sizeId) {
    this.sizeId = sizeId;
  }

  public String getSizeName() {
    return sizeName;
  }

  public void setSizeName(String sizeName) {
    this.sizeName = sizeName;
  }

  public double getProportion() {
    return proportion;
  }

  public void setProportion(double proportion) {
    this.proportion = proportion;
  }

  public Closet getCloset() {
    return closet;
  }

  public void setCloset(Closet closet) {
    this.closet = closet;
  }
}
