FROM jboss/keycloak:7.0.1

USER root

ENV JBOSS_HOME=/opt/jboss/keycloak
ENV TOOLS_HOME=/opt/jboss/tools
ENV TZ=GMT

# Adding themes
ADD themes ${JBOSS_HOME}/themes

# Adding oracle database driver
RUN mkdir -p ${TOOLS_HOME}/databases/oracle
ADD oracle/module.xml ${TOOLS_HOME}/databases/oracle

RUN mkdir -p ${JBOSS_HOME}/modules/system/layers/base/com/oracle/ojdbc7/main
ADD oracle/ojdbc7.jar ${JBOSS_HOME}/modules/system/layers/base/com/oracle/ojdbc7/main
ADD oracle/module.xml ${JBOSS_HOME}/modules/system/layers/base/com/oracle/ojdbc7/main

RUN mkdir -p ${TOOLS_HOME}/cli/databases/oracle
ADD oracle/change-database.cli ${TOOLS_HOME}/cli/databases/oracle
ADD oracle/standalone-configuration.cli ${TOOLS_HOME}/cli/databases/oracle
ADD oracle/standalone-ha-configuration.cli ${TOOLS_HOME}/cli/databases/oracle

# Adding docker-entrypoint
ADD docker-entrypoint.sh ${TOOLS_HOME}
RUN chmod u+x ${TOOLS_HOME}/docker-entrypoint.sh

# Adding SPIs
ADD spi/ejb-jpa-keycloak-spi/target/ejb-jpa-keycloak-spi.jar ${JBOSS_HOME}/standalone/deployments

EXPOSE 8080
EXPOSE 8787
