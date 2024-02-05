package jsonmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * See <a href="https://cowtowncoder.medium.com/jackson-2-16-rc1-overview-55dbb90c22d9">...</a>
 */
public class JsonMapperTest {

    private ObjectMapper build() {
        // reduce default ser/deser cache size; increase type cache
        CacheProvider cacheProvider = DefaultCacheProvider.builder()
                // Deserializer cache (caches JsonDeserializer instances for reuse) — default size 4000
                .maxDeserializerCacheSize(200)
                // Serializer cache (caches JsonSerializer instances for reuse) — default size 2000
                .maxSerializerCacheSize(200)
                // JavaType cache (caches resolved types for reuse) — default size 200
                .maxTypeFactoryCacheSize(1000)
                .build();

        return JsonMapper.builder()
                .cacheProvider(cacheProvider)
                .build();
    }
}
