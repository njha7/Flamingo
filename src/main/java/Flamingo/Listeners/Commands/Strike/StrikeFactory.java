package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommandFactory;
import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.Auth.GreaterThanOrEqualTo;
import Flamingo.Listeners.Commands.Auth.Moderator;
import Flamingo.Listeners.Commands.CommandAction;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import java.util.HashMap;
import java.util.Map;

public class StrikeFactory extends AbstractCommandFactory {

    private StrikeManager strikeManager;

    public StrikeFactory(Map<String, AuthStrategy> authStrategyMap) {
        super(authStrategyMap);
    }

    public Strike build() {
        Map<String, CommandAction> commandActionMap = new HashMap<>();
        commandActionMap.put(StrikeCommand.COMMAND, new StrikeCommand(authStrategyMap.get(GreaterThanOrEqualTo.class.getName()), strikeManager));
        commandActionMap.put(ClearStrikesCommand.COMMAND, new ClearStrikesCommand(authStrategyMap.get(Moderator.class.getName()), strikeManager));
        commandActionMap.put(QueryStrikeCommand.COMMAND, new QueryStrikeCommand(strikeManager));
        return new Strike(commandActionMap);
    }

    public void buildStrikeManager(AmazonDynamoDB dynamoDB) {
        strikeManager = new StrikeManager(dynamoDB);
    }
}

