package sit.int222.mongkolthorn.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "closet")
public class Closet {
  @Id
  private long closetId;
  private String pname;
  private String image;
  private String style;
  private String kind;
  private String type;
  private String color;
  private java.sql.Date pickUpDate;


  public long getClosetId() {
    return closetId;
  }

  public void setClosetId(long closetId) {
    this.closetId = closetId;
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


  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }


  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }


  public java.sql.Date getPickUpDate() {
    return pickUpDate;
  }

  public void setPickUpDate(java.sql.Date pickUpDate) {
    this.pickUpDate = pickUpDate;
  }

}
