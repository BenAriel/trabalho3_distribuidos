package gateway.api.service;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import jakarta.annotation.PostConstruct;

@Service
public class RealTimeDataService {

    private final String broker = "tcp://localhost:1883";
    private final String topic = "gateway/dados_processados/#";
    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    @PostConstruct
    public void connectMqtt() throws MqttException {
        MqttClient client = new MqttClient(broker, "api-gateway-realtime-subscriber");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("API Gateway - Conexão MQTT perdida: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String payload = new String(message.getPayload());
                sink.tryEmitNext(payload);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });

        client.connect(options);
        client.subscribe(topic);
        System.out.println("API Gateway conectada ao MQTT para dados em tempo real.");
    }

    public Flux<String> getRealTimeDataFlux() {
        return sink.asFlux();
    }
}