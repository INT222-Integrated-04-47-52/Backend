package sit.int222.mongkolthorn.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login")
public class Login {
  @Id
  private long loginId;
  private String logEmail;
  private String logPassword;
  private String logPosition;
  private long accountId;


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


  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }

}
