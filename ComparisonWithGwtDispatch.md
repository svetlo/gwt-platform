# Introduction #
GWTP originally started by forking the excellent command pattern framework gwt-Dispatch. Through time, though, the two approaches have diverged significantly. To better help you decide which framework suits you best, this page discusses some of the major differences.

This document was updated in August 2010 and might not be up-to-date now since both projects are still actively adding features.

# Security and action validation #

Gwt-Dispatch has a security module, but it doesn't let you validate each action independantly. GWTP introduces `ActionsValidator`, a mechanism that lets you bind every action to a validator server side. You can customize this validator in any way you want, making it easy to securely confirm whether the current user has the right to use an action or not.

# Automatic XSRF attack detection #

<a href='Hidden comment: I"m almost 100% sure gwt-dispatch prevents XSRF attacks, this is where I got the idea but I changed the implementation so that it does not depend on an internal !AppEngine cookie. I changed your text here, please confirm.
'></a>
Both GWTP and gwt-dispatch offers the ability to automatically detect XSRF attacks (following [this](http://groups.google.com/group/Google-Web-Toolkit/web/security-for-gwt-applications?pli=1)). However, where gwt-dispatch uses an internal AppEngine cookie and is therefore not usable on another platform, GWTP let's you customize the security cookie in any way you like. It also provides a mechanism to create your own security cookie, making it usable on any backend.

Moreover, GWTP lets you configure the actions that are protected and those that aren't. This is useful if you want some actions to be accessible even before the security cookie is set.

# Custom user defined URLs for actions #

Both gwt-dispatch and GWTP let's you configure the URL path to access your actions. In gwt-dipatch, however, all the actions must share a single path whereas in GWTP you can configure this path on an action-by-action basis. This makes it easier to track actions in your web server log, or to intercept them via standard mechanisms.

# Support for undo #

Both gwt-dispatch and GWTP support rollback of composite actions that fail midway through. However, GWTP goes further and uses the rollback function you provide to enable undo support. You can even undo composite actions and the system will rollback the undo if anything goes wrong.

<a href='Hidden comment: Verify the following. I want to make a fair comparison, we should include features that are in gwt-dispatch but not in GWTP.'></a>

# Automatic action batching, client-side caching and queuing #

On his blog, David Chandler has discussed ways to integrate action batching, client-side caching and queuing in Gwt-Dispatch: <br />http://turbomanage.wordpress.com/

These features are planned in GWTP but not available yet. See:
<br />http://code.google.com/p/gwt-platform/issues/detail?id=60
<br />http://code.google.com/p/gwt-platform/issues/detail?id=131
<br />http://code.google.com/p/gwt-platform/issues/detail?id=163