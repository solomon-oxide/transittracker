# SSL Certificate Setup for Transit Tracker

This document provides instructions for setting up SSL certificates for the Transit Tracker application.

## Development Environment (Self-Signed Certificates)

### Quick Start
The application is already configured with self-signed certificates for development. Simply run:

```bash
mvn spring-boot:run
```

The application will be available at: `https://localhost:8443`

**Note**: Your browser will show a security warning for self-signed certificates. Click "Advanced" and "Proceed to localhost" to continue.

### Generated Files
- `ssl/transittracker-cert.pem` - Certificate file
- `ssl/transittracker-key.pem` - Private key file  
- `ssl/transittracker.p12` - PKCS12 keystore (used by application)
- `src/main/resources/ssl/transittracker.p12` - Keystore in classpath

## Production Environment

### Option 1: Using Environment Variables
Set the following environment variables:

```bash
export SSL_KEYSTORE_PATH=/path/to/your/keystore.p12
export SSL_KEYSTORE_PASSWORD=your_keystore_password
export SSL_KEY_ALIAS=your_key_alias
export SSL_TRUSTSTORE_PATH=/path/to/truststore.p12  # Optional
export SSL_TRUSTSTORE_PASSWORD=your_truststore_password  # Optional
```

Run with production profile:
```bash
mvn spring-boot:run -Dspring.profiles.active=production
```

### Option 2: Using application.properties
Update `src/main/resources/application.properties`:

```properties
# Production SSL Configuration
server.ssl.enabled=true
server.ssl.key-store=/path/to/your/keystore.p12
server.ssl.key-store-password=your_keystore_password
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=your_key_alias
server.port=8443
```

## Obtaining Production Certificates

### Option 1: Let's Encrypt (Free)
1. Install certbot: `sudo apt install certbot`
2. Generate certificate: `sudo certbot certonly --standalone -d yourdomain.com`
3. Convert to PKCS12:
   ```bash
   openssl pkcs12 -export -in /etc/letsencrypt/live/yourdomain.com/fullchain.pem \
                  -inkey /etc/letsencrypt/live/yourdomain.com/privkey.pem \
                  -out /path/to/keystore.p12 \
                  -name "transittracker" \
                  -password pass:your_password
   ```

### Option 2: Commercial Certificate Authority
1. Generate a Certificate Signing Request (CSR):
   ```bash
   openssl req -new -newkey rsa:2048 -nodes -keyout yourdomain.key \
               -out yourdomain.csr
   ```
2. Submit CSR to your CA
3. Convert received certificate to PKCS12:
   ```bash
   openssl pkcs12 -export -in yourdomain.crt \
                  -inkey yourdomain.key \
                  -out /path/to/keystore.p12 \
                  -name "transittracker" \
                  -password pass:your_password
   ```

## Security Best Practices

1. **Use Strong Passwords**: Use complex passwords for keystores
2. **Regular Certificate Renewal**: Set up automatic renewal for Let's Encrypt certificates
3. **Secure Storage**: Store certificates and keys securely
4. **Environment Variables**: Use environment variables for sensitive configuration
5. **HTTPS Only**: Configure HTTP to HTTPS redirect in production

## Troubleshooting

### Common Issues

1. **Certificate Not Found**: Ensure the keystore path is correct and accessible
2. **Wrong Password**: Verify the keystore password matches the configuration
3. **Port Already in Use**: Change the port in application.properties if 8443 is occupied
4. **Browser Security Warning**: This is normal for self-signed certificates in development

### Testing SSL Configuration

Test your SSL setup:
```bash
# Test SSL connection
openssl s_client -connect localhost:8443 -servername localhost

# Check certificate details
keytool -list -v -keystore /path/to/keystore.p12 -storetype PKCS12
```

## File Structure

```
transittracker/
├── ssl/                          # SSL certificates directory
│   ├── transittracker-cert.pem   # Certificate file
│   ├── transittracker-key.pem    # Private key file
│   └── transittracker.p12        # PKCS12 keystore
├── src/main/resources/ssl/       # Keystore in classpath
│   └── transittracker.p12        # Development keystore
└── SSL_SETUP.md                  # This documentation
```

## Support

For SSL-related issues:
1. Check the application logs for SSL errors
2. Verify certificate validity and expiration
3. Ensure proper file permissions on certificate files
4. Test with SSL testing tools like SSL Labs
