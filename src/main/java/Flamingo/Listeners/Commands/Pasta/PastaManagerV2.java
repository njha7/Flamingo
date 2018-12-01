package Flamingo.Listeners.Commands.Pasta;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

public class PastaManagerV2 extends PastaManager {

    private static final String CONDITION_EXPRESSION = "attribute_not_exists(" + PastaItemV2.HASH_KEY + ") and attribute_not_exists(" + PastaItemV2.RANGE_KEY + ")";
    private final AmazonDynamoDB dynamoDB;

    public PastaManagerV2(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
        this.dynamoDB = dynamoDB;
    }

    public String savePasta(String guildId, String userId, String alias, String pasta) {
        try {
            GetItemResult oldPasta = dynamoDB.getItem(PastaItem.TABLE_NAME, PastaItem.buildPastaItemKey(guildId, alias));
            if (oldPasta.getItem() == null) {
                dynamoDB.putItem(buildNewPasta(guildId, userId, alias, pasta));
            } else {
                return "Copypasta with alias " + alias + " already exists.";
            }
            return "Copypasta with alias " + alias + " has been saved.";
        } catch (ConditionalCheckFailedException e) {
            return "Copypasta with alias " + alias + " already exists.";
        } catch (Exception e) {
            return "An error has occurred. Please try again later";
        }
    }

    private void migratePasta(String guildId, String userId, String alias, String pasta) {
        try {
            dynamoDB.putItem(buildNewPasta(guildId, userId, alias, pasta));
        } finally {

        }
    }

    //Check the V2 Pasta Table, fail over to the original table.
    //If pasta is found in original table, save to V2.
    public String getPasta(String guildId, String alias) {
        try {
            GetItemResult pastaV2 = dynamoDB.getItem(PastaItemV2.TABLE_NAME, PastaItemV2.buildPastaItemKey(guildId, alias));
            if (pastaV2.getItem() != null && !pastaV2.getItem().isEmpty()) {
                return pastaV2.getItem().get(PastaItemV2.PASTA).getS();
            }
            GetItemResult pasta = dynamoDB.getItem(PastaItem.TABLE_NAME, PastaItem.buildPastaItemKey(guildId, alias));
            if (pasta.getItem() != null) {
                String owner = pasta.getItem().get(PastaItemV2.OWNER).getS();
                String text = pasta.getItem().get(PastaItemV2.PASTA).getS();
                try {
                    migratePasta(guildId, owner, alias, text);
                } finally {
                    return text;
                }
            }
            return "No copypasta with alias " + alias + " found.";
        } catch (Exception e) {
            return "An error has occurred. Please try again later";
        }
    }

    private PutItemRequest buildNewPasta(String guildId, String userId, String alias, String pasta) {
        PutItemRequest pastaRequest = new PutItemRequest()
                .withItem(PastaItemV2.buildPastaItemKey(guildId, alias))
                .withConditionExpression(CONDITION_EXPRESSION);
        pastaRequest.setTableName(PastaItemV2.TABLE_NAME);
        pastaRequest.addItemEntry(PastaItem.OWNER, new AttributeValue().withS(userId));
        pastaRequest.addItemEntry(PastaItem.PASTA, new AttributeValue().withS(pasta));
        return pastaRequest;
    }
}
