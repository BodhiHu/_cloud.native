# Cloud ZD - Cloud Native Microservices, CI/CD

### Install zadig services

```
helm upgrade --install zadig ./zadig-helm \
  --namespace ${NAMESPACE} \
  --version=1.14.0 \
  --set endpoint.type=IP \
  --set endpoint.IP=${IP} \
  --set gloo.gatewayProxies.gatewayProxy.service.httpNodePort=${PORT} \
  --set global.extensions.extAuth.extauthzServerRef.namespace=${NAMESPACE} \
  --set gloo.gatewayProxies.gatewayProxy.service.type=NodePort \
  --set "dex.config.staticClients[0].redirectURIs[0]=http://${IP}:${PORT}/api/v1/callback,dex.config.staticClients[0].id=zadig,dex.config.staticClients[0].name=zadig,dex.config.staticClients[0].secret=ZXhhbXBsZS1hcHAtc2VjcmV0"
```
