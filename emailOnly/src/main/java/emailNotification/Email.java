package emailNotification;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

// import io.github.cdimascio.dotenv.Dotenv;

public class Email {
  /** This call sends a message to the given recipient with vars and custom vars. */
  public Integer sendEmail(String recipient, String likes)
      throws MailjetException, MailjetSocketTimeoutException {

    //            Dotenv dotenv = Dotenv.load();
    MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;
    client =
        new MailjetClient(
            //                                                    dotenv.get("MJ_APIKEY_PUBLIC"),
            //                                                    dotenv.get("MJ_APIKEY_PRIVATE"),
            System.getenv("MJ_APIKEY_PUBLIC"),
            System.getenv("MJ_APIKEY_PRIVATE"),
            new ClientOptions("v3.1"));
    request =
        new MailjetRequest(Emailv31.resource)
            .property(
                Emailv31.MESSAGES,
                new JSONArray()
                    .put(
                        new JSONObject()
                            .put(
                                Emailv31.Message.FROM,
                                new JSONObject()
                                    .put("Email", "colinngsionghaur@gmail.com")
                                    .put("Name", "admin"))
                            .put(
                                Emailv31.Message.TO,
                                new JSONArray().put(new JSONObject().put("Email", recipient)))
                            //                                            .put("Name", "passenger
                            // 1")
                            .put(Emailv31.Message.TEMPLATEID, 5688192)
                            .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                            .put(
                                Emailv31.Message.SUBJECT,
                                String.format("You have %s new comments", likes))
                            .put(
                                Emailv31.Message.VARIABLES, new JSONObject().put("likes", likes))));
    response = client.post(request);
    return response.getStatus();
  }
}
