package ap1.angelina.napaico.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repositorio base reactivo reutilizado por YoutubeResult y TranslateResult.
 * Cada documento usa su propia colección definida en @Document.
 */
@NoRepositoryBean
public interface ApiResultRepository<T> extends ReactiveMongoRepository<T, String> {
}
