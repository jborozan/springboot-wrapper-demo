FROM alpine:3.9
MAINTAINER jurica.borozan

# fetch curl and maven (which gets java too)
RUN apk add --no-cache curl maven

# create .m2 dir if needed (karaf is using maven and might need it)
# it is good practice to share local m2 to docker so the later shall
# not spend ages fetching needed bundles - but be aware that it might
# write something as root user (like docker daemon), so use user 
# option on docker(-compose) command !
RUN [ -d "/root/.m2/repository" ] || mkdir -p /root/.m2/repository

# fetch and unpack karaf into /usr
RUN curl https://archive.apache.org/dist/karaf/4.2.6/apache-karaf-4.2.6.tar.gz | tar xz -C /usr

# correct scripts of karaf to call sh instead of bash (sh is actually bash in alpine)
RUN for file in client instance karaf setenv shell start status stop; do sed -i 's/#!\/bin\/bash/#!\/bin\/sh/' /usr/apache-karaf-4.2.6/bin/$file; done

# ensure that webconsole, blueprint and http services are started too
RUN sed -i 's/kar\/4\.2\.6/kar\/4\.2\.6,aries-blueprint,webconsole,http-whiteboard/' /usr/apache-karaf-4.2.6/etc/org.apache.karaf.features.cfg

# set JAVA_HOME (this time it is open JDK 1.8)
ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk

# expose some ports: karaf and 8080 for http server for servlets
EXPOSE 1099 8101 44444 8181

# and prepare run
ENTRYPOINT ["/usr/apache-karaf-4.2.6/bin/karaf", "server"]
