plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.40"
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
}

taboolib {
    install("common")
    install("common-5")
    install("module-chat")
    install("module-configuration")
    install("module-lang")
    install("module-ui")
    install("platform-bukkit")
    classifier = null
    version = "6.0.9-4"
    description {
        contributors {
            name("jizhe")
        }
        dependencies{
            name("Vault")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11800:11800-minimize:api")
    compileOnly("ink.ptms.core:v11800:11800-minimize:mapped")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

task("copy") {
    //复制前执行构建
    dependsOn("clean")
    dependsOn("build")
    tasks.findByName("build")?.mustRunAfter("clean")
    mustRunAfter("build")
    //进行任务后进行复制
    doLast {
        copy{
            from("${buildDir.path}\\libs\\")
            into("F:/Server/Paper-1.18.1/plugins")
        }
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            groupId = project.group.toString()
        }
    }
}