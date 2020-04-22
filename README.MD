## Bitcoin Automat

### Package to Jar
Skip tests to package Jar
`mvn clean package -DskipTests`

### MongoDB
Start your local MongoDB: 
`docker run -p 27017:27017 mongo`

### Self-hosting
You can host all of your infrastructure yourself. For a full setup, you need
* A PHP capable web space for hosting the Bitcoin.de SDK
* A HTML web space (Apache server) for hosting the Vue.js UI
* A virtual machine to host the Kotlin backend (JVM)

##### SDK : PHP
I'm using all-inkl.com. Any web host with a free tier supporting PHP should do.

##### UI : HTML
Currently under construction. Trying new things with Vue.js

* [Basic CRUD app with Vue.js](https://codesource.io/create-a-crud-application-using-vue-node-and-mongodb/)

##### Backend : Kotlin
1. Install [Apache on EC2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-lamp-amazon-linux-2.html)
1. Add a [.htaccess](https://davidwalsh.name/password-protect-directory-using-htaccess) to /var/www/html to protect your front end ([more information on EC2](https://stackoverflow.com/a/25634496/6500730))
1. Add [port forwarding](https://stackoverflow.com/questions/17161345/how-to-open-a-web-server-port-on-ec2-instance) to port 8080
1. Add [TLS to your AWS instance](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/SSL-on-amazon-linux-2.html)

### Provision server
You need a server with a proper CPU for this. If you do not have access to a proper machine,
start by disabling `Gatherer.shortTermPublicTradeHistory()` and choose a more relaxed the scheduling.
Use `top` and see your CPU and RAM load during execution.

After experimenting with Amazon EC2 I quit because of pricing and performance, I went back to Hetzner
and got the cheapest machine. The application always seemed to freeze after a couple of scheduled 
executions. After some bugfixing I noticed the CPU load, which was constantly at >98%. So I upgraded 
to a `Dedicated Root Server SB29`, currently running at 50% CPU max:
* Intel Core i7-3770
* 2x HDD SATA 3,0 TB
* 2x RAM 8192 MB DDR3 

As the Analyzer is still work in progress, we need some processing to calculate our trading decisions.

_It is highly recommended_, that you start by checking your firewall settings! Currently the trading API does not require any authentication!
For starters, you might want to consider blocking everything except for port 22.

The server is running on CentOS with the following setup:


#### Basic installation
```
yum update
yum install nano
yum install java-11-openjdk-devel
```

#### MongoDB installation (without docker)
Add the MongoDB repository to the yum packet manager. Create the file
```
nano /etc/yum.repos.d/mongodb-org.repo
```
and add the configuration
```
[mongodb-org-3.4]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/3.4/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-3.4.asc
``` 
Install MongoDB using yum
```
yum install mongodb-org
```
Start MongoDB
```
systemctl start mongod
systemctl enable mongod
```

_It is highly recommended_ that you [secure your MongoDB](https://www.cyberciti.biz/faq/how-to-secure-mongodb-nosql-production-database/)!

#### Application setup
We want to start the service as a daemon. Move the application to `/opt/bitcoin/backend-0.0.1-SNAPSHOT.jar` and the configuration to 
`/opt/bitcoin/config/configuration.yml` if you want to just copy paste the setup.

To add the service to the systemd discovery, create the file
```
nano /lib/systemd/system/bitcoin.service
```
and add the configuration
```
[Unit]
Description=Bitcoin Java Process Restart Upstart Script
After=auditd.service systemd-user-sessions.service time-sync.target
 
[Service]
User=root
TimeoutStartSec=0
Type=simple
KillMode=process
WorkingDirectory=/opt/bitcoin
ExecStart=/bin/sh -c "exec java -jar /opt/bitcoin/backend-0.0.1-SNAPSHOT.jar -Dspring.config.location=config/credentials.yml"
LimitNOFILE=5555
 
[Install]
WantedBy=multi-user.target
```
Reload systemd and add the application to autostart
```
systemctl daemon-reload
systemctl enable bitcoin
```

_It is highly recommended_, that you [add a custom user](https://www.digitalocean.com/community/tutorials/how-to-create-a-sudo-user-on-centos-quickstart) to execute the application!

#### Configuration
You need to specify the access tokens for Bitcoin.de and add a custom user
to access the Rest API.
Create a file `./config/credentials.yml` with
```
server:
  port: 8888

bitcoin:
  apiKey: CUSTOMIZE
  apiSecret: CUSTOMIZE

default:
  user: CUSTOMIZE
  password: CUSTOMIZE

spring:
  profiles:
    active: dev
  data:
    mongodb:
      uri: mongodb://127.0.0.1:27017
      database: bitcoin

---
spring:
  profiles: dev
api:
  isLive: false

---
spring:
  profiles: prod
api:
  isLive: true
```

### Start the application

We're all set! You can easily start the application by using the typical on board methods.
```
systemctl start bitcoin
```

### Useful commands
List all running java processes
```
ps -few | grep java
```
Show current application log (live)
```
journalctl -f
```
Show full log
```
journalctl -u bitcoin.service --no-pager
```














