module com.cosmoloj.language.wkt2.v2_1 {
    requires org.slf4j;
    requires com.cosmoloj.util;
    requires com.cosmoloj.bibliography.cosmoloj;
    requires com.cosmoloj.language.wkt.sf;
    requires com.cosmoloj.language.common;
    requires com.cosmoloj.language.api;
    exports com.cosmoloj.language.wkt2.v2_1.expression;
    exports com.cosmoloj.language.wkt2.v2_1.lexeme.compound;
    exports com.cosmoloj.language.wkt2.v2_1.lexeme.simple;
    exports com.cosmoloj.language.wkt2.v2_1.parsing;
}
