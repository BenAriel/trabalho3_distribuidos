package cliente;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class ClienteHttpDashboard {

    private static final String URL_GATEWAY = "http://localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        System.out.println("=== CLIENTE HTTP DASHBOARD ===");
        System.out.println("Consumidor para Dashboard (sob demanda)");
        System.out.println("Fazendo polling a cada 15 segundos...\n");

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(URL_GATEWAY + "/dashboard"))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println("\n--- DADOS DO DASHBOARD (obtido via API Gateway) ---");
                    System.out.println(response.body());
                    System.out.println("--------------------------------------------------\n");
                    Thread.sleep(15000); // Pede atualização a cada 15 segundos
                }
            } catch (Exception e) {
                System.err.println("Erro ao consumir dados do dashboard: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
} 