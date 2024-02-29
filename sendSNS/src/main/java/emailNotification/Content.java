package emailNotification;

public class Content {
  private final String recipient;
  private final String likeCount;

  public Content(String recipient, String likeCount) {
    this.recipient = recipient;
    this.likeCount = likeCount;
  }

  public String getRecipient() {
    return recipient;
  }

  public String getLikeCount() {
    return likeCount;
  }
}
