FROM centos:7

RUN yum -y update
RUN yum -y install java-11-openjdk

RUN useradd -ms /bin/bash configuser

COPY config/build/libs/*.jar /home/configuser
COPY config/docker/entrypoint.sh /home/configuser

RUN chmod +x /home/configuser/entrypoint.sh
RUN chown -R configuser:configuser /home/configuser

USER configuser
WORKDIR /home/configuser

EXPOSE 8080

ENTRYPOINT ["/home/configuser/entrypoint.sh"]