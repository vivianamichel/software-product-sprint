 
package com.google.sps.data;
 
import java.util.Date;

public final class DataStats {
 
  private final Date dateCreated;
  private final String userName;
  private final String comment;

  public DataStats(Date dateCreated, String userName, String comment) {
    this.dateCreated = dateCreated;
    this.userName = userName;
    this.comment = comment;
  }

  public Date getTime() {
    return dateCreated;
  }

  public String getUserName() {
    return userName;
  }

  public String getComment() {
    return comment;
  }

}