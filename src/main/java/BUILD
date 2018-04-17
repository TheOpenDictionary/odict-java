genrule(
  name = "schema",
  srcs = ["@schema//file"],
  cmd = "flatc -j -o $(@D) $(SRCS)",
  visibility = ["//visibility:public"],
  outs = [
    "schema/Dictionary.java",
    "schema/Entry.java",
    "schema/Etymology.java",
    "schema/Group.java",
    "schema/POS.java",
    "schema/Usage.java"
  ]
)

java_binary(
  name = "odict_java",
  deps = [
    "@flatbuffers//:flatbuffers",
    "@jackson_core//jar",
    "@jackson_annotations//jar",
    "@jackson_databind//jar",
    "@snappy//jar"
  ],
  srcs = glob(["**/*.java"]) + [":schema"],
  visibility = ["//visibility:public"]
)