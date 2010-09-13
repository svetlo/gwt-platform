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

package com.gwtplatform.dispatch.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.client.DefaultDispatchAsync;
import com.gwtplatform.dispatch.client.DefaultExceptionHandler;
import com.gwtplatform.dispatch.client.DefaultSecurityCookieAccessor;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.dispatch.client.ExceptionHandler;
import com.gwtplatform.dispatch.client.SecurityCookieAccessor;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandlerRegistry;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

/**
 * This gin module provides provides access to the {@link DispatchAsync}
 * singleton, which is used to make remote procedure calls to the server. This
 * module requires an {@link ExceptionHandler} and a
 * {@link SecurityCookieAccessor}. By default, these will be bound to
 * {@link DefaultExceptionHandler} and {@link DefaultSecurityCookieAccessor}
 * respectively.
 * <p />
 * If you want to prevent XSRF attack (you use secured
 * {@link com.gwtplatform.dispatch.shared.Action}s) the default
 * {@link EmptySecurityCookieAccessor} could leave your application vulnerable
 * to XSRF attacks. For more details see <a href="http://code.google.com/intl/fr/webtoolkit/articles/security_for_gwt_applications.html"
 * > this document</a>. For more security use {@link DispatchAsyncSecureModule}.
 * <p />
 * If you don't need XSRF protection, you can use directly this module with your
 * {@link com.google.gwt.inject.client.Ginjector}, i.e.:
 * 
 * <pre>
 * {@literal @}GinModules( { {@link DispatchAsyncModule}.class, ... } 
 * </pre>
 * For customization, skip the previous step and install the module in one of
 * your {@link #configure()} method:
 * 
 * <pre>
 * install( new DispatchAsyncModule( MyExceptionHandler.class, MySecurityCookieAccessor.class ) );
 * </pre>
 * You can pass {@code null} as any of the two parameter to fallback to the
 * default.
 * 
 * @author David Peterson
 * @author Philippe Beaudoin
 * @author Brendan Doherty
 */
public class DispatchAsyncModule extends AbstractGinModule {
  protected final Class<? extends ExceptionHandler> exceptionHandlerType;
  protected final Class<? extends SecurityCookieAccessor> sessionAccessorType;
  protected final Class<? extends ClientActionHandlerRegistry> clientActionHandlerRegistryType;

  public DispatchAsyncModule() {
    this.exceptionHandlerType = null;
    this.sessionAccessorType = null;
    this.clientActionHandlerRegistryType = null;
  }

  public DispatchAsyncModule(
      Class<? extends ExceptionHandler> exceptionHandlerType,
      Class<? extends SecurityCookieAccessor> sessionAccessorType) {
    this(exceptionHandlerType, sessionAccessorType, null);
  }

  public DispatchAsyncModule(
      Class<? extends ClientActionHandlerRegistry> clientActionHandlerRegistryType) {
    this(null, null, clientActionHandlerRegistryType);
  }

  public DispatchAsyncModule(
      Class<? extends ExceptionHandler> exceptionHandlerType,
      Class<? extends SecurityCookieAccessor> sessionAccessorType,
      Class<? extends ClientActionHandlerRegistry> clientActionHandlerRegistryType) {
    this.exceptionHandlerType = exceptionHandlerType;
    this.sessionAccessorType = sessionAccessorType;
    this.clientActionHandlerRegistryType = clientActionHandlerRegistryType;
  }

  @Override
  protected void configure() {
    if (exceptionHandlerType == null) {
      bind(ExceptionHandler.class).to(DefaultExceptionHandler.class);
    } else {
      bind(ExceptionHandler.class).to(exceptionHandlerType);
    }

    if (sessionAccessorType == null) {
      bind(SecurityCookieAccessor.class).to(DefaultSecurityCookieAccessor.class);
    } else {
      bind(SecurityCookieAccessor.class).to(sessionAccessorType);
    }

    if (clientActionHandlerRegistryType == null) {
      bind(ClientActionHandlerRegistry.class).to(
          DefaultClientActionHandlerRegistry.class).asEagerSingleton();
    } else {
      bind(ClientActionHandlerRegistry.class).to(
          clientActionHandlerRegistryType).asEagerSingleton();
    }
  }

  @Provides
  @Singleton
  protected DispatchAsync provideDispatchAsync(
      ExceptionHandler exceptionHandler,
      SecurityCookieAccessor secureSessionAccessor,
      ClientActionHandlerRegistry registry) {
    return new DefaultDispatchAsync(exceptionHandler, secureSessionAccessor,
        registry);
  }
}
