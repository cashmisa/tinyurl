
#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/jre-11-openjdk
export PATH=$JAVA_HOME/bin:$PATH

export DB_HOST=testdb.cml0xgt6qnl1.us-east-2.rds.amazonaws.com
export DB_PORT=3306
export DB_NAME=tinyurl
export DB_USERNAME=root
export DB_PASSWORD=ThePassword

echo "Starting service..."
nohup java -jar tinyurl-0.0.1-SNAPSHOT.jar > output.log 2>&1 &
echo $! > save_pid.txt

