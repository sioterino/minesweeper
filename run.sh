#!/bin/bash

set -e  # Exit on error
set -u  # Treat unset variables as errors

# Check if gradlew exists
if [ ! -f "./gradlew" ]; then
    echo "Gradle wrapper (./gradlew) not found."
    exit 1
fi

# Make gradlew executable if needed
if [ ! -x "./gradlew" ]; then
    if [ -w "./gradlew" ]; then
        chmod +x ./gradlew
        echo "Made ./gradlew executable."
    else
        echo "No permission to make ./gradlew executable. Run with sudo or fix permissions."
        exit 1
    fi
fi

# Build the distribution
echo "Building project with Gradle..."
./gradlew installDist

# Run the CLI program
APP_EXEC="./app/build/install/app/bin/app"
if [ -x "$APP_EXEC" ]; then
    echo "Running Minesweeper CLI..."
    "$APP_EXEC"
else
    echo "Executable $APP_EXEC not found or not executable."
    exit 1
fi
