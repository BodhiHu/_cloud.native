FROM ubuntu:20.04 
 
WORKDIR /app
 
RUN printf "\
# tencent mirrors\
deb http://mirrors.cloud.tencent.com/ubuntu/ focal main restricted universe multiverse\
deb http://mirrors.cloud.tencent.com/ubuntu/ focal-updates main restricted universe multiverse\
deb http://mirrors.cloud.tencent.com/ubuntu/ focal-backports main restricted universe multiverse\
deb http://mirrors.cloud.tencent.com/ubuntu/ focal-security main restricted universe multiverse\
\
deb https://mirrors.aliyun.com/kubernetes/apt kubernetes-xenial main\
deb https://mirrors.tuna.tsinghua.edu.cn/kubernetes/apt/ kubernetes-xenial main\
\
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

# Install Docker CE
RUN apt install -y apt-transport-https ca-certificates curl software-properties-common
# RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
RUN curl -fsSL https://mirrors.ustc.edu.cn/docker-ce/linux/ubuntu/gpg | apt-key add -
#RUN add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
RUN add-apt-repository "deb [arch=amd64] https://mirrors.ustc.edu.cn/docker-ce/linux/ubuntu $(lsb_release -cs) stable"
RUN apt update
RUN apt install -y docker-ce

# Setup OpenSSH
RUN apt -y install openssh-server openssh-client
RUN mkdir /var/run/sshd
# RUN ssh-keygen -q -t rsa -b 2048 -f ~/.ssh/id_rsa -N ""
# RUN chmod 600 ~/.ssh/id_rsa*
RUN service ssh start && sleep 1
# RUN eval "$(ssh-agent -s)" && ssh-add ~/.ssh/id_rsa

CMD tail -f /dev/null
