package com.practicalunittesting.chp05.types;

import com.practicalunittesting.chp05.types.types.Client;
import com.practicalunittesting.chp05.types.types.MailServer;
import com.practicalunittesting.chp05.types.types.Messenger;
import com.practicalunittesting.chp05.types.types.TemplateEngine;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MessengerTest {
    private final MailServer mailServer = mock(MailServer.class);
    private final TemplateEngine templateEngine = mock(TemplateEngine.class);
    private final Client client = mock(Client.class);
    private final  static String EMAIL = "example@mail.ru";
    private final  static String CONTENT = "I am Russian";
    private final  static String NEW_CONTENT = "I not love anime";
    private Messenger messenger = new Messenger(mailServer, templateEngine);

    @Test
    void testIntegration(){
        when(client.getEmail()).thenReturn(EMAIL);
        when(templateEngine.prepareMessage(CONTENT, client)).thenReturn(NEW_CONTENT);
        verify(templateEngine, never()).prepareMessage(CONTENT, client);
        verify(mailServer, never()).send(EMAIL, CONTENT);
        messenger.sendMessage(client, CONTENT);
        verify(templateEngine).prepareMessage(CONTENT, client);
        verify(mailServer).send(EMAIL, NEW_CONTENT);

//        when(client.getEmail()).thenReturn(EMAIL);
//        verify(client, never()).getEmail();
//        System.out.println(client.getEmail());
//        verify(client, times(1)).getEmail();
//        System.out.println(client.getEmail());
//        verify(client, times(2)).getEmail();

    }
}
