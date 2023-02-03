# README

## Prerequisite

1 Install Vagrant

2 Start VMs with provions enabled:
> Remember to uncomment the provisions of Vagrantfile
```
~ vagrant up --provision
```

## Setup K8S cluster with Rancher/RKE

1 Install rancher/rke
```
https://rancher.com/docs/rke/latest/en/installation/
```

2 Deploy kubernetes cluster with RKE:
```
~ rke up --config rke-cluster.yml
```

3 Set alias for kubctl & helm:
```
~ alias kubectl="kubectl --kubeconfig=\"kube_config_rke-cluster.yml\""
~ alias helm="helm --kubeconfig=\"kube_config_rke-cluster.yml\""
```

## Deploy Rancher to cluster:

Follow steps from:
> [Install/Upgrade Rancher on a Kubernetes Cluster](https://ranchermanager.docs.rancher.com/pages-for-subheaders/install-upgrade-on-a-kubernetes-cluster)

To set up Rancher,(in short)

* Add the Helm chart repository
```
~ helm repo add rancher-stable https://releases.rancher.com/server-charts/stable
```
* Create a namespace for Rancher
```
~ kubectl create namespace cattle-system
```
* Choose your SSL configuration
> We're using the default:
> Rancher Generated Certificates (Default),	ingress.tls.source=rancher

* Install cert-manager (unless you are bringing your own certificates, or TLS will be terminated on a load balancer)

These instructions are adapted from the [official cert-manager documentation](https://cert-manager.io/docs/installation/supported-releases/#installing-with-helm)

```
# If you have installed the CRDs manually instead of with the `--set installCRDs=true` option added to your Helm install command, you should upgrade your CRD resources before upgrading the Helm chart:
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.7.1/cert-manager.crds.yaml

# Add the Jetstack Helm repository
helm repo add jetstack https://charts.jetstack.io

# Update your local Helm chart repository cache
helm repo update

# Install the cert-manager Helm chart
helm install cert-manager jetstack/cert-manager \
  --namespace cert-manager \
  --create-namespace \
  --version v1.7.3
```

Once you’ve installed cert-manager, you can verify it is deployed correctly by checking the cert-manager namespace for running pods:

```
kubectl get pods --namespace cert-manager

NAME                                       READY   STATUS    RESTARTS   AGE
cert-manager-5c6866597-zw7kh               1/1     Running   0          2m
cert-manager-cainjector-577f6d9fd7-tr77l   1/1     Running   0          2m
cert-manager-webhook-787858fcdb-nlzsq      1/1     Running   0          2m
```

* Install Rancher with Helm and your chosen certificate option

```
helm install rancher rancher-stable/rancher \
  --namespace cattle-system \
  --set hostname=<eg: k8s-rancher.bodhitree.org> \
  --set bootstrapPassword=<eg: bodhi>
```

Wait for Rancher to be rolled out:
```
kubectl -n cattle-system rollout status deploy/rancher
Waiting for deployment "rancher" rollout to finish: 0 of 3 updated replicas are available...
deployment "rancher" successfully rolled out
```

* Verify that the Rancher server is successfully deployed

After adding the secrets, check if Rancher was rolled out successfully:

```
kubectl -n cattle-system rollout status deploy/rancher
Waiting for deployment "rancher" rollout to finish: 0 of 3 updated replicas are available...
deployment "rancher" successfully rolled out
```

check the status of the deployment by running the following command:

```
kubectl -n cattle-system get deploy rancher
NAME      DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
rancher   3         3         3            3           3m
```

* Save your options

## Notes

### Run `kubectl` to inspect cluster
```
~ kubectl --kubeconfig="kube_config_rke-cluster.yml" get -A nodes
```

### Run `helm` with cluster
```
~ helm --kubeconfig="kube_config_rke-cluster.yml"
```

### Vagrant

Up VMs:
```
vagrant up
```

SSH to VM:
```
vagrant ssh node-1
```

Down VMs:
```
vagrant halt
```

Others:
```
vagrant suspend
vagrant resume
vagrant status
```
