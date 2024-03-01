package emailNotification;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.sql.*;

public class SNSConfig {

  protected void pubTopic(SnsClient snsClient, String message, String topicArn) {

    try {
      PublishRequest request = PublishRequest.builder()
        .message(message)
        .topicArn(topicArn)
        .build();

      PublishResponse result = snsClient.publish(request);
      System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

    } catch (SnsException e) {
      System.err.println(e.awsErrorDetails().errorMessage());
      System.exit(1);
    }
  }

  protected void sendSingleSNS(Content content) {
    String message = String.format("%s %s", content.getRecipient(), content.getLikeCount());
//        String topicArn = dotenv.get("SNS_ARN");
    String topicArn = System.getenv("SNS_ARN");
    SnsClient snsClient = SnsClient.builder()
      .region(Region.EU_WEST_2)
      .build();
    pubTopic(snsClient, message, topicArn);
    snsClient.close();
  }



}
