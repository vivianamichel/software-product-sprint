 
package com.google.sps.data;
import java.util.Date;

public final class Comment {
 
  private final long id;
  private final long timestamp;
  private final String userName;
  private final String userComment;

  public Comment(long id, long timestamp, String userName, String userComment) {
    this.id = id;
    this.timestamp = timestamp;
    this.userName = userName;
    this.userComment = userComment;
  }

}