plugins {
    id("com.cognifide.aem.package")
    id("com.jfrog.bintray")
    `maven-publish`
}

apply(from = rootProject.file("gradle/common.gradle.kts"))
description = "AEM Stubs - WireMock App"

tasks {
    packageCompose {
        mergePackageProject(":core")
        mergePackageProject(":wiremock")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(common.publicationArtifact(tasks.packageCompose))
        }
    }
}

bintrayOptions()
bintray { setPublications("maven") }
