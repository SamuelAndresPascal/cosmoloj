module com.cosmoloj.processor.bibliography {
    requires java.compiler;
    requires org.slf4j;
    requires com.cosmoloj.processor.common;
    requires com.cosmoloj.language.api;
    requires com.cosmoloj.language.json;
    exports com.cosmoloj.processor.bibliography;
    exports com.cosmoloj.processor.bibliography.annotation;
}
