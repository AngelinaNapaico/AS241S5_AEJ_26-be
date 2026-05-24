package ap1.angelina.napaico.service;

import ap1.angelina.napaico.model.SummaryResult;
import ap1.angelina.napaico.repository.SummaryResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class SummaryService {

    private final WebClient webClient;
    private final SummaryResultRepository repository;

    @Value("${rapidapi.key}")
    private String apiKey;

    @Value("${rapidapi.summarizer.host}")
    private String host;

    @Value("${rapidapi.summarizer.text-url}")
    private String textUrl;

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
    public Mono<SummaryResult> summarizeAndSave(String text, int length, String lang) {
        Map<String, Object> body = Map.of(
                "text", text,
                "length", length,
                "lang", lang
        );

        return webClient.post()
                .uri(textUrl)
                .header("Content-Type", "application/json")
                .header("x-rapidapi-host", host)
                .header("x-rapidapi-key", apiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    SummaryResult result = new SummaryResult();
                    result.setText(text.substring(0, Math.min(text.length(), 100)));
                    result.setLength(length);
                    result.setLang(lang);
                    result.setRawResponse((Map<String, Object>) response);
                    return repository.save(result);
                });
    }

    public Flux<SummaryResult> getAll() {
        return repository.findAll().filter(r -> !r.isDeleted());
    }

    /** Actualiza el texto y vuelve a llamar a la API con el nuevo contenido */
    public Mono<SummaryResult> update(String id, String text, int length, String lang) {
        return repository.findById(id)
                .flatMap(existing -> {
                    Map<String, Object> body = Map.of("text", text, "length", length, "lang", lang);
                    return webClient.post()
                            .uri(textUrl)
                            .header("Content-Type", "application/json")
                            .header("x-rapidapi-host", host)
                            .header("x-rapidapi-key", apiKey)
                            .bodyValue(body)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .flatMap(response -> {
                                existing.setText(text.substring(0, Math.min(text.length(), 100)));
                                existing.setLength(length);
                                existing.setLang(lang);
                                existing.setRawResponse((Map<String, Object>) response);
                                return repository.save(existing);
                            });
                });
    }

    /** Borrado lógico: marca el registro como eliminado sin borrarlo de MongoDB */
    public Mono<SummaryResult> delete(String id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setDeleted(true);
                    return repository.save(existing);
                });
    }
}
