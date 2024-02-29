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

  protected ResultSet getResult() {

    // JDBC URL, username, and password of MySQL server
//        String url = System.getenv("MYSQL_URL");
//        String user = System.getenv("MYSQL_USER");
//        String password = System.getenv("MYSQL_PASS");
//        String url = dotenv.get("MYSQL_URL");
//        String user = dotenv.get("MYSQL_USER");
//        String password = dotenv.get("MYSQL_PASS");
    String url = System.getenv("MYSQL_URL");
    String user = System.getenv("MYSQL_USER");
    String password = System.getenv("MYSQL_PASS");

    try {

      // Establish a connection
      Connection connection = DriverManager.getConnection(url, user, password);

      // Example SELECT query
      String selectQuery =
        "SELECT email, count(like_userid) AS like_count FROM likes a "
          + "LEFT JOIN USERS b ON a.post_userid = b.userid "
          + "WHERE TIMESTAMPDIFF(HOUR, like_timestamp, CURRENT_TIMESTAMP()) <= 24 "
          + "GROUP BY 1 "
          + "HAVING count(like_userid) > 0";

      PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

      ResultSet result = preparedStatement.executeQuery();
      preparedStatement.close();
      connection.close();

      // Execute the query and get the result set
      return result;


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
