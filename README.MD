## Bitcoin Automat

### Environment
You need to specify the access tokens for Bitcoin.de  
apiKey  
apiSecret  


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
1. Upload the backend jar to EC2 (I'm using [Filezilla](https://filezilla-project.org/))
1. Install [screen](https://www.howtoforge.com/linux_screen) so we can let processes run in background
1. Enter `screen` to start a new session
1. Start the backend with `java -jar backend-0.0.1-SNAPSHOT.jar`
1. Ctrl a d - Detaches a screen session (without killing the processes)
1. Install [Apache on EC2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-lamp-amazon-linux-2.html)
1. Add a [.htaccess](https://www.elated.com/password-protecting-your-pages-with-htaccess/) to protect your front end
1. Add [port forwarding](https://stackoverflow.com/questions/17161345/how-to-open-a-web-server-port-on-ec2-instance) to port 8080
1. Add [TLS to your AWS instance](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/SSL-on-amazon-linux-2.html)



