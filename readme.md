Demo project for trying a bundle spring boot, zookeeper and curator.

First, you need Zookeeper. You can start it from docker:
 - docker run --restart always -p 2181:2181 -d zookeeper
To use zookeeper console client use:
 - docker run -it --rm --link <name_of_started_zookeeper_container>:zookeeper zookeeper zkCli.sh -server zookeeper
We have worker as service which register itself on Zookeeper several time as different instance.
 - java -jar worker-0.0.1-SNAPSHOT.jar worker1 --server.port=9000
 - java -jar worker-0.0.1-SNAPSHOT.jar worker2 --server.port=9001
 - java -jar worker-0.0.1-SNAPSHOT.jar worker3 --server.port=9002
And we have manager as client who wants to use worker.
To call worker from manager use:
 - GET http://localhost:8080/manager/delegate
After calling this endpoint, curator will balance and call different instance of worker, using Round Robin strategy.
