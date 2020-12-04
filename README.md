# grpc-User
gRPC based API service implementation  using Spring boot and Couchbase Server.

* Install the couchbase server. Please refer this [link](https://docs.couchbase.com/server/5.1/install/getting-started-docker.html)
* Create a bucket and configure index for the bucket.
* Add new user for the bucket and give the full access.
* Then configure the [application.properties](https://github.com/Senthuran100/grpc-User/blob/master/src/main/resources/application.properties)
* Then use a gRPC client(for ex:- [BloomRPC](https://github.com/uw-labs/bloomrpc)) to call the rpc methods.
