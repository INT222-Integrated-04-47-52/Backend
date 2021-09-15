package sit.int222.mongkolthorn.models;

import javax.persistence.*;

@Entity
@Table(name = "ad_access")
public class AdAccess {
  @Id
  private long aaId;
  private String userFname;
  private String userLname;
  private String userPhone;
  @ManyToOne
  @JoinColumn(name = "closet_id")
  private Closet closetId;


  public long getAaId() {
    return aaId;
  }

  public void setAaId(long aaId) {
    this.aaId = aaId;
  }


  public String getUserFname() {
    return userFname;
  }

  public void setUserFname(String userFname) {
    this.userFname = userFname;
  }


  public String getUserLname() {
    return userLname;
  }

  public void setUserLname(String userLname) {
    this.userLname = userLname;
  }


  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }


  public Closet getClosetId() {
    return closetId;
  }

  public void setClosetId(Closet closetId) {
    this.closetId = closetId;
  }
}
