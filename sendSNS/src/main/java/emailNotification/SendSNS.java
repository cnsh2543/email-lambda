package emailNotification;

import java.sql.*;
import java.util.ArrayList;


public class SendSNS {

//    private final Dotenv dotenv = Dotenv.load();

  private ArrayList<Content> senderList;

  public ArrayList<Content> getSenderList() {
    return senderList;
  }

  private final SNSConfig snsConfig = new SNSConfig();

  public String sendAllSNS() throws SQLException {
    generateList();
    return sendAllSNSHelper(snsConfig);
  }

  public String sendAllSNSHelper(SNSConfig snsConfig) throws SQLException {
    for (Content content : senderList) {
      snsConfig.sendSingleSNS(content);
    }
    return "SNS sent successfully!";
  }



  public void generateList() throws SQLException {


    senderList = new ArrayList<>();
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
          + "LEFT JOIN POSTS c ON a.postid = c.postid "
          + "LEFT JOIN USERS b ON c.userid = b.userid "
          + "WHERE TIMESTAMPDIFF(HOUR, like_timestamp, CURRENT_TIMESTAMP()) <= 24 "
          + "GROUP BY 1 "
          + "HAVING count(like_userid) > 0";

      PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

      ResultSet resultSet = preparedStatement.executeQuery();


      // Process the result set
      while (resultSet.next()) {
        senderList.add(
          new Content(resultSet.getString("email"), resultSet.getString("like_count")));
      }
      // Close resources
      resultSet.close();
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  public void populateList(Content content) {
    senderList = new ArrayList<>();
    senderList.add(content);
  }

}
