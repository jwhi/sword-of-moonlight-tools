# Sword of Moonlight Tools

Parses files for [Sword of Moonlight: The King's Field Making Tool](https://doc.swordofmoonlight.com/) into Kotlin classes to allow automated testing and validation.

Goal of this project is to give insight into map events and verify counters being modified and where those counters are checked to flag breaking changes.

Example project being used to validate parsing is stored in `/src/main/resources`. Full project files provided that can be opened in Sword of Moonlight.

## Current Progress
1. Parse map `.evt` files
   1. Load event definitions and pages
   2. Load operations within pages (IN PROGRESS)

## Future
1. Load in item, NPC, enemy, object, etc. params to have full instead of just the id stored in the map's `.evt`
2. Load in counter names from `SYS.dat` in addition to counter id.
3. Map data to better structured objects to allow better testing.

## Gradle Information
This project uses [Gradle](https://gradle.org/).
To build and run the application, use the *Gradle* tool window by clicking the Gradle icon in the right-hand toolbar,
or run it directly from the terminal:

* Run `./gradlew run` to build and run the application.
* Run `./gradlew build` to only build the application.
* Run `./gradlew check` to run all checks, including tests.
* Run `./gradlew clean` to clean all build outputs.

Note the usage of the Gradle Wrapper (`./gradlew`).
This is the suggested way to use Gradle in production projects.

[Learn more about the Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

[Learn more about Gradle tasks](https://docs.gradle.org/current/userguide/command_line_interface.html#common_tasks).

This project uses a version catalog (see `gradle/libs.versions.toml`) to declare and version dependencies
and both a build cache and a configuration cache (see `gradle.properties`).