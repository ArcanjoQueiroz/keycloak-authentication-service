FROM liquibase/liquibase

ENV WAIT_VERSION 2.8.0
ADD --chown=liquibase:liquibase https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait