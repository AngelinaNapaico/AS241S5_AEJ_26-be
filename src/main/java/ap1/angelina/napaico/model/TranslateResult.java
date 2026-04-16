package ap1.angelina.napaico.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "translate_results")
public class TranslateResult {

    @Id
    private String id;
    private String q;
    private String source;
    private String target;
    private Map<String, Object> rawResponse;
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public Map<String, Object> getRawResponse() { return rawResponse; }
    public void setRawResponse(Map<String, Object> rawResponse) { this.rawResponse = rawResponse; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
