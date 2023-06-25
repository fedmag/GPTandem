plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // speech2text
    implementation("com.google.cloud:google-cloud-speech:4.14.0")
    // text2speech
    implementation("com.google.cloud:google-cloud-texttospeech:2.21.0")
    implementation("javazoom:jlayer:1.0.1")

    // http client
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // ui
    implementation("com.formdev:flatlaf:3.1.1")

    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    // logging
    implementation("ch.qos.logback:logback-classic:1.2.9")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}