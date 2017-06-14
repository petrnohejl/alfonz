Changelog
=========

This document lists the most important changes in Alfonz library. For more detailed changelog see Git log.


0.5.0
-----

* [rx module] Support for Single, Completable, Maybe
* [rx module] Rename LoggedObserver to AlfonzDisposableObserver
* [rx module] Register disposables automatically in RxManager
* [rest module] Support for Single and Completable
* [mvvm module] Remove necessity of calling setModelView in a Fragment, it is called in parent AlfonzFragment
* [mvvm module] Rename ViewCallback to ViewAction, rename runViewCallback() to runViewAction()
* [mvvm module] New BindingUtility
* [adapter module] New BindingUtility, item decoration
* [utility module] New IntentUtility
* [utility module] New PermissionManager


0.4.0
-----

* [rx module] Fix removing running calls in RxManager
* [mvvm module] Don't add a transaction to back stack by default in replaceFragment()
* [mvvm module] Rename action bar indicator constants
* [utility module] Use View instead of EditText in KeyboardUtility


0.3.0
-----

* [graphics module] New module
* [media module] New module
* [view module] New module
* [utility module] New utilities
* [ui module] Remove module


0.2.0
-----

* [rest module] New module


0.1.0
-----

* [adapter module] New module
* [mvvm module] New module
* [rx module] New module
* [ui module] New module
* [utility module] New module
