package Flamingo.Listeners.Commands.Pasta;

import Flamingo.Listeners.Commands.AbstractCommandFactory;
import Flamingo.Listeners.Commands.Auth.AllowAll;
import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.CommandAction;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import java.util.HashMap;
import java.util.Map;

public class PastaFactory extends AbstractCommandFactory {

    private PastaManager pastaManager;

    public PastaFactory(Map<String, AuthStrategy> authStrategyMap) {
        super(authStrategyMap);
    }

    @Override
    public Pasta build() {
        Map<String, CommandAction> commandActionMap = new HashMap<>();
        commandActionMap.put(SavePastaCommand.COMMAND, new SavePastaCommand(authStrategyMap.get(AllowAll.class.getName()), pastaManager));
        commandActionMap.put(GetPastaCommand.COMMAND, new GetPastaCommand(authStrategyMap.get(AllowAll.class.getName()), pastaManager));
        return new Pasta(commandActionMap);
    }

    public void buildPastaManager(AmazonDynamoDB dynamoDB) {
        pastaManager = new PastaManager(dynamoDB);
    }
}
