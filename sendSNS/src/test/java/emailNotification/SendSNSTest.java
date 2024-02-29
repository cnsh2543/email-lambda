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
  public void generateListReturnsArrayOfEmailList() throws SQLException {
    // Create mock objects
    ResultSet mockResultSet = mock(ResultSet.class);

    // Set up mocks for database interactions
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getString("email")).thenReturn("test@example.com");
    when(mockResultSet.getString("like_count")).thenReturn("2");


    // Call the method to be tested
    sendSNS.generateList(mockResultSet);

    // Retrieve the result
    ArrayList<Content> senderList = sendSNS.getSenderList();

    // Assert the expected results based on the mocked data
    assertEquals(1, senderList.size());
    assertEquals("test@example.com", senderList.get(0).getRecipient());
    assertEquals("2", senderList.get(0).getLikeCount());
  }

  @Test
  public void snsPublishingIsTriggered() throws SQLException {
    // Create mock objects
    ResultSet mockResultSet = mock(ResultSet.class);
    SNSConfig mockSNSConfig = mock(SNSConfig.class);

    // Set up mocks for database interactions
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getString("email")).thenReturn("test@example.com");
    when(mockResultSet.getString("like_count")).thenReturn("2");

    doNothing().when(mockSNSConfig).sendSingleSNS(any());
    when(mockSNSConfig.getResult()).thenReturn(mockResultSet);

    // Call the method to be tested
    String result = sendSNS.sendAllSNSHelper(mockSNSConfig);


    // Assert the expected results based on the mocked data
    assertEquals("SNS sent successfully!", result);
  }

}



