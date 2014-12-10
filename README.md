# recruiter

TH Wildau - web application security, research paper, topic recruiter

Tags: jee7, ear, ejb, war, maven, web, security, hibernate, h2/postgresql, wildfly, ssl, jsf, primefaces, lombok, ...

2014 by Sebastian Nemak and Sebastian Förster

## installation

### checkout source
- git clone https://github.com/der-basti/recruiter.git

### prepare wildfly 8.x server
- download (https://wildfly.org/downloads/)
- unzip
- create ssl cert (_/server.keystore_)
- update _/standalone-full.xml_ > _wildfly/standalone/configuration/standalone-full.xml_

### build project

#### with eclipse
- import maven project in eclipse luna (v.4.4.x)
	- install jboss tools plugin (JBoss AS)
		- http://download.jboss.org/jbosstools/updates/stable/luna/
- configure server (wildfly 8.x)
- add ear
- start server

#### with maven (experimental)
- mvn clean install
- ~~mvn checkstyle:checkstyle~~
- https://docs.jboss.org/wildfly/plugins/maven/latest/index.html
- mvn wildfly:deploy
- mvn wildfly:run / mvn wildfly:start
- mvn wildfly:redeploy / mvn wildfly:undeploy
- mvn wildfly:shutdown or ctrl+c

### browse
- http://127.0.0.1:8080/recruiter
