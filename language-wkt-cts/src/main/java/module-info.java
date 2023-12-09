module com.cosmoloj.language.wkt.cts {
    requires org.slf4j;
    requires com.cosmoloj.util;
    requires com.cosmoloj.bibliography.cosmoloj;
    requires com.cosmoloj.language.wkt.sf;
    requires com.cosmoloj.language.common;
    requires com.cosmoloj.language.api;
    exports com.cosmoloj.language.wkt.cts.expression;
    exports com.cosmoloj.language.wkt.cts.lexeme;
    exports com.cosmoloj.language.wkt.cts.parsing;
}
