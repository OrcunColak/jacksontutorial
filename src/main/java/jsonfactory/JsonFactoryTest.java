package jsonfactory;

import com.fasterxml.jackson.core.ErrorReportConfiguration;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.core.util.JsonRecyclerPools;

// See https://cowtowncoder.medium.com/jackson-2-16-rc1-overview-55dbb90c22d9
class JsonFactoryTest {

    private JsonFactory build() {
        // let's use stricter limits than defaults:
        StreamReadConstraints streamReadConstraints = StreamReadConstraints.builder()
                // Longest allowed number value (default: 1000 characters)
                .maxNumberLength(100)
                // Longest allowed String value (default: 20 million characters)
                .maxStringLength(1_000_000)
                // Deepest nesting allowed for input documents (default: 1000 levels)
                .maxNestingDepth(100)
                // Maximum length of the input document (default: no limit)
                .maxDocumentLength(10_000_000)
                // Maximum length of Object property name (default: 50,000 characters)
                .maxNameLength(1_000)
                .build();

        StreamWriteConstraints streamWriteConstraints = StreamWriteConstraints.builder()
                .maxNestingDepth(100)
                .build();

        return JsonFactory.builder()
                .streamReadConstraints(streamReadConstraints)
                .streamWriteConstraints(streamWriteConstraints)
                // For now default implementation is JsonRecyclerPools.threadLocalPool()
                // // Let's use globally shared lock-free recycler pool
                .recyclerPool(JsonRecyclerPools.sharedLockFreePool())
                .build();
    }

    private JsonFactory buildErrorReportConfiguration() {
        ErrorReportConfiguration config = ErrorReportConfiguration.builder()
                // Maximum length of token (like individual JSON String value) to include during parsing exception (default: 256 characters)
                .maxErrorTokenLength(100)
                // Maximum length of “raw content” (input for parsing, that is, input document) to include (default: 500 characters)
                .maxRawContentLength(2000)
                .build();
        return JsonFactory.builder()
                .errorReportConfiguration(config)
                // !!! NOTE: without this no source content is included in exception
                .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                .build();
    }
}
