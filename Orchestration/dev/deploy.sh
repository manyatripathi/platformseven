oc config use-context platformtestingthree-dev-apps
oc create configmap app-config --from-file=src/main/resources/config-dev/application-dev.yml
oc apply -f Orchestration/dev/deployment.yaml
oc apply -f Orchestration/dev/service.yaml