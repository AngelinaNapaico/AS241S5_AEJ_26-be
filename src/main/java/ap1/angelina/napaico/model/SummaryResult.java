package ap1.angelina.napaico.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "summary_results")
public class SummaryResult {

    @Id
    private String id;
    private String url;
    private int length;
    private String lang;
    private Map<String, Object> rawResponse;
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public String getLang() { return lang; }
    public void setLang(String lang) { this.lang = lang; }

    public Map<String, Object> getRawResponse() { return rawResponse; }
    public void setRawResponse(Map<String, Object> rawResponse) { this.rawResponse = rawResponse; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
