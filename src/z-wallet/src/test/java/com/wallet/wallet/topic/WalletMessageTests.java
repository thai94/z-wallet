package com.wallet.wallet.topic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletMessageTests {

    @LocalServerPort
    private int port;

    static final String WEBSOCKET_TOPIC = "/ws/topic/updated_wallet";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @Before
    public void setup() {



        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        String WEBSOCKET_URI = String.format("ws://localhost:%s/ws-stomp", port);
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);

        Assert.assertEquals(true, session.isConnected());

        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        WalletMessage message = new WalletMessage("1", 100);

        session.send(WEBSOCKET_TOPIC, message.toString().getBytes());
        System.out.println(message.toString());
        Assert.assertEquals(message, blockingQueue.poll(5, SECONDS));
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }


}
