package Flamingo.Listeners.Commands.Pasta;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class PastaItem {

    public static final String KEY_SEPARATOR = "!";
    public static final String KEY = "guild!user";
    public static final String TABLE_NAME = "FlamingoStrikes";
    public static final String KEY_EXPRESSION_SUBSTITUTION = "#k";
    public static final String PASTA_EXPRESSION_SUBSTITUTION = ":p";
    public static final String OWNER_EXPRESSION_SUBSTITUTION = ":o";
    public static final String OWNER = "owner";
    public static final String PASTA = "pasta";

    private static String buildKey(String guildId, String alias) {
        StringBuilder sb = new StringBuilder(guildId.length() + alias.length() + 1);
        sb.append(guildId);
        sb.append(KEY_SEPARATOR);
        sb.append(alias);
        return sb.toString();
    }

    public static Map<String, AttributeValue> buildPastaItemKey(String guildId, String alias) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(KEY, new AttributeValue().withS(buildKey(guildId, alias)));
        return key;
    }

}
