##############################
#     MAVEN DEPENDENCIES     #
##############################

# Snappy
# ======
maven_jar(
    name = "snappy",
    artifact = "org.xerial.snappy:snappy-java:1.1.4",
    sha1 = "d94ae6d7d27242eaa4b6c323f881edbb98e48da6"
)

# Jackson
# =======

# Core
# ----
maven_jar(
    name = "jackson_core",
    artifact = "com.fasterxml.jackson.core:jackson-core:2.9.4",
    sha1 = "a9a71ec1aa37da47db168fede9a4a5fb5e374320"
)

# Annotations
# -----------
maven_jar(
    name = "jackson_annotations",
    artifact = "com.fasterxml.jackson.core:jackson-annotations:2.9.4",
    sha1 = "1380b592ad70439346b5d954ad202be048451c5a"
)

# Databind
# --------
maven_jar(
    name = "jackson_databind",
    artifact = "com.fasterxml.jackson.core:jackson-databind:2.9.4",
    sha1 = "498bbc3b94f566982c7f7c6d4d303fce365529be"
)

############################
#     WEB DEPENDENCIES     #
############################

# Flatbuffers
# ===========
new_http_archive(
    name = "flatbuffers",
    url = "https://github.com/google/flatbuffers/archive/v1.9.0.zip",
    sha256 = "81bb76a503a624f45bebad96fac1fea98d90d0c84e7771ff96ed168c19168de7",
    strip_prefix = "flatbuffers-1.9.0",
    build_file_content = """java_library(name = "flatbuffers", srcs = glob(["java/**/*.java"]), visibility = ["//visibility:public"])"""
)

# ODict Schema
# ============
http_file(
    name = "schema",
    url = "https://raw.githubusercontent.com/odict/odict/master/src/schema.fbs",
    sha256 = "88963751f1e0f9a7afddab2fe96425501c7ea6ca3a77d3e6f1633a4971e9dd01",
)