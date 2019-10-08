FROM nginx:1.17.4

RUN apt-get update

RUN apt-get install -y systemd

RUN apt-get install -y wget
RUN wget https://releases.hashicorp.com/consul-template/0.22.0/consul-template_0.22.0_linux_amd64.tgz
RUN tar -zxvf consul-template_0.22.0_linux_amd64.tgz

RUN cp consul-template /usr/local/bin
RUN chmod +x /usr/local/bin/consul-template

RUN mkdir /usr/local/app-config

COPY gateway/consul-template-config.hcl /usr/local/app-config/consul-template-config.hcl
COPY gateway/app-config.ctmpl /etc/nginx/conf.d
COPY gateway/entrypoint.sh /usr/local/app-config

RUN chmod +x /usr/local/app-config/entrypoint.sh

RUN rm -f /etc/nginx/sites-enabled/default
RUN rm -f /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["/usr/local/app-config/entrypoint.sh"]