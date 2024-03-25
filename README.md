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
	- Service connections: new service connection -> Docker registry or github -> Azure Container registry(created at step 2) -> service principle -> ... -> service connection name(e.g. abc)
  - Edit azure-pipeline.yml if needed and trigger it.
  
  
