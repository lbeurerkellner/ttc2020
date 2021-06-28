#FROM docker.io/adoptopenjdk/openjdk11:jre-11.0.11_9
FROM maven:3.8-adoptopenjdk-11

COPY . . 

RUN mvn verify

RUN mkdir results
WORKDIR results

CMD java -cp `find ../ -name '*.jar' -printf '%p:' | sed 's/:$//'` \
 org.junit.runner.JUnitCore de.hub.mse.ttc2020.solution.AllJavaPerformanceTests\
 && cat results.csv
