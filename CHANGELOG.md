Changelog
=========

This document lists the most important changes in Alfonz library. For more detailed changelog see a Git log.
Each release uses the newest versions of dependencies.


0.8.1
-----

* [arch module] Add EventObserver which passes non-null event object in onChanged()
* [utility module] Check methods in PermissionManager now accept Context directly


0.8.0
-----

* [adapter module] Add adapter callbacks to observable collections bound to generic adapters and automatically notify adapters about changes
* [adapter module] Implement getItemPosition() to fix updating pager adapter with notifyDataSetChanged()
* [arch module] New module
* [mvvm module] MVVM module is deprecated, use Arch module instead
* [rx module] Add isPending() method in RxManager to find if there is any call pending
* [utility module] Make check methods in PermissionManager static


0.7.0
-----

* [adapter module] Simple data bound adapters are no longer abstract
* [adapter module] AdapterView interface for handling event callbacks, remove dependency on MVVM module
* [adapter module] Remove useless type parameters
* [media module] Add ImagePickerCallback in ImagePicker
* [mvvm module] Add ToolbarIndicator
* [rx module] New RxBus
* [utility module] Add PermissionRequestable in PermissionManager to persist callback and handle orientation change properly
* [all modules] Infer nullity


0.6.0
-----

* [adapter module] Binding adapters for RecyclerView setting up layout managers and item decorations
* [adapter module] Use IntDef instead of enum in BindingUtility
* [adapter module] New MultiDataBoundPagerAdapter
* [media module] Use IntDef instead of enum in SoundManager
* [mvvm module] Add Fragment tag in replaceFragment()
* [mvvm module] New AlfonzBindingActivity
* [rest module] Make catchObservableHttpError() and catchSingleHttpError() public
* [utility module] Add isCallable method in IntentUtility
* [utility module] New ServiceUtility
* [view module] Add invisibleWhenHidden attribute in StatefulLayout
* [view module] Use IntDef instead of enum in StatefulLayout


0.5.0
-----

* [adapter module] New BindingUtility, item decoration
* [mvvm module] Remove necessity of calling setModelView in a Fragment, it is called in parent AlfonzFragment
* [mvvm module] Rename ViewCallback to ViewAction, rename runViewCallback() to runViewAction()
* [mvvm module] New BindingUtility
* [rest module] Support for Single and Completable
* [rx module] Support for Single, Completable, Maybe
* [rx module] Rename LoggedObserver to AlfonzDisposableObserver
* [rx module] Register disposables automatically in RxManager
* [utility module] New IntentUtility
* [utility module] New PermissionManager


0.4.0
-----

* [mvvm module] Don't add a transaction to back stack by default in replaceFragment()
* [mvvm module] Rename action bar indicator constants
* [rx module] Fix removing running calls in RxManager
* [utility module] Use View instead of EditText in KeyboardUtility


0.3.0
-----

* [graphics module] New module
* [media module] New module
* [ui module] Remove module
* [utility module] New utilities
* [view module] New module


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
