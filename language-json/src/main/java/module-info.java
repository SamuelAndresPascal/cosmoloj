module com.cosmoloj.language.json {
    requires com.cosmoloj.language.common;
    requires com.cosmoloj.language.api;
    requires org.slf4j;
    exports com.cosmoloj.language.json.parsing;
    exports com.cosmoloj.language.json.lexeme.compound;
    exports com.cosmoloj.language.json.lexeme.simple;
    exports com.cosmoloj.language.json.expression;
    exports com.cosmoloj.language.json.mapping;
}
