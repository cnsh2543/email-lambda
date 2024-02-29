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
    return sendAllSNSHelper(snsConfig);
  }

  public String sendAllSNSHelper(SNSConfig snsConfig) throws SQLException {
    generateList(snsConfig.getResult());
    for (Content content : senderList) {
      snsConfig.sendSingleSNS(content);
    }
    return "SNS sent successfully!";
  }


  public void generateList(ResultSet resultSet) throws SQLException {


    senderList = new ArrayList<>();


    // Process the result set
    while (resultSet.next()) {
      senderList.add(
        new Content(resultSet.getString("email"), resultSet.getString("like_count")));
    }
    // Close resources
    resultSet.close();


  }
}
