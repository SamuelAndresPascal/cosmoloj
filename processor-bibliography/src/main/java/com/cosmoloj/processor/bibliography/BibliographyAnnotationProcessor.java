package com.cosmoloj.processor.bibliography;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.json.expression.JsonArray;
import com.cosmoloj.language.json.expression.JsonValue;
import com.cosmoloj.language.json.parsing.JsonParser;
import com.cosmoloj.processor.bibliography.writer.BibliographyWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
@SupportedAnnotationTypes("com.cosmoloj.processor.bibliography.annotation.Bibliography")
@SupportedSourceVersion(SourceVersion.RELEASE_22)
public class BibliographyAnnotationProcessor extends AbstractProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        LOG.warn("process bibliography ");
        annotations.forEach(annotation -> {

            LOG.info("process bib annotation {}", annotation);
            for (final Element elt : roundEnv.getElementsAnnotatedWith(annotation)) {

                try {
                    final String pack = processingEnv.getElementUtils().getPackageOf(elt).getQualifiedName().toString();

                    /*
                    Pour que le fichier de ressources de la bibliographie se trouve dans la sortie des classes le
                    processeur doit être placé dans le processus de build après la copie des fichiers de ressources dans
                    le répertoire des .class.
                    */
                    final FileObject resource = processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "",
                    pack.replace('.', '/') + '/' + elt.getSimpleName().toString().toLowerCase(Locale.ROOT) + ".json");

                    LOG.info("bibliographie : " + resource.getName() + " / modifiéé : " + resource.getLastModified());
                    LOG.info(resource.toString());

                    final StringBuilder sb = new StringBuilder();
                    try (var reader = new BufferedReader(new InputStreamReader(resource.openInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    }

                    final JsonArray<JsonValue> json = (JsonArray<JsonValue>) JsonParser.parse(sb.toString());

                    new BibliographyWriter(processingEnv, (TypeElement) elt, json).writeFile();
                } catch (final IOException | LanguageException ex) {
                    LOG.warn("problem: ", ex);
                    throw new IllegalStateException(ex);
                }
            }

        });

        return false;
    }


}
