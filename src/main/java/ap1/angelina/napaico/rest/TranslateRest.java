package ap1.angelina.napaico.rest;

import ap1.angelina.napaico.model.TranslateResult;
import ap1.angelina.napaico.service.TranslateService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/translate")
public class TranslateRest {

    private final TranslateService service;

    public TranslateRest(TranslateService service) {
        this.service = service;
    }

    /**
     * POST /api/translate
     * Traduce un texto y guarda el resultado en MongoDB.
     *
     * Ejemplo body:
     * {
     *   "q": "Hello World",
     *   "source": "en",
     *   "target": "es"
     * }
     */
    @PostMapping
    public Mono<TranslateResult> translate(@RequestBody TranslateRequest request) {
        return service.translateAndSave(request.q(), request.source(), request.target());
    }

    /** GET /api/translate - Retorna todas las traducciones guardadas */
    @GetMapping
    public Flux<TranslateResult> getAll() {
        return service.getAll();
    }

    public record TranslateRequest(String q, String source, String target) {}
}
