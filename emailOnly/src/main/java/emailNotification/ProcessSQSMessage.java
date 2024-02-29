package emailNotification;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetServerException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

public class ProcessSQSMessage implements RequestHandler<SQSEvent, Void> {

  private Email email;

  public ProcessSQSMessage() {
    this.email = new Email();
  }

  public void setEmail(Email email) {
    this.email = new Email();
  }

  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    try {
      for (SQSMessage msg : event.getRecords()) {
        System.out.println(new String(msg.getBody()));
        email.sendEmail(msg.getBody().split(" ")[0], msg.getBody().split(" ")[1]);
      }
      return null;
    } catch (MailjetException | MailjetSocketTimeoutException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }
  }
}
