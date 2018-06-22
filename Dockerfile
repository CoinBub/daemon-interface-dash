FROM ubuntu:16.04
EXPOSE 9998 9999 19998 19999

COPY .docker/ /
ADD https://github.com/dashpay/dash/releases/download/v0.12.2.3/dashcore-0.12.2.3-linux64.tar.gz /tmp/

RUN tar -xvf /tmp/dashcore-*.tar.gz -C /tmp/ \
 && cp /tmp/dashcore*/bin/*  /usr/local/bin \
 && rm -rf /tmp/dashcore* \
 && apt-get -y update \
 && apt-get -y install iproute2 \
                       netcat

#ADD ./bin /usr/local/bin
#RUN chmod a+x /usr/local/bin/*

RUN groupadd --gid 1000 dash \
 && useradd --uid 1000 --gid dash --shell /bin/false --create-home dash \
 && chmod +x /usr/local/bin/entrypoint

USER dash
WORKDIR /home/dash

RUN mkdir -p /home/dash/.dashcore

ENTRYPOINT [ "/usr/local/bin/entrypoint" ]
CMD ["dashd", "-printtoconsole"]