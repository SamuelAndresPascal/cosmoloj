module com.cosmoloj.language.wkt2.v1_0 {
    requires org.slf4j;
    requires com.cosmoloj.util;
    requires com.cosmoloj.bibliography.cosmoloj;
    requires com.cosmoloj.language.wkt.sf;
    requires com.cosmoloj.language.common;
    requires com.cosmoloj.language.api;
    exports com.cosmoloj.language.wkt2.v1_0.expression;
    exports com.cosmoloj.language.wkt2.v1_0.lexeme.compound;
    exports com.cosmoloj.language.wkt2.v1_0.lexeme.simple;
    exports com.cosmoloj.language.wkt2.v1_0.parsing;
}
