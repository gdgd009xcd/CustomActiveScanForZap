import org.zaproxy.gradle.addon.AddOnStatus

version = "0.5.7"
description = "a Active Scanner with custmizable rules"

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Multi-Release"] = "true"
    }
}

zapAddOn {
    addOnName.set("CustomActiveScanForZAP")
    addOnStatus.set(AddOnStatus.ALPHA)
    zapVersion.set("2.11.0")

    manifest {
        author.set("gdgd009xcd")
        repo.set("https://github.com/gdgd009xcd/CustomActiveScanForZAP")
        helpSet {
            baseName.set("help%LC%.helpset")
            localeToken.set("%LC%")
        }
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.0")
    testImplementation("org.apache.commons:commons-lang3:3.9")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    artifacts {
        archives(sourcesJar)
        // archives(jar)
    }
}

spotless {
    java {
        clearSteps()
        // Don't enforce 
        targetExclude("**/*.java")
    }
}
