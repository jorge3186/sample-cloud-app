FROM node:10

RUN npm install -g yarn http-server uuid4 get-port

WORKDIR /app

COPY ui/yarn* ./
COPY ui/package*.json ./

RUN yarn install

COPY ui .
COPY ui/docker/entrypoint.sh .
RUN chmod +x entrypoint.sh

RUN yarn run build

EXPOSE 8080

ENTRYPOINT ["./entrypoint.sh"]