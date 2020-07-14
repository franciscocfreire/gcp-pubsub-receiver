package com.example.gcReceiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReceiveMessage {

    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "exampleSubscription");
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }

    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public void messageReceiver(String payload) {
        log.info("Message arrived! Payload: {} ", payload);
    }


}
