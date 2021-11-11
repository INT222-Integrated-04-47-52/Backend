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
  @OneToOne
  @JoinColumn(name = "account_id")
  private Account accountId;


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


  public Account getAccountId() {
    return accountId;
  }

  public void setAccountId(Account accountId) {
    this.accountId = accountId;
  }
}
