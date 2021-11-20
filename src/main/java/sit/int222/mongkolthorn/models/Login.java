package sit.int222.mongkolthorn.models;

import javax.persistence.*;

@Entity
@Table(name = "login")
public class Login {
  @Id
  private long loginId;
  @Column(name= "log_email")
  private String logEmail;
  @Column(name= "log_password")
  private String logPassword;
  @Column(name= "log_position")
  private String logPosition;
  @OneToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "account_id")
  private Account account;

  public Login() {
  }

  public Login(long loginId, String logEmail, String logPassword, String logPosition) {
    this.loginId = loginId;
    this.logEmail = logEmail;
    this.logPassword = logPassword;
    this.logPosition = logPosition;
  }

  public Login(long loginId, String logEmail, String logPassword, String logPosition, Account account) {
    this.loginId = loginId;
    this.logEmail = logEmail;
    this.logPassword = logPassword;
    this.logPosition = logPosition;
    this.account = account;
  }

  public long getLoginId() {
    return loginId;
  }

  public void setLoginId(long loginId) {
    this.loginId = loginId;
  }


  public String getLogEmail() {
    return logEmail;
  }

  public void setLogEmail(String logEmail) {
    this.logEmail = logEmail;
  }


  public String getLogPassword() {
    return logPassword;
  }

  public void setLogPassword(String logPassword) {
    this.logPassword = logPassword;
  }


  public String getLogPosition() {
    return logPosition;
  }

  public void setLogPosition(String logPosition) {
    this.logPosition = logPosition;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
