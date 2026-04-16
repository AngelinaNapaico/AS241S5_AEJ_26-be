# AS241S5_AEJ_##-be — Angelina Napaico

Proyecto Spring WebFlux + MongoDB que consume 2 APIs IA desde RapidAPI, almacena los resultados en una base de datos Cloud (MongoDB Atlas).

---

## ✅ APIs IA utilizadas

### 1. Article Extractor and Summarizer (RapidAPI)
- **Proveedor:** restyler en RapidAPI
- **Endpoint usado:** `GET /summarize` — extrae y resume el contenido de cualquier artículo web
- **Host:** `article-extractor-and-summarizer.p.rapidapi.com`
- **Características:**
  - Extrae el texto principal de una URL automáticamente
  - Genera resúmenes configurables por número de párrafos
  - Soporte multiidioma con el parámetro `lang`
  - Motor de resumen configurable (engine 1 o 2)

### 2. Deep Translate (RapidAPI)
- **Proveedor:** gatzuma en RapidAPI
- **Endpoint usado:** `POST /language/translate/v2` — traduce texto entre idiomas
- **Host:** `deep-translate1.p.rapidapi.com`
- **Características:**
  - Traducción de texto libre de cualquier longitud
  - Detección automática del idioma origen
  - Soporta más de 100 idiomas
  - Acepta texto plano o HTML

---

## ✅ Herramientas y versiones

| Herramienta        | Versión     |
|--------------------|-------------|
| Java               | 17          |
| Spring Boot        | 3.3.5       |
| Spring WebFlux     | 6.x         |
| MongoDB Reactive   | Spring Data |
| Maven              | 3.x         |
| MongoDB Atlas      | Cloud       |
| RapidAPI           | v1          |

---

## ✅ Endpoints disponibles

### Article Summarizer
| Método | Ruta           | Descripción                                  |
|--------|----------------|----------------------------------------------|
| POST   | `/api/summary` | Resume un artículo por URL y lo guarda       |
| GET    | `/api/summary` | Lista todos los resúmenes guardados          |

**Body ejemplo:**
```json
{
  "url": "https://time.com/6286679/musk-ai-open-letter/",
  "length": 3,
  "lang": "en"
}
```

### Deep Translate
| Método | Ruta              | Descripción                                  |
|--------|-------------------|----------------------------------------------|
| POST   | `/api/translate`  | Traduce un texto y lo guarda en MongoDB      |
| GET    | `/api/translate`  | Lista todas las traducciones guardadas       |

**Body ejemplo:**
```json
{
  "q": "Hello World",
  "source": "en",
  "target": "es"
}
```

---

## ✅ Configuración

Todas las credenciales están en `src/main/resources/application.yaml`.  
Reemplaza el `uri` de MongoDB con tu cadena de conexión de MongoDB Atlas:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://<usuario>:<password>@<cluster>.mongodb.net/napaico_db

rapidapi:
  key: TU_RAPIDAPI_KEY
```
