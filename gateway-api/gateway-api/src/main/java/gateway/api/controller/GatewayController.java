package gateway.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import gateway.api.service.RealTimeDataService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final RealTimeDataService realTimeDataService;
    private final WebClient webClient;

    public GatewayController(RealTimeDataService realTimeDataService) {
        this.realTimeDataService = realTimeDataService;
        
        this.webClient = WebClient.builder().baseUrl("http://localhost:8082").build();
    }

    // ... o resto do código permanece o mesmo
    @GetMapping(value = "/tempo-real", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getDadosTempoReal() {
        System.out.println("getDadosTempoReal");
        return realTimeDataService.getRealTimeDataFlux();
    }

    @GetMapping("/dashboard")
    public Flux<String> getDashboardData() {
        System.out.println("getDashboardData");
        return webClient.get()
                .uri("/dashboard")
                .retrieve()
                .bodyToFlux(String.class);
    }
}