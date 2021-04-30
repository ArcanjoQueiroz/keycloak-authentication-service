FROM node:14.15.5-alpine3.12

WORKDIR /app

RUN apk update && apk --no-cache add curl busybox-extras

COPY package.json package.json
COPY package-lock.json package-lock.json
RUN npm install --production
COPY . .

ENV NODE_ENV=production

CMD [ "node", "index.js" ]
