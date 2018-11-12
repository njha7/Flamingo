package Flamingo.Listeners.Commands.Strike;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;

import java.util.HashMap;
import java.util.Map;

public class StrikeManager {
    private final AmazonDynamoDB dynamoDB;

    public StrikeManager(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    public String strike(String guildId, String userId) {
        try {
            UpdateItemResult strikeResult = dynamoDB.updateItem(buildStrike(guildId, userId));
            String strikes = strikeResult.getAttributes().get(StrikeItem.STRIKES).getN();
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("<@" + userId + ">");
            messageBuilder.append(" has " + strikes + " strikes.");
            return messageBuilder.toString();
        } catch (Exception e) {
            return "An error has occurred. Please try again later";
        }
    }

    public String clearStrikes(String guildId, String userId) {
        try {
            UpdateItemResult updateItemRequest = dynamoDB.updateItem(buildClearstrike(guildId, userId));
            String strikes = updateItemRequest.getAttributes().get(StrikeItem.STRIKES).getN();
            System.out.println(strikes);
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append(" Strikes for ");
            messageBuilder.append("<@" + userId + ">");
            messageBuilder.append(" have been reset.");
            return messageBuilder.toString();
        } catch (Exception e) {
            return "An error has occurred. Please try again later";
        }
    }

    private String buildKey(String guildId, String userId) {
        StringBuilder sb = new StringBuilder(guildId.length() + userId.length() + 1);
        sb.append(guildId);
        sb.append(StrikeItem.KEY_SEPARATOR);
        sb.append(userId);
        return sb.toString();
    }

    private Map<String, AttributeValue> buildStrikeItemKey(String guildId, String userId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(StrikeItem.KEY, new AttributeValue().withS(buildKey(guildId, userId)));
        return key;
    }

    private UpdateItemRequest buildStrike(String guildId, String userId) {
        UpdateItemRequest updateItemRequest = new UpdateItemRequest();
        updateItemRequest.setTableName(StrikeItem.TABLE_NAME);
        updateItemRequest.setKey(buildStrikeItemKey(guildId, userId));
        updateItemRequest.setUpdateExpression("ADD " + StrikeItem.STRIKES + " " + StrikeItem.EXPRESSION_SUBSTITUTION);
        updateItemRequest.setExpressionAttributeValues(StrikeItem.STRIKE_INCREMENT_VALUE);
        updateItemRequest.setReturnValues(ReturnValue.UPDATED_NEW);
        return updateItemRequest;
    }

    private UpdateItemRequest buildClearstrike(String guildId, String userId) {
        UpdateItemRequest updateItemRequest = new UpdateItemRequest();
        updateItemRequest.setTableName(StrikeItem.TABLE_NAME);
        updateItemRequest.setKey(buildStrikeItemKey(guildId, userId));
        updateItemRequest.setUpdateExpression("SET " + StrikeItem.STRIKES + "=" + StrikeItem.EXPRESSION_SUBSTITUTION);
        updateItemRequest.setExpressionAttributeValues(StrikeItem.STRIKE_RESET_VALUE);
        updateItemRequest.setReturnValues(ReturnValue.UPDATED_NEW);
        return updateItemRequest;
    }
}
