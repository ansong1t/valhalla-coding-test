## Valhalla Coding Test ##

## The Architecture ##
* MVVM Pattern with Clean Architecture (includes Domain, Repository layer). MVVM separates your view (i.e. `Activity`s and `Fragment`s) from your business logic.
  ![MVVM Clean Architecture with Repository](images/mvvm_clean_arch_with_repository.png)

### Tech Stack ###
- [Kotlin](https://kotlinlang.org/) - app primary programming language
- [Kotlin Coroutine](https://github.com/Kotlin/kotlinx.coroutines) - for asynchronous and reactive programming
- [Databinding](https://developer.android.com/topic/libraries/data-binding) - binding observable data to xml
- [Epoxy](https://github.com/airbnb/epoxy) - building complex screens in a RecyclerView
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Android dependency injection framework
- [Room](https://developer.android.com/topic/libraries/architecture/room) - persistent database library
- [Retrofit](https://square.github.io/retrofit/) - a type-safe HTTP client
- [Moshi](https://github.com/square/moshi) - JSON library
- [Navigation](https://developer.android.com/guide/navigation) - app navigation
- [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - pagination
- [Coil](https://github.com/coil-kt/coil) - image loading library
- [Ktlint](https://github.com/pinterest/ktlint)/[Detekt](https://github.com/detekt/detekt) - code linting and code smells

### App Features List ###
- Modular app architecture
- Offline mode
- Installed code analysis and code smells
