/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.philbeaudoin.gwtp.dispatch.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.internal.UniqueAnnotations;
import com.philbeaudoin.gwtp.dispatch.server.ActionHandler;
import com.philbeaudoin.gwtp.dispatch.server.DefaultSecureSessionValidator;
import com.philbeaudoin.gwtp.dispatch.server.SecureSessionValidator;
import com.philbeaudoin.gwtp.dispatch.shared.Action;
import com.philbeaudoin.gwtp.dispatch.shared.Result;

/**
 * Base Dispatch module that will bind {@link Action}s to {@link ActionHandler}s
 * and {@link SecureSessionValidator}. Your own Guice modules should extend this
 * class.
 * 
 * @author Christian Goudreau
 * @author David Peterson
 */
public abstract class DispatchModule extends AbstractModule {
    /**
     * Implementation of {@link SecureSessionValidatorMap} that links
     * {@link Action}s to {@link SecureSessionValidator}s
     * 
     * @param <A>
     *            Type of {@link Action}
     * @param <R>
     *            Type of {@link Result}
     * 
     * @author Christian Goudreau
     */
    private static class SecureSessionValidatorMapImpl<A extends Action<R>, R extends Result> implements SecureSessionValidatorMap<A, R> {
        private final Class<A> actionClass;
        private final Class<? extends SecureSessionValidator> secureSessionValidator;

        public SecureSessionValidatorMapImpl(Class<A> actionClass, Class<? extends SecureSessionValidator> secureSessionValidator) {
            this.actionClass = actionClass;
            this.secureSessionValidator = secureSessionValidator;
        }

        @Override
        public Class<A> getActionClass() {
            return actionClass;
        }

        @Override
        public Class<? extends SecureSessionValidator> getSecureSessionValidatorClass() {
            return secureSessionValidator;
        }
    }

    /**
     * Implementation of {@link ActionHandlerMap} that links {@link Action}s to
     * {@link ActionHandler}s
     * 
     * @param <A>
     *            Type of {@link Action}
     * @param <R>
     *            Type of {@link Result}
     * 
     * @author David Paterson
     */
    private static class ActionHandlerMapImpl<A extends Action<R>, R extends Result> implements ActionHandlerMap<A, R> {

        private final Class<A> actionClass;
        private final Class<? extends ActionHandler<A, R>> handlerClass;

        public ActionHandlerMapImpl(Class<A> actionClass, Class<? extends ActionHandler<A, R>> handlerClass) {
            this.actionClass = actionClass;
            this.handlerClass = handlerClass;
        }

        public Class<A> getActionClass() {
            return actionClass;
        }

        public Class<? extends ActionHandler<A, R>> getActionHandlerClass() {
            return handlerClass;
        }
    }

    @Override
    protected final void configure() {
        install(new ServerDispatchModule());

        configureHandlers();
    }

    protected abstract void configureHandlers();

    /**
     * @param <A>
     *            Type of {@link Action}
     * @param <R>
     *            Type of {@link Result}
     * @param actionClass
     *            Implementation of {@link Action} to link and bind
     * @param handlerClass
     *            Implementation of {@link ActionHandler} to link and bind
     * @param secureSessionValidator
     *            Implementation of {@link SecureSessionValidator} to link and
     *            bind
     */
    protected <A extends Action<R>, R extends Result> void bindSecureHandler(Class<A> actionClass, Class<? extends ActionHandler<A, R>> handlerClass,
            Class<? extends SecureSessionValidator> secureSessionValidator) {
        bind(SecureSessionValidatorMap.class).annotatedWith(UniqueAnnotations.create()).toInstance(new SecureSessionValidatorMapImpl<A, R>(actionClass, secureSessionValidator));
        bind(ActionHandlerMap.class).annotatedWith(UniqueAnnotations.create()).toInstance(new ActionHandlerMapImpl<A, R>(actionClass, handlerClass));
    }

    /**
     * @param <A>
     *            Type of {@link Action}
     * @param <R>
     *            Type of {@link Result}
     * @param actionClass
     *            Implementation of {@link Action} to link and bind
     * @param handlerClass
     *            Implementation of {@link ActionHandler} to link and bind
     */
    protected <A extends Action<R>, R extends Result> void bindHandler(Class<A> actionClass, Class<? extends ActionHandler<A, R>> handlerClass) {
        bind(ActionHandlerMap.class).annotatedWith(UniqueAnnotations.create()).toInstance(new ActionHandlerMapImpl<A, R>(actionClass, handlerClass));
        bind(SecureSessionValidatorMap.class).annotatedWith(UniqueAnnotations.create()).toInstance(new SecureSessionValidatorMapImpl<A, R>(actionClass, DefaultSecureSessionValidator.class));
    }
}