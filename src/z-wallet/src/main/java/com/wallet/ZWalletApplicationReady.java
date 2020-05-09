package com.wallet;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class ZWalletApplicationReady {

    static final String WEBSOCKET_TOPIC = "/ws/topic/updated_wallet";
    WebSocketStompClient stompClient;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() throws InterruptedException, ExecutionException, TimeoutException {
        stompClient = new WebSocketStompClient(new SockJsClient(
                Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))));

        String WEBSOCKET_URI = String.format("ws://localhost:%s/ws-stomp", 8080);

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);

        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());
        session.subscribe("/ws/topic/notify_1584784717714", new DefaultStompFrameHandler());
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            System.out.println("========= Websocket msg: " + new String((byte[]) o));
        }
    }
}
