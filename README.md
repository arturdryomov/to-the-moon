# To the Moon

This repository contains the source code for the To the Moon Android app
[available on Google Play][Google Play link].

> I have run 10 miles a day, every day, for 18 years.
> That’s 65 thousand miles. A third of the way to the Moon.
> My goal is to run to the Moon. — Chris Traeger

[![Screenshot][Screenshot image]][Google Play link]

## License

* [Apache Version 2.0][Apache link]

## Building

[![Travis Status][Travis image]][Travis link]

You will need JDK 1.7+ installed.
Gradle, Android SDK and all dependencies will be downloaded automatically.

```
$ ./gradlew clean assembleDebug
```

## Acknowledgements

The application uses some open source libraries.

* [Bundler][Bundler link]
* [Butter Knife][Butter Knife link]
* [Dart][Dart link]
* [Floating Action Button][Floating Action Button link]
* [Icepick][Icepick link]
* [Otto][Otto link]


  [Apache link]: http://www.apache.org/licenses/LICENSE-2.0.html
  [Google Play link]: https://play.google.com/store/apps/details?id=ru.ming13.moon
  [Travis link]: https://travis-ci.org/ming13/to-the-moon

  [Bundler link]: https://github.com/f2prateek/bundler
  [Butter Knife link]: https://github.com/JakeWharton/butterknife
  [Dart link]: https://github.com/f2prateek/dart
  [Floating Action Button link]: https://github.com/makovkastar/FloatingActionButton
  [Icepick link]: https://github.com/frankiesardo/icepick
  [Otto link]: https://github.com/square/otto
  
  [Screenshot image]: https://cloud.githubusercontent.com/assets/200401/6227277/98542f34-b6ae-11e4-8e41-d55c4902eb0f.png
  [Travis image]: https://travis-ci.org/ming13/to-the-moon.svg?branch=master
