Raspi-Cam
=========

Record video on Raspberry Pi using the Raspberry Pi camera and a button or PIR sensor to trigger recording.

Written in Java.

Get
---
`git clone https://github.com/joe-akeem/raspi-cam.git`

Build
-----
`cd raspi-cam`
`mvn install`

Run
---
only needed if a remote debugger shall be connected:
`export MAVEN_OPTS="-Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006"`

`sudo mvn exec:java`