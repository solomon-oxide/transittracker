#!/bin/bash

# SSL Certificate Generation Script for Transit Tracker
# This script generates self-signed certificates for development

set -e

CERT_DIR="$(dirname "$0")"
CERT_NAME="transittracker"
DAYS=365

echo "Generating SSL certificates for Transit Tracker..."

# Create certificate directory if it doesn't exist
mkdir -p "$CERT_DIR"

# Generate private key and certificate
echo "Generating private key and certificate..."
openssl req -x509 -newkey rsa:4096 \
    -keyout "$CERT_DIR/${CERT_NAME}-key.pem" \
    -out "$CERT_DIR/${CERT_NAME}-cert.pem" \
    -days $DAYS \
    -nodes \
    -subj "/C=US/ST=State/L=City/O=TransitTracker/OU=Development/CN=localhost"

# Generate PKCS12 keystore
echo "Generating PKCS12 keystore..."
openssl pkcs12 -export \
    -in "$CERT_DIR/${CERT_NAME}-cert.pem" \
    -inkey "$CERT_DIR/${CERT_NAME}-key.pem" \
    -out "$CERT_DIR/${CERT_NAME}.p12" \
    -name "$CERT_NAME" \
    -password pass:changeit

# Copy to resources directory
echo "Copying keystore to resources..."
mkdir -p "../src/main/resources/ssl"
cp "$CERT_DIR/${CERT_NAME}.p12" "../src/main/resources/ssl/"

echo "SSL certificates generated successfully!"
echo ""
echo "Files created:"
echo "  - $CERT_DIR/${CERT_NAME}-cert.pem (Certificate)"
echo "  - $CERT_DIR/${CERT_NAME}-key.pem (Private Key)"
echo "  - $CERT_DIR/${CERT_NAME}.p12 (PKCS12 Keystore)"
echo "  - ../src/main/resources/ssl/${CERT_NAME}.p12 (Classpath Keystore)"
echo ""
echo "You can now run your application with SSL enabled:"
echo "  mvn spring-boot:run"
echo ""
echo "Application will be available at: https://localhost:8443"
echo "Note: Browser will show security warning for self-signed certificate"
