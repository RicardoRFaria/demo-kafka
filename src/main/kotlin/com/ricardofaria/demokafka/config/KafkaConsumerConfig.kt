package com.ricardofaria.demokafka.config

import com.ricardofaria.demokafka.messages.PriceMessageConsumer
import com.ricardofaria.demokafka.model.PriceMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer


@EnableKafka
@Configuration
class KafkaConsumerConfig(
    @Value(value = "\${spring.kafka.bootstrap-servers}") private val bootstrapServers: String,
    @Value(value = "\${kafka.consumer.group-id}") private val consumerGroupId: String,
    @Value(value = "\${kafka.changeprice.topic}") private val changePriceTopic: String,
) {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val consumerConfig: MutableMap<String, Any> = HashMap()
        consumerConfig[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        consumerConfig[ConsumerConfig.GROUP_ID_CONFIG] = consumerGroupId
        val deserializer = JsonDeserializer<Any>()
        deserializer.addTrustedPackages("com.ricardofaria.demokafka.model")

        return DefaultKafkaConsumerFactory(
            consumerConfig,
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun messageListenerContainer(consumerFactory:  ConsumerFactory<String, Any>, priceMessageConsumer: PriceMessageConsumer): ConcurrentMessageListenerContainer<*, *> {
        val containerProperties = ContainerProperties(changePriceTopic)
        containerProperties.messageListener = priceMessageConsumer

        val container: ConcurrentMessageListenerContainer<*, *> = ConcurrentMessageListenerContainer(
            consumerFactory,
            containerProperties
        )
        container.start()
        return container
    }

}