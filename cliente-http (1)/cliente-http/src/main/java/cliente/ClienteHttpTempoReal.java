package cliente;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ClienteHttpTempoReal {

    private static final String URL_GATEWAY = "http://localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        System.out.println("=== CLIENTE HTTP TEMPO REAL ===");
        System.out.println("Consumidor para Tempo Real (streaming com SSE)");
        System.out.println("Conectando para receber dados em tempo real...\n");

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GATEWAY + "/tempo-real"))
                    .header("Accept", "text/event-stream")
                    .build();

            CompletableFuture<Void> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                    .thenAccept(response -> {
                        System.out.println("Conexão SSE estabelecida. Aguardando dados...\n");
                        response.body().forEach(line -> {
                            if (line.startsWith("data:")) {
                                String data = line.substring(5).trim();
                                System.out.println("[TEMPO REAL] " + data);
                            } else if (line.startsWith("event:")) {
                                String event = line.substring(6).trim();
                                System.out.println("[EVENTO] " + event);
                            } else if (!line.isEmpty()) {
                                System.out.println("[SSE] " + line);
                            }
                        });
                    });

            // Mantém o processo vivo
            future.join();
            
        } catch (Exception e) {
            System.err.println("Erro ao conectar para dados em tempo real: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 