/**
 * Copyright 2010 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.mvp.client.proxy;

import com.gwtplatform.mvp.client.Presenter;

/**
 * The interface of a {@link TabContentProxy} that is also a {@link Place}.
 * This interface assumes the use of a {@link TabDescriptionText}. 
 * For more flexibility, implement directly {@link TabContentProxyPlaceGeneric}.
 * 
 * @author Philippe Beaudoin
 * 
 * @param <P> The type of the {@link Presenter} attached to this
 *          {@link TabContentProxy}.
 */
public interface TabContentProxyPlace<P extends Presenter> extends
    extends TabContentProxyPlaceGeneric<TabDescriptionText, P> {
}
