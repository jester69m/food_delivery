import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.notification.dto.OrderCreateMessage;
import com.notification.dto.OrderLineMessage;
import com.notification.dto.UserRequest;
import com.notification.service.SendEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SendEmailServiceTest {

    @InjectMocks
    private SendEmailService sendEmailService;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        listAppender = new ListAppender<>();
        listAppender.start();
        Logger logger = (Logger) LoggerFactory.getLogger(SendEmailService.class);
        logger.addAppender(listAppender);
    }

    @Test
    void sendEmail_ValidUserRequest_SuccessfullySendsWelcomeEmail() {
        UserRequest userRequest = new UserRequest("test@example.com", "first", "last");

        sendEmailService.sendEmail(userRequest);

        List<ILoggingEvent> logsList = listAppender.list;
        assertThat(logsList).hasSize(1);
        assertThat(logsList.get(0).getFormattedMessage()).isEqualTo("Sending email to test@example.com with message 'Welcome in food_delivery, first last!'");
    }

    @Test
    void sendLoginEmail_ValidEmail_SuccessfullySendsLoginConfirmationEmail() {
        String email = "test@example.com";

        sendEmailService.sendLoginEmail(email);

        List<ILoggingEvent> logsList = listAppender.list;
        assertThat(logsList).hasSize(1);
        assertThat(logsList.get(0).getFormattedMessage()).isEqualTo("Sending email to test@example.com with message 'You have logged in!'");
    }

    @Test
    void sendCreateOrderEmail_ValidOrderCreateMessage_LogsOrderCreation() {
        LocalDateTime orderDate = LocalDateTime.now();
        OrderCreateMessage orderCreateMessage = new OrderCreateMessage(123456L, "test@example.com", new HashSet<>(Set.of(
                new OrderLineMessage(1L, 1L, 3),
                new OrderLineMessage(1L, 2L, 2),
                new OrderLineMessage(1L, 3L, 1)
        )), orderDate, 100.0);

        sendEmailService.sendCreateOrderEmail(orderCreateMessage);

        List<ILoggingEvent> logsList = listAppender.list;
        assertThat(logsList).hasSize(1);
        assertThat(logsList.get(0).getFormattedMessage()).isEqualTo("Order created with id 123456 for user test@example.com with content " + orderCreateMessage);
    }
}