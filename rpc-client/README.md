# wastebinj RPC library

:coffee: Simple and easy to use RPC library for Java

## 1. Create service contract

```java
interface AdderServiceContract {
    int add(int x, int y);
}
```

## 2. Create service implementation

```java
class AdderService implements AdderServiceContract {
    @Override
    public int add(int x, int y) {
        return x + y;
    }
}
```

## 3. Start server

```
RPCServer server = new RPCServer(7112, AdderService.class);
server.start()
```

Execute request to server:

```
AdderServiceContract stub = new ByteBuddyStubBuilder<AdderServiceContract>()
        .stubClass(AdderServiceContract.class)
        .ip("localhost")
        .port(7112)
        .createStub();

int ret = stub.add(2, 2); // 4
```
