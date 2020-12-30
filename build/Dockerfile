from ubuntu:focal

# tzdata fix: https://anonoz.github.io/tech/2020/04/24/docker-build-stuck-tzdata.html
RUN apt update && \
        DEBIAN_FRONTEND=noninteractive \
        TZ=Asia/Singapore \
        apt install -y openjdk-14-jdk p7zip-full ant git

WORKDIR /Flashtool
RUN git clone https://github.com/Androxyde/Flashtool .

ARG revision

RUN git checkout ${revision}

# Follow BUILD.md now ..

# We need to fetch and merge https://github.com/Androxyde/Flashtool/pull/141
RUN git remote add fix https://github.com/derMart/Flashtool && git pull fix fix_build

# The new BUILD.md:
# https://github.com/derMart/Flashtool/blob/fix_build/BUILD.md

RUN ant -buildfile ant/build-jar.xml
RUN ant -buildfile ant/deploy-release.xml
RUN ant -buildfile ant/setup-release.xml

