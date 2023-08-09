package com.ricardofaria.demokafka.messages

import com.ricardofaria.demokafka.model.PriceMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service


@Service
class PriceMessageConsumer: MessageListener<String, PriceMessage> {

    override fun onMessage(consumerRecord: ConsumerRecord<String, PriceMessage>) {
        val priceMessage = consumerRecord.value()
        if (priceMessage.price <= 0) {
            throw IllegalArgumentException("Invalid price received, message will not be consumed")
        }
        println("Received price message: $priceMessage")
    }

}