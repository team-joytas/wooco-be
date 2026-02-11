rootProject.name = "wooco-be"

include(
    "core",
    "bootstrap:api",
    "support:common",
    "support:metric",
    "support:logging",
    "infrastructure:aws",
    "infrastructure:rest",
    "infrastructure:mysql",
    "infrastructure:redis",
    "infrastructure:fcm",
)
