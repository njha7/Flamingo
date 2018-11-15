package Flamingo.Listeners.Commands.Pasta;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

import static Flamingo.Listeners.Commands.Pasta.PastaItem.KEY;
import static Flamingo.Listeners.Commands.Pasta.PastaItem.KEY_SEPARATOR;

public class PastaManager {

    private static final String CONDITION_EXPRESSION = "attribute_not_exists(" + PastaItem.KEY_EXPRESSION_SUBSTITUTION + ")";
    private final AmazonDynamoDB dynamoDB;

    public PastaManager(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    private static String buildKey(String guildId, String alias) {
        StringBuilder sb = new StringBuilder(guildId.length() + alias.length() + 1);
        sb.append(guildId);
        sb.append(KEY_SEPARATOR);
        sb.append(alias);
        return sb.toString();
    }

    private static Map<String, AttributeValue> buildPastaItemKey(String guildId, String alias) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(KEY, new AttributeValue().withS(buildKey(guildId, alias)));
        return key;
    }

    public String savePasta(String guildId, String userId, String alias, String pasta) {
        try {
            dynamoDB.putItem(buildNewPasta(guildId, userId, alias, pasta));
            return "Copypasta with alias " + alias + " has been saved.";
        } catch (ConditionalCheckFailedException e) {
            return "Copypasta with alias " + alias + " already exists.";
        } catch (Exception e) {
            return "An error has occurred. Please try again later";
        }
    }

    public String getPasta(String guildId, String alias) {
        try {
            GetItemResult pasta = dynamoDB.getItem(PastaItem.TABLE_NAME, buildPastaItemKey(guildId, alias));
            if (pasta.getItem() != null) {
                return pasta.getItem().get(PastaItem.PASTA).getS();
            }
            return "No copypasta with alias " + alias + " found.";
        } catch (Exception e) {
            return "An error has occurred. Please try again later";
        }
    }

    private PutItemRequest buildNewPasta(String guildId, String userId, String alias, String pasta) {
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put(PastaItem.KEY_EXPRESSION_SUBSTITUTION, PastaItem.KEY);
        PutItemRequest pastaRequest = new PutItemRequest()
                .withItem(buildPastaItemKey(guildId, alias))
                .withConditionExpression(CONDITION_EXPRESSION)
                .withExpressionAttributeNames(expressionAttributeNames);
        pastaRequest.setTableName(PastaItem.TABLE_NAME);
        pastaRequest.addItemEntry(PastaItem.OWNER, new AttributeValue().withS(userId));
        pastaRequest.addItemEntry(PastaItem.PASTA, new AttributeValue().withS(pasta));
        return pastaRequest;
    }
}
