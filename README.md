# Introduction

This is demo of software adapter that enables switching between technologies, in this case from OSGI to Springboot. 
It assumes having legacy code that uses Blueprint XML and convert it to Springboot XML configuration.
It is done without recompiling existing Java Code. There is minimal java adapter code on Springboot side.

# Howto use it

This project is composed out of 3 OSGI bundles
1. bundle-api: definition of service interface
1. bundle-one: implementation of service interface
1. bundle-two: consumer of service interface and implementation of Managed Service and Servlet interfaces

It uses Docker or Docker-Compose to enable and run Karaf as OSGI runtime platform and Spring boot framework. Dockerfiles are prepared with maven. 

To build just issue:
1. mvn clean install

For OSGI based demo:
1. go to karaf-docker/target/docker
1. issue "docker-compose up" to run Karaf container
1. use URL "http://localhost:8181/message" to fetch content

For Springboot based demo:
1. go to springboot-wrapper/target/docker
1. issue "docker-compose up" to run Karaf container
1. use URL "http://localhost:9090/message" to fetch content

In both cases, there will be one greeting message displayed along with read properties.

# Disclaimer

It is provided as it is - for the demonstration purposes.