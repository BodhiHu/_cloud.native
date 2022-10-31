# Cloud Native Microservices & CI/CD

## Create Kind Cluster

```
$ ./kind/bin/kind create cluster --name kind --config ./kind/clusters/multi-node-cluster.yaml
```

<!-----------------------------------DEPRECATED------------------------------------------->
## Deprecated

#### Install zadig services

```
$ helm repo add koderover-chart https://koderover.tencentcloudcr.com/chartrepo/chart
$ helm repo update

$ kubectl create ns zadig

$ export NAMESPACE=zadig \
  export IP=127.0.0.1 \
  export PORT=31500

$ helm upgrade --debug -v 4 --timeout 300s --install zadig ./zadig-helm \
  --namespace ${NAMESPACE} \
  --version=1.14.0 \
  --set endpoint.type=IP \
  --set endpoint.IP=${IP} \
  --set gloo.gatewayProxies.gatewayProxy.service.httpNodePort=${PORT} \
  --set global.extensions.extAuth.extauthzServerRef.namespace=${NAMESPACE} \
  --set gloo.gatewayProxies.gatewayProxy.service.type=NodePort \
  --set "dex.config.staticClients[0].redirectURIs[0]=http://${IP}:${PORT}/api/v1/callback,dex.config.staticClients[0].id=zadig,dex.config.staticClients[0].name=zadig,dex.config.staticClients[0].secret=ZXhhbXBsZS1hcHAtc2VjcmV0"
```

Open ```http://127.0.0.1:31500``` to access dashboard.
