fgfs --geometry=480x360 \
--aircraft=c172p \
--timeofday=noon \
--disable-real-weather-fetch \
--generic=socket,out,2,localhost,10500,udp,fgcontrol \
--generic=socket,in,20,localhost,10501,udp,fgcontrol \
--generic=socket,out,20,localhost,10502,udp,fgcontrol
