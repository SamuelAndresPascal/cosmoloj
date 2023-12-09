package com.cosmoloj.processor.common.writer;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AstrolojFactoryWriter extends AstrolojCommonWriter {

    protected static final String SOURCE_API_PACKAGE = "api";
    protected static final String TARGET_BEAN_PACKAGE = "bean";

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String factoryName;
    private final StringBuilder before = new StringBuilder();
    private final StringBuilder body = new StringBuilder();
    private final StringBuilder after = new StringBuilder();

    private String[] apiPackage = new String[0];
    private StringBuilder currentBuilder;

    protected AstrolojFactoryWriter(final ProcessingEnvironment procEnv, final RoundEnvironment roundEnv,
            final String factoryName) {
        super(procEnv, roundEnv);
        this.factoryName = Objects.requireNonNull(factoryName);
        this.currentBuilder = body;
    }

    protected final String factoryName() {
        return factoryName;
    }

    @Override
    protected StringBuilder builder() {
        return currentBuilder;
    }

    @Override
    protected String flush() {
        header();
        footer();
        return before.toString() + body.toString() + after.toString();
    }

    protected final String[] apiPackage() {
        return apiPackage;
    }

    protected final String[] implPackage() {
        if (apiPackage.length > 0) {
            return ProcessorUtil.replaceAndCutPackage(apiPackage, SOURCE_API_PACKAGE, TARGET_BEAN_PACKAGE);
        }
        return apiPackage;
    }

    @Override
    protected String filePath() {
        final String path = apiPackage.length == 0
                ? factoryName
                : String.join(".", implPackage()) + "." + factoryName;
        LOG.warn("factory \"{}\": path resolved to {}", factoryName, path);
        return path;
    }

    protected abstract Class<? extends Processor> annotationProcessor();

    /**
     * <div class="fr">Méthode de prise en charge des types. À ce niveau d'abstraction, la seule tâche effectuée est
     * de mettre à jour le paqutage de l'élément en cours d'exploration. Si l'on souhaite maintenir cette mise à jour
     * tout en effectuant une tâche réelle d'écriture lors de la prise en charge du type, cette méthode doit être
     * redéfinie dans une classe concrète et celle-ci commencer par appeler super.addElement().</div>
     * @param element
     */
    public void addElement(final TypeElement element) {

        final String[] pack = ProcessorUtil.packages(getProcessingEnvironment(), element);

        LOG.warn("factory {}: update package from {} to {}", factoryName, apiPackage, pack);
        this.apiPackage = pack;
    }

    protected void header() {
        this.currentBuilder = this.before;

        final String[] pack = implPackage();
        if (pack.length != 0) {
            printPackage(pack);
            ln(2);
        }

        printImport("javax.annotation.processing.Generated");
        ln(2);

        print(ProcessorUtil.generatedAnnotation(annotationProcessor()));
        ln();
    }

    protected void footer() {
        this.currentBuilder = this.after;
        ln();
        println("}");
    }

    protected static String methodName(final TypeElement element) {

        final StringBuilder name = new StringBuilder(element.getSimpleName().toString());
        TypeElement current = element;
        while (!ElementKind.PACKAGE.equals(current.getEnclosingElement().getKind())) {
            current = (TypeElement) current.getEnclosingElement();
            name.append(current.getSimpleName());
        }
        return name.toString();
    }
}
