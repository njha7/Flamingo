package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommand;
import Flamingo.Listeners.Commands.AbstractCommandTest;
import Flamingo.Listeners.Commands.Strike.Auth.GreaterThanOrEqualTo;
import Flamingo.Listeners.Commands.TestAbstractCommand;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.xspec.S;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class StrikeTest extends AbstractCommandTest {

    private AmazonDynamoDB dynamoDB;
    protected AbstractCommand command;

    @Before
    public void setup() {
        super.setup();
        Dotenv dotenv = Dotenv.load();
        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("us-east-1")
                .build();
        command = new Strike(dynamoDB, new GreaterThanOrEqualTo());
    }

    @Test
    public void ack_user_command() {
        //TODO: Write more sophisticated and meaningful test case
        assertTrue(true);
    }
}
