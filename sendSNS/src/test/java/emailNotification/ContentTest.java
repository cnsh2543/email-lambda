package emailNotification;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ContentTest {
  private Content dummyContent = new Content("Tom", "5");

  @Test
  public void ableToRetrieveLikeCounts() {

    String val = dummyContent.getLikeCount();

    assertEquals(val, "5");

  }

  @Test
  public void ableToRetrieveRecipient() {
    String val = dummyContent.getRecipient();
    assertEquals(val, "Tom");
  }
}
