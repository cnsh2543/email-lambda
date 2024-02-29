package emailNotification;

import static org.mockito.Mockito.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.List;

public class ProcessSQSMessageTest {

  @Test
  public void testHandleRequest() throws MailjetSocketTimeoutException, MailjetException {
    Email mockEmail = mock(Email.class);
    ProcessSQSMessage processSQSMessage1 = new ProcessSQSMessage();
    processSQSMessage1.setEmail(mockEmail);
    // Create a sample SQS event with one message
    SQSEvent event = new SQSEvent();
    SQSMessage sqsMessage = new SQSMessage();
    sqsMessage.setBody("recipient@example.com 5");
    event.setRecords(List.of(sqsMessage));

    PrintStream originalOut = System.out;
    try {
      PrintStream mockPrintStream = mock(PrintStream.class);
      System.setOut(mockPrintStream);

      // Create an instance of ProcessSQSMessage
      ProcessSQSMessage processSQSMessage = new ProcessSQSMessage();

      // Call the handleRequest method
      processSQSMessage.handleRequest(event, mock(Context.class));

      // Verify that System.out.println was called with the expected message
      verify(mockPrintStream).println("recipient@example.com 5");
    } finally {
      // Reset System.out to the original PrintStream
      System.setOut(originalOut);
    }
  }
}
