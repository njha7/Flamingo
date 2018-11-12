package Flamingo.Listeners.Commands.Strike;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;

import java.util.HashMap;
import java.util.Map;

public class StrikeItem {
    public static final String KEY_SEPARATOR = "!";
    public static final String KEY = "guild!user";
    public static final String TABLE_NAME = "FlamingoStrikes";
    public static final String EXPRESSION_SUBSTITUTION = ":s";
    public static final Map<String, AttributeValue> EXPRESSION_ATTRIBUTE_VALUES = new HashMap<>();
    public static final String STRIKES = "strikes";

    static {
        EXPRESSION_ATTRIBUTE_VALUES.put(EXPRESSION_SUBSTITUTION, new AttributeValue().withN("1"));
    }

    private static String buildKey(String guildId, String userId) {
        StringBuilder sb = new StringBuilder(guildId.length() + userId.length() + 1);
        sb.append(guildId);
        sb.append(KEY_SEPARATOR);
        sb.append(userId);
        return sb.toString();
    }

    private static Map<String, AttributeValue> buildStrikeItemKey(String guildId, String userId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(KEY, new AttributeValue().withS(buildKey(guildId, userId)));
        return key;
    }

    public static UpdateItemRequest buildStrike(String guildId, String userId) {
        UpdateItemRequest updateItemRequest = new UpdateItemRequest();
        updateItemRequest.setTableName(StrikeItem.TABLE_NAME);
        updateItemRequest.setKey(StrikeItem.buildStrikeItemKey(guildId, userId));
        updateItemRequest.setUpdateExpression("ADD " + STRIKES + " " + StrikeItem.EXPRESSION_SUBSTITUTION);
        updateItemRequest.setExpressionAttributeValues(StrikeItem.EXPRESSION_ATTRIBUTE_VALUES);
        updateItemRequest.setReturnValues(ReturnValue.UPDATED_NEW);
        return updateItemRequest;
    }
}
