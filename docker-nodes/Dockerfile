FROM ubuntu:20.04 
 
WORKDIR /app
 
RUN printf "\
deb http://10.67.69.28/artifactory/ubuntu-remote/ focal main restricted universe multiverse\
deb http://10.67.69.28/artifactory/ubuntu-remote/ focal-security main restricted universe multiverse\
deb http://10.67.69.28/artifactory/ubuntu-remote/ focal-updates main restricted universe multiverse\
deb http://10.67.69.28/artifactory/ubuntu-remote/ focal-backports main restricted universe multiverse\
deb https://mirrors.aliyun.com/kubernetes/apt kubernetes-xenial main\
deb https://mirrors.tuna.tsinghua.edu.cn/kubernetes/apt/ kubernetes-xenial main\
\n\n\
$(cat /etc/apt/sources.list)\
" > /etc/apt/sources.list

RUN apt-get update
RUN apt-get install -y vim-tiny net-tools iputils-ping curl bash-completion
RUN chsh -s /bin/bash
RUN printf "\
$(cat ~/.bashrc)\
\n\
\n\
export PS1=\"🌻\[\033[01;32m\] \W\[\033[00m\] \"\
\n\
" > ~/.bashrc


CMD tail -f /dev/null
