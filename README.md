## Chirp
This is a WIP Kotlin Multiplatform project, meant to be a real-time chat application, with a complete account management flow. It connects to a [dedicated backend service](https://github.com/fgrzeskowiak/chirp-backend) and targets Android, iOS, and Desktop (in the future).  
It uses a multi-module architecture with separate `data`, `domain` and `presentation` modules with additional separation into `core` and `feature` modules. This allows to follow Clean Architecture principles on a module level.  
The structure looks as follows:

```
core
   data
   domain
   presentation
feature
   chat
      data
      domain
      presentation
```
To properly handle build logic in those modules, multiple Gradle Convention plugins have been implemented in `build-logic` directory.

The project is being created as a part of a [Kotlin Full-Stack course](https://pl-coding.com/full-stack-bundle), however it is not blindly following the course 1:1, but contains changes, like:
* Introducing [Arrow](https://arrow-kt.io) library for error handling and easier functional programming
* Using [Koin Annotations 2.2.0](https://insert-koin.io/docs/reference/koin-annotations/start/) with JSR330 support instead of regular Koin library to achieve more Dagger-like setup
* Extracting some logic from ViewModels to UseCases, where it was more business-related logic than presentation logic
* Improving readability of some Convention Plugins

### Currently implemented features:
* registration
* login
* email verification deeplinking (however the email sending is not yet implemented on BE side)

In order to build the project, a Chirp production `API_KEY` must be placed inside `local.properties` file.