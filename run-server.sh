#!/bin/bash
PAPER_VERSION="1.21.1"
PAPER_BUILD="122"
PLUGIN_NAME="spawn-warps-1.0.0.jar"
SERVER_DIR="server"

echo "========== SpawnWarps Dev Server =========="

mkdir -p "$SERVER_DIR/plugins"

PAPER_JAR="$SERVER_DIR/paper-$PAPER_VERSION.jar"
if [ ! -f "$PAPER_JAR" ]; then
    echo "Baixando Paper $PAPER_VERSION..."
    curl -o "$PAPER_JAR" "https://api.papermc.io/v2/projects/paper/versions/$PAPER_VERSION/builds/$PAPER_BUILD/downloads/paper-$PAPER_VERSION-$PAPER_BUILD.jar"
fi

echo "eula=true" > "$SERVER_DIR/eula.txt"

echo "Compilando plugin..."
mvn package -q -DskipTests

echo "Copiando plugin..."
cp "target/$PLUGIN_NAME" "$SERVER_DIR/plugins/"

echo "Iniciando servidor..."
cd "$SERVER_DIR"
java -Xmx2G -jar "paper-$PAPER_VERSION.jar" --nogui
