fgfs --geometry=480x360 \
--aircraft=c172p \
--timeofday=noon \
--disable-real-weather-fetch \
--generic=socket,out,20,localhost,10500,udp,flightbot \
--generic=socket,in,20,localhost,10501,udp,flightbot \
--httpd=10502
