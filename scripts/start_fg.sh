fgfs --geometry=480x360 \
--aircraft=c172p \
--timeofday=noon \
--disable-real-weather-fetch \
--generic=socket,in,50,localhost,10501,udp,fgcontrol \
--generic=socket,out,10,localhost,10502,udp,fgcontrol \
--generic=socket,out,1,localhost,10600,udp,fgcontrol
