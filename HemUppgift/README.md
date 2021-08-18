I have used the following to build, run and test this project:
**Java SE 15
Spring Boot 2.4.2
Windows 10 x64
IntelliJ IDEA 2021.1**


To build jar file, you need to:

Open the Maven window in IntelliJ on the right and follow the instructions:

1- click on **lifecycle**

2- click on **package** and run it.

3- This will build a jar file. It's name is **music_mashup-0.0.1-SNAPSHOT.jar** in **target**. target folder is on the left side.

To **run** the project you can follow:

1- You can copy the file and paste it in your desktop.

2- Open command prompt.

3- **cd Dekstop**.

4-**dir** (To make sure that the jar file is in your desktop)

5-**java -jar music_mashup-0.0.1-SNAPSHOT.jar** (Then you can test run the link below in postman or browser)

Here is some examples to test the API, This link can be run in Postman or browser

http://localhost:9000/restApi/showArtists/ed3f4831-e3e0-4dc0-9381-f5649e9df221

http://localhost:9000/restApi/showArtists/410c9baf-5469-44f6-9852-826524b80c61

http://localhost:9000/restApi/showArtists/5b11f4ce-a62d-471e-81fc-a69a8278c7da

http://localhost:9000/restApi/showArtists/0ab49580-c84f-44d4-875f-d83760ea2cfe
