package emailNotification;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.ArrayList;

public class SendSNSTest {

  // Create an instance of SendSNS
  SendSNS sendSNS = new SendSNS();

  @Test
  public void snsPublishingIsTriggered() throws SQLException {
    // Create mock objects
    SNSConfig mockSNSConfig = mock(SNSConfig.class);

    // Set up mocks for database interactions
    doNothing().when(mockSNSConfig).sendSingleSNS(any());

    sendSNS.populateList(new Content("test@example.com","5"));

    // Call the method to be tested
    String result = sendSNS.sendAllSNSHelper(mockSNSConfig);


    // Assert the expected results based on the mocked data
    assertEquals("SNS sent successfully!", result);
  }

  @Test
  public void getSenderListWillRetrieveContentArray() throws SQLException {
    // Create mock objects


    // Call the method to be tested
    sendSNS.populateList(new Content("test@example.com","5"));
    ArrayList<Content> result = sendSNS.getSenderList();

    assertEquals(1,result.size());
    // Retrieve the result
  }


}



