package com.ricardofaria.demokafka.messages

import com.ricardofaria.demokafka.model.PriceMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class PriceMessageConsumer: MessageListener<String, PriceMessage> {

    override fun onMessage(consumerRecord: ConsumerRecord<String, PriceMessage>) {
        val priceMessage = consumerRecord.value()
        if (priceMessage.price <= 0) {
            Thread.sleep(10_000)
            throw IllegalArgumentException("Invalid price received, message will not be consumed")
        }
        println("Received price message: $priceMessage")
    }

}