package ap1.angelina.napaico.rest;

import ap1.angelina.napaico.model.SummaryResult;
import ap1.angelina.napaico.service.SummaryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/summary")
public class SummaryRest {

    private final SummaryService service;

    public SummaryRest(SummaryService service) {
        this.service = service;
    }

    /**
     * POST /api/summary
     * Resume el artículo de la URL dada y guarda el resultado en MongoDB.
     *
     * Ejemplo body:
     * {
     *   "url": "https://time.com/6286679/musk-ai-open-letter/",
     *   "length": 3,
     *   "lang": "en"
     * }
     */
    @PostMapping
    public Mono<SummaryResult> summarize(@RequestBody SummaryRequest request) {
        return service.summarizeAndSave(request.url(), request.length(), request.lang());
    }

    /** GET /api/summary - Retorna todos los resúmenes guardados */
    @GetMapping
    public Flux<SummaryResult> getAll() {
        return service.getAll();
    }

    public record SummaryRequest(String url, int length, String lang) {}
}
