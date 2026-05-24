package ap1.angelina.napaico.rest;

import ap1.angelina.napaico.model.TranslateResult;
import ap1.angelina.napaico.service.TranslateService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/translate")
@CrossOrigin(origins = "*")
public class TranslateRest {

    private final TranslateService service;

    public TranslateRest(TranslateService service) {
        this.service = service;
    }

    /** POST /api/translate — Traduce un texto y lo guarda en MongoDB */
    @PostMapping
    public Mono<TranslateResult> translate(@RequestBody TranslateRequest request) {
        return service.translateAndSave(request.q(), request.source(), request.target());
    }

    /** GET /api/translate — Lista todos los registros activos */
    @GetMapping
    public Flux<TranslateResult> getAll() {
        return service.getAll();
    }

    /** PUT /api/translate/{id} — Actualiza el texto, llama a la API y guarda el nuevo resultado */
    @PutMapping("/{id}")
    public Mono<TranslateResult> update(@PathVariable String id, @RequestBody TranslateRequest request) {
        return service.update(id, request.q(), request.source(), request.target());
    }

    /** DELETE /api/translate/{id} — Borrado lógico */
    @DeleteMapping("/{id}")
    public Mono<TranslateResult> delete(@PathVariable String id) {
        return service.delete(id);
    }

    public record TranslateRequest(String q, String source, String target) {}
}
