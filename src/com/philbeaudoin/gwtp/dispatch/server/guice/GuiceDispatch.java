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

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.philbeaudoin.gwtp.dispatch.server.AbstractDispatch;
import com.philbeaudoin.gwtp.dispatch.server.ActionHandlerRegistry;
import com.philbeaudoin.gwtp.dispatch.server.SecureSessionValidatorRegistry;

/**
 * Base dispatch class used by Guice.
 * 
 * @author Christian Goudreau
 * @author David Paterson
 */
@Singleton
public class GuiceDispatch extends AbstractDispatch {
    private final ActionHandlerRegistry handlerRegistry;
    private final SecureSessionValidatorRegistry secureSessionValidatorRegistry;

    @Inject
    public GuiceDispatch(ActionHandlerRegistry handlerRegistry, SecureSessionValidatorRegistry secureSessionValidatorRegistry) {
        this.handlerRegistry = handlerRegistry;
        this.secureSessionValidatorRegistry = secureSessionValidatorRegistry;
    }

    @Override
    protected ActionHandlerRegistry getHandlerRegistry() {
        return handlerRegistry;
    }

    @Override
    protected SecureSessionValidatorRegistry getSecureSessionValidatorRegistry() {
        return secureSessionValidatorRegistry;
    }
}