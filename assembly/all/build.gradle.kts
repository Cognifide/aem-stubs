plugins {
    id("com.cognifide.aem.package")
    `maven-publish`
}

apply(from = rootProject.file("gradle/common.gradle.kts"))
description = "AEM Stubs - All-In-One"

tasks {
    packageCompose {
        mergePackageProject(":faker")
        mergePackageProject(":core")
        mergePackageProject(":moco")
        mergePackageProject(":wiremock")

        vaultDefinition.filter("/apps/stubs/groovy/install")
        installBundle("org.codehaus.groovy:groovy-all:2.4.15") {
            dirPath.set("/apps/stubs/groovy/install")
            vaultFilter.set(false)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(common.publicationArtifact(tasks.packageCompose))
        }
    }
}
