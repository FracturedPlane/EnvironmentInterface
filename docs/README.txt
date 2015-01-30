Hello,

Attached is what I have done so far to create a good interface between Prolog and a C++ environment.

There are two Classes I use to test the application tcp.server.TCPSocketClient to represent the Unreal environemnt ( The unreal environement can only use TCP connections ) and udp.server.UDPTestServer to represent the connection to Prolog. Last the Class unrealinterface.client.UEIClient Is the application. 

On top of what I said the interface is also designed to make it easy to work with different data transfer languages, like JSON, XML and prolog  lists structures. Basically the EnvironmentInterface will receive a message and depending on the handler you set to the Entity ( In the environment Interface ) will be converted to another format before it is sent to the Prolog Server and visa-versa.

Take care.
- Glen B