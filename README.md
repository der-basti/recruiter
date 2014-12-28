# recruiter

TH Wildau - web application security, research paper, topic recruiter

2014 (IM14) by Sebastian Nemak and Sebastian Förster

Tags: jee7, java 8, ear, ejb, war, maven, web, security, hibernate, h2/postgresql, wildfly, ssl, jsf, primefaces, lombok, ...

## installation

require: java 8, wildfly 8.x, eclipse luna, git, maven

### checkout source
- git clone https://github.com/der-basti/recruiter.git

### prepare wildfly 8.x server
- download (https://wildfly.org/downloads/)
- unzip
- create ssl cert (follow _/server.keystore_ instructions)
- update _/standalone-full.xml_
	- copy to _wildfly/standalone/configuration/standalone-full.xml_
	- update settings (e.g. mail config)

### build project

#### with eclipse
- import maven project in eclipse luna (v. 4.4.x)
	- hint: you can install all these plugins via eclipse marketplace
	- install jboss tools plugin (JBoss AS)
		- http://download.jboss.org/jbosstools/updates/		stable/luna/
	- install findbugs plugin
		- http://findbugs.cs.umd.edu/eclipse
	- install checkstyle plugin
		- http://eclipse-cs.sf.net/update
- configure server (wildfly 8.x)
	- set runtime config _standalone-full.xml_
	- remove launch config vm arg "-XX:MaxPermSize=256m"
- update _/recruiter-web/src/webapp/WEB-INF/web.xml_
	- context-param > _primefaces.PRIVATE_CAPTCHA_KEY_ and _primefaces.PUBLIC_CAPTCHA_KEY_
- add ear & start server

#### with maven (experimental)
- mvn clean install
- ~~mvn checkstyle:checkstyle~~
- https://docs.jboss.org/wildfly/plugins/maven/latest/index.html
- mvn wildfly:deploy
- mvn wildfly:run / mvn wildfly:start
- mvn wildfly:redeploy / mvn wildfly:undeploy
- mvn wildfly:shutdown or ctrl+c

### browse
- http://127.0.0.1:8080/recruiter or direct https://127.0.0.1:8443/recruiter

## production mode
- use postgres, maria, oracle, ... database
- update _persistence.xml_ > hibernate.hbm2ddl.auto > validate or update
- update _web.xml_ > javax.faces.PROJECT_STAGE > Production
- update log level > ERROR