# Clientes HTTP Separados

Este projeto agora possui **dois clientes HTTP separados** que funcionam como microserviços independentes, conforme a arquitetura do projeto.

## 📁 Estrutura dos Clientes

### 1. ClienteHttpDashboard
- **Função**: Consumidor para Dashboard (polling sob demanda)
- **Tipo**: Cliente HTTP síncrono
- **Comportamento**: Faz requisições GET a cada 15 segundos para obter dados do dashboard
- **Endpoint**: `http://localhost:8080/api/dashboard`

### 2. ClienteHttpTempoReal
- **Função**: Consumidor para Tempo Real (streaming com SSE)
- **Tipo**: Cliente HTTP async
- **Comportamento**: Conecta via Server-Sent Events (SSE) para receber dados em tempo real
- **Endpoint**: `http://localhost:8080/api/tempo-real`

## 🚀 Como Executar

### Pré-requisitos
1. Certifique-se de que todos os serviços estão rodando:
   - Drones (publicando dados)
   - Gateway principal (processando dados)
   - API Gateway (servindo endpoints)
   - Base de dados (armazenando dados)

### Executando os Clientes

**IMPORTANTE**: Cada cliente deve ser executado em um **terminal separado**!

#### Terminal 1 - Cliente Dashboard:
```bash
cd cliente-http (1)/cliente-http
mvn compile
java -cp target/classes cliente.ClienteHttpDashboard
```

#### Terminal 2 - Cliente Tempo Real:
```bash
cd cliente-http (1)/cliente-http
mvn compile
java -cp target/classes cliente.ClienteHttpTempoReal
```

## 🔧 Teste de Conectividade

Se houver problemas, execute o teste de conectividade:

```bash
cd cliente-http (1)/cliente-http
mvn compile
java -cp target/classes cliente.TesteConectividade
```

## 📊 Arquitetura

```
Drones → Gateway Principal → API Gateway → Clientes
                                    ↓
                            ┌─────────────┐
                            │   Cliente   │
                            │  Dashboard  │
                            │  (Polling)  │
                            └─────────────┘
                                    ↓
                            ┌─────────────┐
                            │   Cliente   │
                            │  Tempo Real │
                            │   (SSE)     │
                            └─────────────┘
```

## 🔍 Sobre SSE (Server-Sent Events)

O **ClienteHttpTempoReal** usa **SSE** (Server-Sent Events), que é adequado para:
- **Cliente HTTP Async**: ✅ Sim, SSE é uma tecnologia HTTP assíncrona
- **Cliente CoAP**: ❌ Não, CoAP é um protocolo diferente

SSE permite:
- Receber dados em tempo real sem polling
- Conexão persistente HTTP
- Streaming de dados do servidor para o cliente
- Reconexão automática em caso de perda de conexão

## 🐛 Troubleshooting

### Problema: Clientes recebendo 0 dados

1. **Verifique se os drones estão publicando**:
   ```
   Drone [norte] publicou em [drones/norte/dados]: 1001,86-493,04-19,86-64,24
   ```

2. **Verifique se o Gateway está processando**:
   ```
   Processing data from norte
   ```

3. **Verifique se a API Gateway está funcionando**:
   ```
   API Gateway conectada ao MQTT para dados em tempo real.
   ```

4. **Execute o teste de conectividade**:
   ```bash
   java -cp target/classes cliente.TesteConectividade
   ```

### Problema: Erro de conexão

- Verifique se a API Gateway está rodando na porta 8080
- Verifique se o Gateway principal está rodando na porta 8082
- Verifique se o MQTT broker está rodando na porta 1883

## 📝 Logs Esperados

### Cliente Dashboard:
```
=== CLIENTE HTTP DASHBOARD ===
Consumidor para Dashboard (sob demanda)
Fazendo polling a cada 15 segundos...

--- DADOS DO DASHBOARD (obtido via API Gateway) ---
--- Dashboard de Dados (Servido pelo Gateway Principal) ---
Total de dados coletados: 42

- NORTE: 25.00% (10 dados)
- SUL: 23.81% (10 dados)
- LESTE: 26.19% (11 dados)
- OESTE: 25.00% (10 dados)
--------------------------------------------------
```

### Cliente Tempo Real:
```
=== CLIENTE HTTP TEMPO REAL ===
Consumidor para Tempo Real (streaming com SSE)
Conectando para receber dados em tempo real...

Conexão SSE estabelecida. Aguardando dados...

[TEMPO REAL] [norte | 25.30 | 65.20 | 1012.45 | 450.30]
[TEMPO REAL] [sul | 28.15 | 72.80 | 1008.90 | 380.45]
[TEMPO REAL] [leste | 26.70 | 68.90 | 1015.20 | 520.10]
[TEMPO REAL] [oeste | 24.80 | 61.30 | 1010.75 | 410.25]
``` 