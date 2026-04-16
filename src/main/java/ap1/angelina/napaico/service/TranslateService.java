package ap1.angelina.napaico.service;

import ap1.angelina.napaico.model.TranslateResult;
import ap1.angelina.napaico.repository.TranslateResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class TranslateService {

    private final WebClient webClient;
    private final TranslateResultRepository repository;

    @Value("${rapidapi.key}")
    private String apiKey;

    @Value("${rapidapi.translate.host}")
    private String host;

    @Value("${rapidapi.translate.url}")
    private String url;

    public TranslateService(WebClient webClient, TranslateResultRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    /**
     * Traduce un texto usando Deep Translate y guarda el resultado en MongoDB.
     *
     * @param q      texto a traducir
     * @param source idioma origen (ej: "en"), o "auto" para detección automática
     * @param target idioma destino (ej: "es")
     */
    public Mono<TranslateResult> translateAndSave(String q, String source, String target) {
        Map<String, Object> body = Map.of(
                "q", q,
                "source", source,
                "target", target
        );

        return webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .header("x-rapidapi-host", host)
                .header("x-rapidapi-key", apiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    TranslateResult result = new TranslateResult();
                    result.setQ(q);
                    result.setSource(source);
                    result.setTarget(target);
                    result.setRawResponse((Map<String, Object>) response);
                    return repository.save(result);
                });
    }

    public Flux<TranslateResult> getAll() {
        return repository.findAll();
    }
}
