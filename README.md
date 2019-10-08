# sample-cloud-app
This app is a simple currency converter that is unnecessarily split into microservices 
to demonstrate the use of containers, docker services, kubernetes, and spring boot apps.

### Pre-requisites
- gradle
- docker
- kubernetes cluster (minikube is fine for local cluster)

### To start all services locally with docker
```bash
cd sample-cloud-app

gradle clean \
    config:bootJar \
    currency-converter:bootJar \
    exchange-rates:bootJar

docker-compose up --build
```
This will generate the following containers
- sample-cloud-app_discovery_1
- sample-cloud-app_config_1
- sample-cloud-app_gateway_1
- sample-cloud-app_currencyconverter_1
- sample-cloud-app_exchangerates_1
- sample-cloud-app_ui_1


Check `docker-compose.yml` for reference on how these containers are scaled. You can scale the `ui`, `exchangerates`, and `currencyconverter`
services with the following commands
```bash
docker-compose scale ui=3
docker-compose scale currencyconvert=3
docker-compose scale exchangerates=5
```  
When doing a scale, the gateway container, which is running `nginx` will get updates from
the discovery container (running consul) and register the new service instances
and apply round robbin load-balancing

### Kubernetes

When looking to run these containers in a kubernetes cluster, the first thing
that needs to be done is to build the containers with the appropriate container
names.
```bash
cd sample-cloud-app

gradle clean \
    config:bootJar \
    currency-converter:bootJar \
    exchange-rates:bootJar

# build config
docker build -t sample-config -f config/docker/Dockerfile .

# build exchange-rates
docker build -t sample-exchange-rates -f exchange-rates/docker/Dockerfile .

# build currency-converter
docker build -t sample-currency-converter -f currency-converter/docker/Dockerfile .

# build ui
docker build -t sample-ui -f ui/docker/Dockerfile .

# build gateway
docker build -t sample-gateway -f gateway/Dockerfile .
```

Once that is complete and your kubernetes cluster is up and running we can start
to create the deployments, pods, and services found in the k8s folders.
```bash
eval $(minikube docker-env)
# or if not using minikube
eval $(kubeadm docker-env)

kubectl create secret generic git --from-literal GIT_PASSWORD=$GIT_PASSWORD

kubectl create -f k8s/sample-discovery.yml
kubectl create -f config/k8s/sample-config.yml
kubectl create -f currency-converter/k8s/sample-currency-converter.yml
kubectl create -f exchange-rates/k8s/sample-exchange-rates.yml
kubectl create -f ui/k8s/sample-ui.yml
kubectl create -f gateway/k8s/sample-gateway.yml
```
These yml files will hold deployments, pods and services that will run within the 
k8s cluster.