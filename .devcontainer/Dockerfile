FROM mcr.microsoft.com/devcontainers/base:ubuntu
RUN apt-get update && apt-get install vim -y \
    && mkdir -p /env && cd /env \
    && wget https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz \
    && wget https://services.gradle.org/distributions/gradle-6.3-all.zip \
    && wget https://github.com/fwcd/kotlin-debug-adapter/releases/download/0.4.3/adapter.zip \
    && wget https://github.com/fwcd/kotlin-language-server/releases/download/1.3.1/server.zip \
    && wget https://github.com/fwcd/kotlin-language-server/releases/download/1.3.1/grammars.zip \
    && tar -xzf openjdk-11.0.2_linux-x64_bin.tar.gz && rm openjdk-11.0.2_linux-x64_bin.tar.gz \
    && unzip gradle-6.3-all.zip && rm gradle-6.3-all.zip \
    && unzip adapter.zip && rm adapter.zip && mv adapter kotlin-adapter \
    && unzip server.zip && rm server.zip && mv server kotlin-server \
    && mkdir kotlin-grammars && mv grammars.zip kotlin-grammars && cd kotlin-grammars && unzip grammars.zip && rm grammars.zip
ENV JAVA_HOME /env/jdk-11.0.2
ENV GRADLE_HOME /env/gradle-6.3
ENV PATH $JAVA_HOME/bin:$GRADLE_HOME/bin:$PATH
