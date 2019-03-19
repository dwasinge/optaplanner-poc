# Delivery Scheduler POC

This is a sample backend scheduling application that makes use of Optaplanner running on a KIE Server in OpenShift.  There are a total of four projects to be deployed.

* Delivery Scheduler Solver - a KJAR project containing the Optaplanner solver configuration and Rules required to create a schedule.  Will be deployed to a KIE Server.
* Skills Service - a simple Spring Boot mircoservice that manages skill data required for delivery crew as well as delivery assignments
* Aircrew Service - a simple Spring Boot microservice that manages delivery aircrew data for crew members as well as their availability
* Delivery Service - a Spring Boot microservice that manages schedules, delivery assignments, and roles.  It also acts as an integration service that will gather all the required data as input to the solver and call the KIE server.

The sample application requires an openshift cluster to be available for both the CI/CD pipelines as well as the deployment of the application.  All the required namespaces and tooling can be deployed using the Red Hat Labs CI CD project found here:

https://github.com/rht-labs/labs-ci-cd

This application has been tested using the tag: v3.11.4

Below are the installation instructions.  Please see the corresponding READMEs in the projects for usage.


## Prerequisites

### Create Openshift Namespaces Required for Build and Deployment

1. Clone the labs-ci-cd repository from above and switch to the tag.

`git clone git@github.com:rht-labs/labs-ci-cd.git`

`cd labs-ci-cd`

`git checkout -b v3.11.4`

2. Log in to the OpenShift cluster using the OC command providing your credentials when prompted:

`oc login`

3. From the root directory of the labs-ci-cd git repository, install the required roles for the openshift-applier:

`ansible-galaxy install -r requirements.yml --roles-path=roles`

4. Create the namespaces:

`ansible-playbook site.yml -e ci_cd_namespace=optaplanner-scheduler-ci-cd -e dev_namespace=optaplanner-scheduler-dev -e test_namespace=optaplanner-scheduler-test -l bootstrap`

5. Deploy CI CD tooling:

`ansible-playbook site.yml -e ci_cd_namespace=optaplanner-scheduler-ci-cd -e dev_namespace=optaplanner-scheduler-dev -e test_namespace=optaplanner-scheduler-test -l tools`

NOTE:  Make sure all pods in the optaplanner-scheduler-ci-cd namespace have come up before proceeding.  In particular, SonarQube, Jenkins, and Nexus.

## Optaplanner POC Installation

Clone this project:

`git clone git@github.com:dwasinge/optaplanner-poc.git`

`cd optaplanner-poc`

### Deploy the KIE Server Project

1. Change directories to the .openshift-applier folder under delivery-scheduler-solver

`cd ./delivery-scheduler-solver/.openshift-applier`

2. Install the required roles for the openshift-applier:

`ansible-galaxy install -r requirements.yml --roles-path=roles`

3. Install all service objects into the OpenShift cluster

`ansible-playbook apply.yml -i inventory/`

This will created the required pipeline to build the application as well as the deployment configurations for the dev and test environments.  The first pipeline should start which will check out the source code, build/package the artifact, and create the container image.  Once the image has been created, it will be deployed to the dev namespace.  There is a manual promotion to the test environment so you will have to click proceed through the pipeline in OpenShift or through Jenkins.

### Delpoy the Skills Service

1. Change directories to the .openshift-applier folder under delivery-scheduler-solver

`cd ./skills-service/.openshift-applier`

2. Install the required roles for the openshift-applier:

`ansible-galaxy install -r requirements.yml --roles-path=roles`

3. Install all service objects into the OpenShift cluster

`ansible-playbook apply.yml -i inventory/`

This will created the required pipeline to build the application as well as the deployment configurations for the dev and test environments.  The first pipeline should start which will check out the source code, build/package the artifact, and create the container image.  Once the image has been created, it will be deployed to the dev namespace.  There is a manual promotion to the test environment so you will have to click proceed through the pipeline in OpenShift or through Jenkins.

4.  The Swagger API for the application can be found at the following path:  <hostname>/swagger-ui.html.

### Deploy the Delivery Aircrew Service

1. Change directories to the .openshift-applier folder under delivery-scheduler-solver

`cd ./aircrew-service/.openshift-applier`

2. Install the required roles for the openshift-applier:

`ansible-galaxy install -r requirements.yml --roles-path=roles`

3. Install all service objects into the OpenShift cluster

`ansible-playbook apply.yml -i inventory/`

This will created the required pipeline to build the application as well as the deployment configurations for the dev and test environments.  The first pipeline should start which will check out the source code, build/package the artifact, and create the container image.  Once the image has been created, it will be deployed to the dev namespace.  There is a manual promotion to the test environment so you will have to click proceed through the pipeline in OpenShift or through Jenkins.

4.  The Swagger API for the application can be found at the following path:  <hostname>/swagger-ui.html.

### Deploy the Delivery Service

1. Change directories to the .openshift-applier folder under delivery-scheduler-solver

`cd ./delivery-service/.openshift-applier`

2. Install the required roles for the openshift-applier:

`ansible-galaxy install -r requirements.yml --roles-path=roles`

3. Install all service objects into the OpenShift cluster

`ansible-playbook apply.yml -i inventory/`

This will created the required pipeline to build the application as well as the deployment configurations for the dev and test environments.  The first pipeline should start which will check out the source code, build/package the artifact, and create the container image.  Once the image has been created, it will be deployed to the dev namespace.  There is a manual promotion to the test environment so you will have to click proceed through the pipeline in OpenShift or through Jenkins.

4.  The Swagger API for the application can be found at the following path:  <hostname>/swagger-ui.html.
