POC Circuit Breaker with Hystrix

This is a proof of concept of the spike using hystrix in AUTHX.

Installation

	We have two maven projects: MySystem and MyExternalDependecy. Import each one and run as spring boot application.
	The performance folder contains jmeter script to perform tests like real world.

Hystrix dashboard
	Clone  https://github.com/Netflix/Hystrix.git
	Go to /Hystrix/hystrix-dashboard
	Run it using ../gradlew jettyRun
	Go to url http://localhost:7979/hystrix-dashboard/ and add http://localhost:9080/hystrix.stream. Click and Add Stream and then in monitor Stream.
	Play it

Credits
	Authx Team
