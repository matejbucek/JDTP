# JDTP 🖥
Java library which can be really helpful for creating Server and Client application.

## What can I use this library for?

You can create Server for example for your app and then create Client, which easily connects to your Server.
 You can send String, Bytes, or any Object you create trought this connection.

## How does it work?

Server starts and listen on given port. When client first connect, it immediately sends Open Frame. When this Open Frame arrives to the Server, it takes incoming hash, edits the hash and then sends it back to the Client. If this matches with Clients edited hash, handshake was successful. 

After this you can sand any Object you want throught this connection.

## How do add this library to my project?

You can use Maven:

Add this into your repositories.
```
<repository>
  <id>github</id>
  <name>JDTP</name>
  <url>https://maven.pkg.github.com/matejbucek/JDTP</url>
</repository>
```
And this into your pom.xml file.

```
<dependency>
  <groupId>cz.mbucek</groupId>
  <artifactId>JDTP</artifactId>
  <version>0.2-snapshot</version>
</dependency>
```

Or you can use JAR file in Releases and add that to build bath of your project.

## First steps

### Server

1. Create a Server.

2. Create ServerEndPoints and add them to the Server.

3. Then you can start Server and all should be working.

### Client

1. Create a Client.

2. Create ClientEndPoint and add that EndPoint into your Client.

3. Connect.

*I will add link to examples as soon as passible.*

*Look to wiki for more info.*
