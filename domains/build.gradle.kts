plugins {
    id("buildsrc.convention.kotlin-jvm")
}

dependencies {
    implementation(libs.kaitai.structs)
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.kotest)
}
