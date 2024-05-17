package com.notification.service;

import com.notification.dto.OrderCreateMessage;
import com.notification.dto.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendEmailService {

    @RabbitListener(queues = "q.user-registration")
    public void sendEmail(UserRequest userRequest) {
        if(userRequest == null) {
            log.error("UserRequest is null");
        } else if (userRequest.getEmail() == null) {
            log.error("Email is null");
        } else if(userRequest.getFirstName() == null || userRequest.getLastName() == null) {
            log.info(String.format("Sending email to %s with message '%s'", userRequest.getEmail(), "Welcome in food_delivery!"));
        } else {
            log.info(String.format("Sending email to %s with message '%s'", userRequest.getEmail(), "Welcome in food_delivery, " + userRequest.getFirstName() + " " + userRequest.getLastName() + "!"));
        }
    }

    @RabbitListener(queues = "q.user-login")
    public void sendLoginEmail(String email) {
        if(email == null) {
            log.error("Email is null");
        } else {
            log.info(String.format("Sending email to %s with message '%s'", email, "You have logged in!"));
        }
    }

    @RabbitListener(queues = "q.order")
    public void sendCreateOrderEmail(OrderCreateMessage message) {
        if(message == null) {
            log.error("Message is null");
        } else if(message.getUserEmail() == null) {
            log.info(String.format("Order created with id %s with content %s", message.getOrderId(), message));
        } else {
            log.info(String.format("Order created with id %s for user %s with content %s", message.getOrderId(), message.getUserEmail(), message));
        }
    }
}
