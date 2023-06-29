package com.ricardofaria.demokafka.service

import com.google.gson.Gson
import com.ricardofaria.demokafka.model.PriceMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PriceService(private val kafkaProducer: KafkaTemplate<String, Any>, @Value(value = "\${kafka.changeprice.topic}") private val changePriceTopic: String) {

    fun sendPrice(priceMessage: PriceMessage) {
        kafkaProducer.send(changePriceTopic, priceMessage.id.toString(), priceMessage)
    }


}