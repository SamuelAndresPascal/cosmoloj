module com.cosmoloj.language.common {
    requires org.slf4j;
    requires com.cosmoloj.util;
    requires com.cosmoloj.language.api;
    exports com.cosmoloj.language.common.impl.semantic;
    exports com.cosmoloj.language.common.text.lexeme.simple;
    exports com.cosmoloj.language.common.impl.builder;
    exports com.cosmoloj.language.common.datetime.parsing;
    exports com.cosmoloj.language.common.number.lexeme.simple;
    exports com.cosmoloj.language.common.number.lexeme.compound;
    exports com.cosmoloj.language.common.impl.exception;
    exports com.cosmoloj.language.common.impl.parsing;
    exports com.cosmoloj.language.common.number.parsing;
    exports com.cosmoloj.language.common.impl.cache;
}
