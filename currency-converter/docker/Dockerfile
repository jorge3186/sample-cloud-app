FROM centos:7

RUN yum -y update
RUN yum -y install java-11-openjdk
RUN yum -y install wget

RUN useradd -ms /bin/bash curruser

COPY currency-converter/build/libs/*.jar /home/curruser
COPY currency-converter/docker/entrypoint.sh /home/curruser

RUN chmod +x /home/curruser/entrypoint.sh
RUN chown -R curruser:curruser /home/curruser

USER curruser
WORKDIR /home/curruser

EXPOSE 8080

ENTRYPOINT ["/home/curruser/entrypoint.sh"]