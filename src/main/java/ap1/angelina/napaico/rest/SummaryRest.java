package ap1.angelina.napaico.rest;

import ap1.angelina.napaico.model.SummaryResult;
import ap1.angelina.napaico.service.SummaryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/summary")
@CrossOrigin(origins = "*")
public class SummaryRest {

    private final SummaryService service;

    public SummaryRest(SummaryService service) {
        this.service = service;
    }

    /** POST /api/summary — Resume un texto y lo guarda en MongoDB */
    @PostMapping
    public Mono<SummaryResult> summarize(@RequestBody SummaryRequest request) {
        return service.summarizeAndSave(request.text(), request.length(), request.lang());
    }

    /** GET /api/summary — Lista todos los registros activos */
    @GetMapping
    public Flux<SummaryResult> getAll() {
        return service.getAll();
    }

    /** PUT /api/summary/{id} — Actualiza el texto, llama a la API y guarda el nuevo resultado */
    @PutMapping("/{id}")
    public Mono<SummaryResult> update(@PathVariable String id, @RequestBody SummaryRequest request) {
        return service.update(id, request.text(), request.length(), request.lang());
    }

    /** DELETE /api/summary/{id} — Borrado lógico */
    @DeleteMapping("/{id}")
    public Mono<SummaryResult> delete(@PathVariable String id) {
        return service.delete(id);
    }

    public record SummaryRequest(String text, int length, String lang) {}
}
