FROM java:8-jre
VOLUME /tmp
ARG DEPENDENCY=targetApp/dependency
ARG mainApp="targetApp.Application"
ARG remoteDebugArgs=""
COPY ${DEPENDENCY}/BOOT-INF/lib     /app/lib
COPY ${DEPENDENCY}/META-INF         /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENV MAIN_APP=$mainApp
ENV REMOTE_DEBUG_ARGS=$remoteDebugArgs
ENTRYPOINT java -cp "app:app/lib/*" $REMOTE_DEBUG_ARGS $MAIN_APP

