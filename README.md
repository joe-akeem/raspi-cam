Raspi-Cam
=========

Various examples to record video on Raspberry Pi using the Raspberry Pi camera and a button or PIR sensor to trigger recording.

Written in Java.

Get it
------
`git clone https://github.com/joe-akeem/raspi-cam.git`

Build it
--------
`cd raspi-cam`
`mvn install`

Prepare
-------
`mkdir /home/pi/capture`

Run
---
only needed if a remote debugger shall be connected:
`export MAVEN_OPTS="-Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006"`

1. Start the camera by pushing the button, stop it by releasing the button
   -> the button must be connected to GPIO02 (27)
 
`sudo mvn -PbuttonCam install exec:java`


2. Play a sound when the PIR notices movement
   -> the button must be connected to GPIO02 (27)

`sudo mvn -PwelcomeSound install exec:java`

Videos end up in /home/pi/capture

Play Videos on Raspberry Pi
---------------------------
`omxplayer filename.h264`

Convert captured videos from *.h264 to mp4
------------------------------------------
E.g. on Ubuntu:
`sudo apt-get install gpac`
`MP4Box -add filename.h264 filename.mp4`