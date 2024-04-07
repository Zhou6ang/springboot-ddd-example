# Java DDD example
links:
  - https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/
  - https://www.baeldung.com/hexagonal-architecture-ddd-spring
  - https://www.baeldung.com/spring-persisting-ddd-aggregates
  

## Azure Web App Deployed by Azure Pipeline Example
1. Create azure portal and devops account
  - https://portal.azure.com/
  - https://dev.azure.com/
2. In Azure portal
  - create subscription
  - create resource group
  - create container registry
  - create azure web app with container
3. In Azure DevOps
  - Repos: import a project via github or azure repository
  - Pipelines: create new pipeline for project
  - Environment: create environment for project, e.g. dev, prod
  - Project setting
    - Agent pools: setup cicd via self-hosted agent or purchase Azure hosted agent. 
	- Service connections: 
	  - Docker registry: new service connection -> Docker registry or github -> Azure Container registry(created at step 2) -> service principle -> ... -> service connection name(e.g. abc)
	  - Azure resource manager: new service connection -> Azure resource manager -> Service principal(automatic) -> Subscription -> ... -> service connection name(e.g. bcd/Azure subscription 1)
  - Edit azure-pipeline.yml if needed and trigger it.
  
  
## Deployed to AKS manually
1. create imagePullSecret: kubectl create secret docker-registry my-acr-secret --docker-server=xxx.azurecr.io --docker-username=xxx --docker-password=xxx --docker-email=xxx -n azure-ddd
2. kubectl -n <namespace> apply -f my-aks-deployment.yml
3. kubectl -n <namespace> delete -f my-aks-deployment.yml

## Deployed to AKS via helm
1. create namespace & imagePullSecret: kubectl create secret docker-registry my-acr-secret --docker-server=xxx.azurecr.io --docker-username=xxx --docker-password=xxx --docker-email=xxx -n azure-ddd
2. helm install <my-app> ./helm --debug -n <namespace>
3. helm del <my-app> -n <namespace>