FROM hseeberger/scala-sbt:11.0.11-oraclelinux8_1.5.4_2.12.13

RUN microdnf update 

RUN microdnf install -y xorg-x11-server-Xvfb libXrender libXtst

WORKDIR /app

COPY . .

CMD [ "sbt", "run" ]
