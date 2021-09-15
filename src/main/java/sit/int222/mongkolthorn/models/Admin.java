package sit.int222.mongkolthorn.models;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
  @Id
  private long adminId;
  private String adFname;
  private String adLname;
  private String adEmail;
  private String adPassword;
  @OneToOne
  @JoinColumn(name = "access_aa_id")
  private AdAccess accessAaId;


  public long getAdminId() {
    return adminId;
  }

  public void setAdminId(long adminId) {
    this.adminId = adminId;
  }


  public String getAdFname() {
    return adFname;
  }

  public void setAdFname(String adFname) {
    this.adFname = adFname;
  }


  public String getAdLname() {
    return adLname;
  }

  public void setAdLname(String adLname) {
    this.adLname = adLname;
  }


  public String getAdEmail() {
    return adEmail;
  }

  public void setAdEmail(String adEmail) {
    this.adEmail = adEmail;
  }


  public String getAdPassword() {
    return adPassword;
  }

  public void setAdPassword(String adPassword) {
    this.adPassword = adPassword;
  }


  public AdAccess getAccessAaId() {
    return accessAaId;
  }

  public void setAccessAaId(AdAccess accessAaId) {
    this.accessAaId = accessAaId;
  }
}
