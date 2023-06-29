package com.ricardofaria.demokafka.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin


private const val NUMBER_OF_PARTITIONS = 2
private const val REPLICATION_FACTOR = 1

@Configuration
class KafkaTopicConfig(@Value(value = "\${spring.kafka.bootstrap-servers}") private val bootstrapServers: String,
                       @Value(value = "\${kafka.changeprice.topic}") private val changePriceTopic: String) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        return KafkaAdmin(configs)
    }

    @Bean
    fun topicDemoKafka(): NewTopic {
        return NewTopic(changePriceTopic, NUMBER_OF_PARTITIONS, REPLICATION_FACTOR.toShort())
    }

}