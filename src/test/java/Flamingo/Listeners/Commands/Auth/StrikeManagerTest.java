package Flamingo.Listeners.Commands.Auth;

import Flamingo.Listeners.Commands.Strike.StrikeItem;
import Flamingo.Listeners.Commands.Strike.StrikeManager;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

public class StrikeManagerTest {

    private AmazonDynamoDB dynamoDB;
    private StrikeManager strikeManager;
    private Map<String, AttributeValue> newUserKey = new HashMap<>();
    private Map<String, AttributeValue> oldUserKey = new HashMap<>();

    @Before
    public void setup() {
        Dotenv dotenv = Dotenv.load();
        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("us-east-1")
                .build();
        strikeManager = new StrikeManager(dynamoDB);
        newUserKey.put(StrikeItem.KEY, new AttributeValue().withS("1!1"));
        oldUserKey.put(StrikeItem.KEY, new AttributeValue().withS("2!2"));
        strikeManager.strike("2", "2");
    }

    @After
    public void teardown() {
        dynamoDB.deleteItem(StrikeItem.TABLE_NAME, newUserKey);
        dynamoDB.deleteItem(StrikeItem.TABLE_NAME, oldUserKey);
    }

    @Test
    public void strike_new_user_is_one() {
        strikeManager.strike("1", "1");
        GetItemResult result = dynamoDB.getItem(StrikeItem.TABLE_NAME, newUserKey);
        assertTrue(result.getItem().get(StrikeItem.STRIKES).getN().equals("1"));
    }

    @Test
    public void strike_old_user_is_two() {
        strikeManager.strike("2", "2");
        GetItemResult result = dynamoDB.getItem(StrikeItem.TABLE_NAME, oldUserKey);
        assertTrue(result.getItem().get(StrikeItem.STRIKES).getN().equals("2"));
    }

    @Test
    public void clear_strikes() {
        strikeManager.clearStrikes("2", "2");
        GetItemResult result = dynamoDB.getItem(StrikeItem.TABLE_NAME, oldUserKey);
        assertTrue(result.getItem().get(StrikeItem.STRIKES).getN().equals("0"));
    }
}
