#!/usr/bin/env bash
set -euo pipefail

# JitPack provides an Android SDK; point Gradle at it via local.properties.
if [ -z "${ANDROID_HOME:-}" ]; then
  for candidate in /opt/android-sdk /usr/local/android-sdk "${HOME}/android-sdk"; do
    if [ -d "$candidate" ]; then
      export ANDROID_HOME="$candidate"
      break
    fi
  done
fi

if [ -n "${ANDROID_HOME:-}" ]; then
  echo "sdk.dir=${ANDROID_HOME}" > local.properties
  echo "Using Android SDK at ${ANDROID_HOME}"
else
  echo "Warning: ANDROID_HOME is not set; ensure local.properties exists before building."
fi
