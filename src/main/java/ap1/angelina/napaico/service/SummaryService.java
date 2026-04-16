package ap1.angelina.napaico.service;

import ap1.angelina.napaico.model.SummaryResult;
import ap1.angelina.napaico.repository.SummaryResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import java.util.Map;

@Service
public class SummaryService {

    private final WebClient webClient;
    private final SummaryResultRepository repository;

    @Value("${rapidapi.key}")
    private String apiKey;

    @Value("${rapidapi.summarizer.host}")
    private String host;

    @Value("${rapidapi.summarizer.url}")
    private String url;

    public SummaryService(WebClient webClient, SummaryResultRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    /**
     * Extrae y resume el artículo de la URL dada, luego guarda el resultado en MongoDB.
     *
     * @param articleUrl URL del artículo a resumir
     * @param length     longitud del resumen en párrafos (0 = automático)
     * @param lang       idioma del resumen (ej: "en", "es")
     */
    public Mono<SummaryResult> summarizeAndSave(String articleUrl, int length, String lang) {
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("url", articleUrl)
                .queryParam("length", length)
                .queryParam("lang", lang)
                .build(false)
                .encode()
                .toUriString();

        return webClient.get()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("x-rapidapi-host", host)
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(e -> e instanceof WebClientResponseException &&
                                ((WebClientResponseException) e).getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE))
                .flatMap(response -> {
                    SummaryResult result = new SummaryResult();
                    result.setUrl(articleUrl);
                    result.setLength(length);
                    result.setLang(lang);
                    result.setRawResponse((Map<String, Object>) response);
                    return repository.save(result);
                });
    }

    public Flux<SummaryResult> getAll() {
        return repository.findAll();
    }
}
