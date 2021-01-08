##############################
#     MAVEN DEPENDENCIES     #
##############################

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_file")

RULES_JVM_EXTERNAL_TAG = "4.0"

RULES_JVM_EXTERNAL_SHA = "31701ad93dbfe544d597dbe62c9a1fdd76d81d8a9150c2bf1ecf928ecdf97169"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.xerial.snappy:snappy-java:1.1.4",
        "com.fasterxml.jackson.core:jackson-core:2.9.4",
        "com.fasterxml.jackson.core:jackson-annotations:2.9.4",
        "com.fasterxml.jackson.core:jackson-databind:2.9.4",
        "com.google.flatbuffers:flatbuffers-java:1.12.0",
    ],
    maven_install_json = "//:maven_install.json",
    repositories = [
        "https://jcenter.bintray.com/",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

# ODict Schema
# ============
http_file(
    name = "schema",
    downloaded_file_path = "schema.fbs",
    sha256 = "10d9a90c24faedcc85c4282da337702702c5372040798d04c54e451e8c306d0c",
    urls = ["https://raw.githubusercontent.com/odict/odict/master/go/schema/schema.fbs"],
)
