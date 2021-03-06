package sit.int222.mongkolthorn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
  @Id
  private long accountId;
  private String fname;
  private String lname;
  private String phone;
  private String email;
  @JsonIgnore
  private String password;
  private String role;
  @JsonBackReference
  @OneToMany(mappedBy = "account")
  private List<Closet> closetList;

  public Account() {
  }

  public Account(long accountId, String fname, String lname, String phone, String email, String password, String role) {
    this.accountId = accountId;
    this.fname = fname;
    this.lname = lname;
    this.phone = phone;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }


  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }


  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<Closet> getClosetList() {
    return closetList;
  }

  public void setClosetList(List<Closet> closetList) {
    this.closetList = closetList;
  }
}
