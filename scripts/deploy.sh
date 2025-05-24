#!/bin/bash
#########################################################
### Script para deploy da aplicação no Kubernetes     ###
#########################################################

# Definir ambiente (dev ou prod)
ENV=${1:-dev}
NAMESPACE=fast-food-${ENV}

echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\033[01;32m### Iniciando deploy para ambiente: ${ENV} ###\033[01;32m"
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\n\n"

# Verificar se namespace existe, senão criar
kubectl get namespace ${NAMESPACE} > /dev/null 2>&1 || kubectl create namespace ${NAMESPACE}

# Aplicar configurações usando kustomize
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\033[01;32m### Aplicando configurações com Kustomize ###\033[01;32m"
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\n\n"

kubectl apply -k ../k8s/overlays/${ENV} -n ${NAMESPACE}

# Verificar status dos pods
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\033[01;32m### Verificando status dos pods ###\033[01;32m"
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\n\n"

kubectl get pods -n ${NAMESPACE} --watch
