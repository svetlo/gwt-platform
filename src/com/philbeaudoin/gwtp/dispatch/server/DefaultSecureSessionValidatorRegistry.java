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

package com.philbeaudoin.gwtp.dispatch.server;

import java.util.HashMap;
import java.util.Map;

import com.philbeaudoin.gwtp.dispatch.shared.Action;
import com.philbeaudoin.gwtp.dispatch.shared.Result;

/**
 * The default {@link InstanceSecureSessionValidatorRegistry} implementation.
 * 
 * @author Christian Goudreau
 */
public class DefaultSecureSessionValidatorRegistry implements InstanceSecureSessionValidatorRegistry {
    private final Map<Class<? extends Action<? extends Result>>, SecureSessionValidator> validators;

    public DefaultSecureSessionValidatorRegistry() {
        validators = new HashMap<Class<? extends Action<? extends Result>>, SecureSessionValidator>(100);
    }

    @Override
    public <A extends Action<R>, R extends Result> void addSecureSessionValidator(Class<A> actionClass, SecureSessionValidator secureSessionValidator) {
        validators.put(actionClass, secureSessionValidator);
    }

    @Override
    public <A extends Action<R>, R extends Result> boolean removeSecureSessionValidator(Class<A> actionClass) {
        return validators.remove(actionClass) != null;
    }

    @Override
    public void clearSecureSessionValidators() {
        validators.clear();
    }

    @Override
    public <A extends Action<R>, R extends Result> SecureSessionValidator findSecureSessionValidator(A action) {
        return validators.get(action.getClass());
    }

}
