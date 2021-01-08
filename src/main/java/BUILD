load("@rules_java//java:defs.bzl", "java_library")

genrule(
    name = "schema",
    srcs = ["@schema//file"],
    outs = [
        "schema/Dictionary.java",
        "schema/Entry.java",
        "schema/Etymology.java",
        "schema/Group.java",
        "schema/POS.java",
        "schema/Usage.java",
    ],
    cmd = "flatc -j -o $(@D) $(SRCS)",
    visibility = ["//visibility:public"],
)

java_library(
    name = "odict",
    srcs = glob(["**/*.java"]) + [":schema"],
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_google_flatbuffers_flatbuffers_java",
        "@maven//:org_xerial_snappy_snappy_java",
    ],
)
