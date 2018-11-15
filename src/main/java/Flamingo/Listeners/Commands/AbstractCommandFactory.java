package Flamingo.Listeners.Commands;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;

import java.util.Map;

public abstract class AbstractCommandFactory {

    protected Map<String, AuthStrategy> authStrategyMap;

    public AbstractCommandFactory(Map<String, AuthStrategy> authStrategyMap) {
        this.authStrategyMap = authStrategyMap;
    }

    public abstract AbstractCommand build();
}
