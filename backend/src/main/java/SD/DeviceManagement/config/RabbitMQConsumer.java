package SD.DeviceManagement.config;

import SD.DeviceManagement.entities.CustomMessage;
import SD.DeviceManagement.services.DeviceService;
import SD.DeviceManagement.services.EnergyConsumptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConsumer implements CommandLineRunner {

    private final static String QUEUE_NAME = "queue";
    private EnergyConsumptionService energyConsumptionService;
    @Autowired
    public RabbitMQConsumer(EnergyConsumptionService energyConsumptionService)
    {
        this.energyConsumptionService=energyConsumptionService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setPort(5672);
            factory.setUri("amqps://mcexueyw:w39arss3lXQX9Z9Fvx6plqFPygRnDAnM@sparrow.rmq.cloudamqp.com/mcexueyw");
            factory.setPassword("w39arss3lXQX9Z9Fvx6plqFPygRnDAnM");

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                CustomMessage message = new ObjectMapper().readValue(delivery.getBody(),CustomMessage.class);
                energyConsumptionService.logRabbitEnergyCons(message);
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        }catch(IOException | TimeoutException | URISyntaxException | NoSuchAlgorithmException | KeyManagementException e)
        {
            e.printStackTrace();
        }
    }
}
