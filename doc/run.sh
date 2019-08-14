#!/bin/bash

APP_HOME=/data/app/crm/crm-api

cd $APP_HOME

start()
{
    echo -n $"Starting $prog: "
    echo "Hello crm ..."
    nohup java -jar  destroyer-api.jar > run.log 2>&1 & new_agent_pid=$!
    echo "start status = $?"
    echo "$new_agent_pid" > crm.pid
}
stop()
{

     if [ -f crm.pid ];then
                    SPID=`cat crm.pid`
                      if [ "$SPID" != "" ];then
                         kill -9  $SPID
                         rm -f crm.pid
                         echo "stop success"
                      fi
     else
        echo "pid not found"
     fi
}


case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        sleep 1
        start
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart}"
        RETVAL=1
esac
exit $RETVAL