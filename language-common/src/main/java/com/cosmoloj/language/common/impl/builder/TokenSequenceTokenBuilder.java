package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.builder.TokenBuilder;
import com.cosmoloj.language.api.exception.ParserException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.exception.ParserExceptionBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * <div class="fr">Constructeur d'expressions à partir de jetons.</div>
 *
 * @author Samuel Andrés
 * @param <I> <span class="fr">type de jeton accepté</span>
 * @param <O> <span class="fr">type de jeton produit</span>
 */
public abstract class TokenSequenceTokenBuilder<I extends Token, O extends Token> implements TokenBuilder<I, O>,
        StateCheckTokenBuilder<I> {

    private final List<I> tokens = new ArrayList<>();

    @Override
    public void add(final I token) {
        tokens.add(token);
    }

    @Override
    public void checkAndAdd(final I token) throws ParserException {
        if (check(token)) {
            add(token);
        } else {
            final Class<? extends I>[] expectations = null; //expectations();
            if (expectations != null) {
               throw new ParserExceptionBuilder(token, index())
                       .types(expectations)
                       .exception();
            }
        }
    }

    @Override
    public List<I> tokens() {
        return tokens;
    }
}
