module com.cosmoloj.util {
    requires java.sql;
    requires org.slf4j;
    requires com.cosmoloj.configuration;
    exports com.cosmoloj.util;
    exports com.cosmoloj.util.bib;
    exports com.cosmoloj.util.bin;
    exports com.cosmoloj.util.conversion;
    exports com.cosmoloj.util.function;
    exports com.cosmoloj.util.net;
    exports com.cosmoloj.util.security;
    exports com.cosmoloj.util.sql;
    exports com.cosmoloj.util.test;
    exports com.cosmoloj.util.io;
}
