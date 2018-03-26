# DraftServices

Welcome to DraftServices. This is a server that will ease your search of finding players from different major sports such as baseball, basketball and football.

The server was coded in java with the jax-rs web service api.

Data is also kept persistent by the use of a mongo database. In order for the server to communicate with your database please have a mongo
database up and ready and provide the database connection information on the properties file located in /src/main/resources. There you would just
provide the hostname and port.

This was tested and hosted by using a tomcat servet container.

Now in order to retrieve a player you would need to naviagate to a url with the following format:
 {hostname where server is hosted}:{port where server is hosted}/draft/player/{playerId}
 
 In order to retrieve players in a named sport you would need to navigate to url with the following format:
 {hostname where server is hosted}:{port where server is hosted}/draft/sport/{sportname}
