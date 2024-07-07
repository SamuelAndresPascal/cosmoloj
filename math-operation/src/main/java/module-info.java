module com.cosmoloj.math.operation {
    requires org.slf4j;
    requires com.cosmoloj.util;
    requires com.cosmoloj.configuration;
    requires com.cosmoloj.math.util;
    requires com.cosmoloj.bibliography.cosmoloj;
    requires com.cosmoloj.format.gr3df97a;
    requires com.cosmoloj.math.tabular;
    exports com.cosmoloj.math.operation;
    exports com.cosmoloj.math.operation.surface;
    exports com.cosmoloj.math.operation.projection;
    exports com.cosmoloj.math.operation.conversion;
    exports com.cosmoloj.math.operation.transformation;
    exports com.cosmoloj.math.operation.perspective;
}
