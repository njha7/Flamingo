package Flamingo.Listeners.Commands;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.Auth.TestAuthStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CommandTest {

    protected AuthStrategy authStrategy;
    protected CommandAction commandAction;
    protected AbstractCommand command;


    @Before
    public void setup() {
        authStrategy = new TestAuthStrategy();
        commandAction = new TestCommandAction(authStrategy);
        Map<String, CommandAction> commandActionMap = new HashMap<>();
        commandActionMap.put(TestCommand.COMMAND, commandAction);
        command = new TestCommand(commandActionMap);
    }

    @Test
    public void unauthorized_command() {

    }

}
