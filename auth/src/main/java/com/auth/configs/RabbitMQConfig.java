package com.auth.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String USER_EXCHANGE = "user.exchange";
    private static final String REGISTRATION_QUEUE = "q.user-registration";
    private static final String LOGIN_QUEUE = "q.user-login";


    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(USER_EXCHANGE);
    }

    @Bean
    public Queue registrationQueue() {
        return new Queue(REGISTRATION_QUEUE);
    }

    @Bean
    public Binding registrationBinding(Queue registrationQueue, DirectExchange userExchange) {
        return BindingBuilder.bind(registrationQueue).to(userExchange).with(REGISTRATION_QUEUE);
    }

    @Bean
    public Queue loginQueue() {
        return new Queue(LOGIN_QUEUE);
    }

    @Bean
    public Binding loginBinding(Queue loginQueue, DirectExchange userExchange) {
        return BindingBuilder.bind(loginQueue).to(userExchange).with(LOGIN_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
