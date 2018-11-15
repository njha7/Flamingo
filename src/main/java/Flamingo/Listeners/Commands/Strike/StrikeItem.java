package Flamingo.Listeners.Commands.Strike;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class StrikeItem {
    public static final String KEY_SEPARATOR = "!";
    public static final String KEY = "guild!user";
    public static final String TABLE_NAME = "FlamingoStrikes";
    public static final String EXPRESSION_SUBSTITUTION = ":s";
    public static final Map<String, AttributeValue> STRIKE_INCREMENT_VALUE = new HashMap<>();
    public static final String STRIKES = "strikes";
    public static final Map<String, AttributeValue> STRIKE_RESET_VALUE = new HashMap<>();

    static {
        STRIKE_INCREMENT_VALUE.put(EXPRESSION_SUBSTITUTION, new AttributeValue().withN("1"));
        STRIKE_RESET_VALUE.put(EXPRESSION_SUBSTITUTION, new AttributeValue().withN("0"));
    }
}
