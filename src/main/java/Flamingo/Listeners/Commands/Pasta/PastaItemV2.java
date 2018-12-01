package Flamingo.Listeners.Commands.Pasta;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class PastaItemV2 {
    public static final String HASH_KEY = "guild";
    public static final String RANGE_KEY = "alias";
    public static final String TABLE_NAME = "FlamingoPasta";
    public static final String PASTA_EXPRESSION_SUBSTITUTION = ":p";
    public static final String OWNER_EXPRESSION_SUBSTITUTION = ":o";
    public static final String OWNER = "owner";
    public static final String PASTA = "pasta";

    public static Map<String, AttributeValue> buildPastaItemKey(String guildId, String alias) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(HASH_KEY, new AttributeValue().withS(guildId));
        key.put(RANGE_KEY, new AttributeValue().withS(alias));
        return key;
    }
}
