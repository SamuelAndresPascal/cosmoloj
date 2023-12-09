package com.cosmoloj.processor.common.writer;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class Generics {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Types types;
    private final Map<TypeElement, Map<Name, TypeMirror>> genericsMap = new LinkedHashMap<>();

    public Generics(final DeclaredType type, final Types types) {
        this.types = types;
        LOG.info("create generics map for {}", type);
        load(type);
    }

    private void load(final DeclaredType type) {
        LOG.info("map generics for {}", type);

        final Map<Name, TypeMirror> map = new HashMap<>();
        final TypeElement elt = (TypeElement) type.asElement();
        genericsMap.put(elt, map);
        for (int i = 0; i < type.getTypeArguments().size(); i++) {
            LOG.info("map {} to {}", elt.getTypeParameters().get(i).getSimpleName(), type.getTypeArguments().get(i));
            map.put(elt.getTypeParameters().get(i).getSimpleName(), type.getTypeArguments().get(i));
        }

        for (final TypeMirror t : types.directSupertypes(type)) {
            load((DeclaredType) t);
        }
    }

    private boolean contains(final TypeElement type, final TypeVariable generic) {
        LOG.info("get argument for {}({}) in {}", type, generic, genericsMap);
        return genericsMap.containsKey(type) && genericsMap.get(type).containsKey(generic.asElement().getSimpleName());
    }

    private TypeMirror getArg(final TypeElement element, final CharSequence parameter) {
        LOG.info("get argument for {}({})", element, parameter);
        final Map<Name, TypeMirror> get = genericsMap.get(element);
        for (final Map.Entry<Name, TypeMirror> entry : get.entrySet()) {
            if (entry.getKey().contentEquals(parameter)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String replaceGenerics(final TypeElement context, final String arg) {
        final String[] split1 = arg.split("<");
        if (split1.length == 1) {
            final TypeMirror arg1 = getArg(context, split1[0]);
            if (arg1 == null) {
                return split1[0];
            } else {
                return arg1.toString();
            }
        } else {
            final String[] split2 = split1[1].substring(0, split1[1].length() - 1).split("\\s*,\\s*");
            final String generics = String.join(", ", Stream.of(split2)
                    .map(s -> replaceGenerics(context, s))
                    .toList());
            return split1[0] + '<' + generics + '>';
        }
    }

    public TypeMirror mapTypeVariable(final TypeElement element, final TypeVariable variable) {
        LOG.info("argument is type variable ; search mapping for {}({})", element.getClass(), variable);
        if (contains(element, variable)) {
            LOG.info("mapping found for type variable: get argument for {}({})", element, variable);
            return genericsMap.get(element).get(variable.asElement().getSimpleName());
        } else {
            LOG.info("no interface to search mapping in");
            return variable;
        }
    }

    public Map<TypeElement, Map<Name, TypeMirror>> getMap() {
        return genericsMap;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<TypeElement, Map<Name, TypeMirror>> entry : genericsMap.entrySet()) {
            sb.append(entry).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static Generics of(final TypeElement element, final ProcessingEnvironment procEnv) {
        return new Generics((DeclaredType) element.asType(), procEnv.getTypeUtils());
    }
}
