package abc.example.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient inventoryWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://api-gateway/api/inventory")
                .build();
    }

    @Bean
    public WebClient productWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://api-gateway/api/product")
                .build();
    }
}