POC Circuit Breaker with Hystrix

This is a proof of concept of the spike using hystrix in AUTHX.

Installation

	We have two maven projects: MySystem and MyExternalDependecy. Import each one and run as spring boot application.
	The performance folder contains jmeter script to perform tests like real world.

Hystrix dashboard
	Clone  https://github.com/Netflix/Hystrix.git
	Go to /Hystrix/hystrix-dashboard
	Run it using ../gradlew jettyRun

Credits
	Authx Team
