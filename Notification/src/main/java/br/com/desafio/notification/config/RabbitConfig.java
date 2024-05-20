package br.com.desafio.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static br.com.desafio.notification.constant.RabbitConstants.*;

@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;

    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Queue reservedConfirmationQueue() {
        return QueueBuilder
                .nonDurable(RESERVATION_QUEUE)
                .deadLetterExchange(RESERVATION_EXCHANGE_DLQ)
                .build();
    }

    @Bean
    public Queue deadLetterQueueReservedConfirmationQueue() {
        return QueueBuilder
                .nonDurable(RESERVATION_QUEUE_DLQ)
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(RESERVATION_EXCHANGE)
                .build();
    }

    @Bean
    public FanoutExchange deadLetterFanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(RESERVATION_EXCHANGE_DLQ)
                .build();
    }

    @Bean
    public Binding bindReservedConfirmation() {
        return BindingBuilder
                .bind(reservedConfirmationQueue())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindDlxReservedConfirmation() {
        return BindingBuilder
                .bind(deadLetterQueueReservedConfirmationQueue())
                .to(deadLetterFanoutExchange());
    }
}
