Alfonz
======

Mr. Alfonz is here to help you build your Android app, make the development process easier and avoid boilerplate code.

When I develop Android apps, I often repeat the same code. I use the same utilities or helper classes. I decided to pack these classes into a library and that's how Alfonz was born. Alfonz's grandpa is my collection of [templates and utilities](https://github.com/petrnohejl/Android-Templates-And-Utilities).

Alfonz is a multi purpose library split into small modules (something like Android Jetpack). Some of them depend on other modules or 3rd party libraries (RxJava, Retrofit, OkHttp), but in general I try to keep Alfonz simple and independent from other libs as much as possible. Alfonz is written in Java, but it's fully compatible with Kotlin as well.

[![Alfonz logo](extras/graphics/alfonz.gif)](https://www.androidify.com/en/#/gallery/ab210bf216670c2d575502f78b920e97)


Modules
-------

* [adapter](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-adapter) - generic adapters for RecyclerView or ViewPager with data binding support
* [arch](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-arch) - base classes for MVVM architecture (based on Architecture Components)
* [graphics](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-graphics) - utilities for working with bitmaps and drawables
* [media](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-media) - utilities for working with images, sounds and videos
* [rest](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-rest) - helper classes for managing REST API calls
* [rx](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-rx) - helper classes for managing RxJava observables and subscriptions
* [utility](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-utility) - bunch of miscellaneous utilities
* [view](https://github.com/petrnohejl/Alfonz/tree/master/alfonz-view) - collection of custom views and layouts


Samples
-------

Samples can be found in the [samples](https://github.com/petrnohejl/Alfonz/tree/master/samples) app module. You can also check [Stocks project](https://github.com/petrnohejl/Android-Stocks) - experimental Android app with MVVM architecture. It uses most of the Alfonz's modules.


Download
--------

```groovy
implementation "org.alfonz:alfonz-adapter:0.9.3"
implementation "org.alfonz:alfonz-arch:0.9.3"
implementation "org.alfonz:alfonz-graphics:0.9.3"
implementation "org.alfonz:alfonz-media:0.9.3"
implementation "org.alfonz:alfonz-rest:0.9.3"
implementation "org.alfonz:alfonz-rx:0.9.3"
implementation "org.alfonz:alfonz-utility:0.9.3"
implementation "org.alfonz:alfonz-view:0.9.3"
```

Artifacts are available in [JCenter repository](https://bintray.com/alfonz/maven). Alfonz requires at minimum Android 5 (API level 21).


Developed by
------------

[Petr Nohejl](http://petrnohejl.cz)  
[Tomáš Mlynarič](https://github.com/mlykotom)


License
-------

    Copyright 2016 Petr Nohejl
    Copyright 2016 Tomáš Mlynarič

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
