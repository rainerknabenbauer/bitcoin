## Bitcoin Automat

### Environment
You need to specify the access tokens for Bitcoin.de  
apiKey  
apiSecret  

### Package to Jar
Skip tests to package Jar
`mvn clean package -DskipTests`

### MongoDB
Start your MongoDB: 
`docker run -p 27017:27017 mongo`

### Self-hosting
You can host all of your infrastructure yourself. For a full setup, you need
* A PHP capable web space for hosting the Bitcoin.de SDK
* A HTML web space for hosting the Vue.js UI
* A virtual machine to host the Kotlin backend

##### SDK : PHP
I'm using all-inkl.com. Any web host with a free tier supporting PHP should do.

##### UI : HTML
This should be hosted on the same machine as the backend.
The backend should not publicly disclose the trading endpoints.
That's why we need the UI calling localhost endpoints - endpoints being protected by the firewall.

* [Basic CRUD app with Vue.js](https://codesource.io/create-a-crud-application-using-vue-node-and-mongodb/)

##### Backend : Kotlin
Any vhost should do. I'm using the free tier of 
[Amazon EC2](https://aws.amazon.com/free/?all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=tier%2312monthsfree&awsm.page-all-free-tier=1).
1. Install [MongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-amazon/) on your EC2 instance
1. Upload the backend jar to EC2 (I'm using [Filezilla](https://filezilla-project.org/))
1. Install [screen](https://www.howtoforge.com/linux_screen) so we can let processes run in background
1. Enter `screen` to start a new session
1. Start the backend with `java -jar backend-0.0.1-SNAPSHOT.jar`
1. Ctrl a d - Detaches a screen session (without killing the processes)
1. Install [Apache on EC2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-lamp-amazon-linux-2.html)
1. Add a [.htaccess](https://davidwalsh.name/password-protect-directory-using-htaccess) to /var/www/html to protect your front end ([more information on EC2](https://stackoverflow.com/a/25634496/6500730))
1. Add [port forwarding](https://stackoverflow.com/questions/17161345/how-to-open-a-web-server-port-on-ec2-instance) to port 8080
1. Add [TLS to your AWS instance](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/SSL-on-amazon-linux-2.html)

### Provision server
You need a server with a proper CPU for this. If you do not have access to a proper machine,
start by disabling `Gatherer.shortTermPublicTradeHistory()` and choose a more relaxed the scheduling.
Use `top` and see your CPU and RAM load during execution.

I use a dedicated server with CentOS with the following setup:

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

It is highly recommended that you [secure your MongoDB](https://www.cyberciti.biz/faq/how-to-secure-mongodb-nosql-production-database/)!

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
Reload systemd and start the daemon
```
systemctl daemon-reload
```






















