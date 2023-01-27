# Docker Hints

## Shell onto the docker conatiner
$$ docker exec -it $containerName bash

## Prune everything that's not used
$$ docker system prune --volumes

## Run image in front
$$ docker run -i -t IMAGE /bin/bash

## Run image in background
$$ docker run --detach IMAGE

## update container restart policy
##   unless-stopped - always restart unless it is explicitly stopped or Docker is restarted
$$ sudo docker update --restart=unless-stopped container-name

