package Flamingo.Listeners.Commands.Pasta;

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

public class PastaManagerMigrationTest {
    private AmazonDynamoDB dynamoDB;
    private PastaManager pastaManager;
    private PastaManagerV2 pastaManagerV2;
    private Map<String, AttributeValue> oldPastaKey = new HashMap<>();
    private Map<String, AttributeValue> newPastaKey = new HashMap<>();
    private Map<String, AttributeValue> newPastaKey2 = new HashMap<>();
    private Map<String, AttributeValue> migratedPastaKey = new HashMap<>();

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
        pastaManagerV2 = new PastaManagerV2(dynamoDB);
        pastaManager = new PastaManager(dynamoDB);

        oldPastaKey = PastaItem.buildPastaItemKey("1", "1'");
        migratedPastaKey = PastaItemV2.buildPastaItemKey("1", "1'");
        newPastaKey = PastaItemV2.buildPastaItemKey("2", "2");
        newPastaKey2 = PastaItemV2.buildPastaItemKey("4", "4");


        pastaManager.savePasta("1", "1", "1", "1");
    }

    @After
    public void teardown() {
        dynamoDB.deleteItem(PastaItem.TABLE_NAME, oldPastaKey);
        dynamoDB.deleteItem(PastaItemV2.TABLE_NAME, migratedPastaKey);
        dynamoDB.deleteItem(PastaItemV2.TABLE_NAME, newPastaKey);
        dynamoDB.deleteItem(PastaItemV2.TABLE_NAME, newPastaKey2);
    }

    @Test
    public void gets_pasta_from_old_table_and_migrates_it() {
        String pasta = pastaManagerV2.getPasta("1", "1");
        GetItemResult migratedPasta = dynamoDB.getItem(PastaItemV2.TABLE_NAME, PastaItemV2.buildPastaItemKey("1", "1"));
        assertTrue("1".equals(migratedPasta.getItem().get(PastaItemV2.PASTA).getS()));
    }

    @Test
    public void save_new_pasta() {
        String result = pastaManagerV2.savePasta("2", "2", "2", "2");
        assertTrue("Copypasta with alias 2 has been saved.".equals(result));

    }

    @Test
    public void get_new_pasta() {
        pastaManagerV2.savePasta("4", "4", "4", "4");
        String result = pastaManagerV2.getPasta("4", "4");
        assertTrue("4".equals(result));
    }

    @Test
    public void cannot_save_new_pasta_with_alias_in_old_table() {
        String status = pastaManagerV2.savePasta("1", "1", "1", "1");
        assertTrue("Copypasta with alias 1 already exists.".equals(status));
    }

    @Test
    public void get_nonextant_pasta() {
        String status = pastaManagerV2.getPasta("3", "3");
        assertTrue("No copypasta with alias 3 found.".equals(status));
    }
}
