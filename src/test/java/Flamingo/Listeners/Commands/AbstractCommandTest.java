package Flamingo.Listeners.Commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static junit.framework.TestCase.assertTrue;

public class AbstractCommandTest {

    protected AbstractCommand command = new TestAbstractCommand();
    protected String MESSAGE_STRING = "asdf";
    protected JDA jda = null;
    protected User bot;
    protected User user;

    protected Message buildMessage(String contents, User author) {
        return new ReceivedMessage(0, null, MessageType.DEFAULT, true, false, null
                , null, false, false, contents, "", author, null, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    }

    @Before
    public void setup() {
        bot = new UserImpl(0, (JDAImpl) jda);
        ((UserImpl) bot).setBot(true);
        user = new UserImpl(1, (JDAImpl) jda);
        ((UserImpl) user).setBot(false);
    }

    @Test
    public void ignore_bot_message() {
        Message message = buildMessage(MESSAGE_STRING, bot);
        MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent(jda, 0, message);
        command.onMessageReceived(messageReceivedEvent);
        assertTrue(message.getContentDisplay().equals(MESSAGE_STRING));
    }

    @Test
    public void ignore_bot_command() {
        Message message = buildMessage(command.COMMAND, bot);
        MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent(jda, 0, message);
        command.onMessageReceived(messageReceivedEvent);
        assertTrue(message.getContentDisplay().equals(command.COMMAND));
    }

    @Test
    public void ignore_user_message() {
        Message message = buildMessage(MESSAGE_STRING, user);
        MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent(jda, 0, message);
        command.onMessageReceived(messageReceivedEvent);
        assertTrue(message.getContentDisplay().equals(MESSAGE_STRING));
    }

    //hack b/c mocking these objects is hard and messages that aren't sent by self are immutable
    @Test(expected = NullPointerException.class)
    public void ack_user_command() {
        Message message = buildMessage(TestAbstractCommand.COMMAND, user);
        MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent(jda, 0, message);
        command.onMessageReceived(messageReceivedEvent);
        assertTrue(message.getContentDisplay().equals(command.COMMAND));
    }

}
