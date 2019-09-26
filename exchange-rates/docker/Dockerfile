FROM centos:7

RUN yum -y update
RUN yum -y install java-11-openjdk
RUN yum -y install wget

RUN useradd -ms /bin/bash exrtuser

COPY exchange-rates/build/libs/*.jar /home/exrtuser
COPY exchange-rates/docker/entrypoint.sh /home/exrtuser

RUN chmod +x /home/exrtuser/entrypoint.sh
RUN chown -R exrtuser:exrtuser /home/exrtuser

USER exrtuser
WORKDIR /home/exrtuser

EXPOSE 8080

ENTRYPOINT ["/home/exrtuser/entrypoint.sh"]