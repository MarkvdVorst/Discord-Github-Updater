# GitHub To Discord Updater

This bot is meant to get the Pull Request event from a GitHub Webhook and send the relevant information about the pull request to a specified discord channel. 

## Installation:
1. Install Apache Maven and Java JDK 21
2. Clone the repository to your system
3. Add a "secrets.json" file to the root of your project and fill in the following information:
```json
{
  "Token": "<Your discord token>",
  "ChannelId": "<Channel id of the discord channel you want the bot to send messages to>",
  "ApiKey": "<Api key (can be anything)"
}
```

4. Add an application.properties file at ./src/main/resources/application.properties and add the following:
```
spring.application.name=GithubDiscordUpdater
server.port=8080

spring.datasource.url=jdbc:h2:mem:pullrequestdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=<your username>
spring.datasource.password=<your pass>
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```
5. Run the following command `mvn spring-boot:run` and the program will boot up

And then you're done. The program is running locally now, so we have to do a couple more steps to make it fully function with GitHub.

1. Make sure to port forward port 8080 in your router settings. It usually falls under the "security category"
2. Get your public IPv4 address. Here's a link where you can find it if you don't know what it is: https://whatismyipaddress.com/
3. Go to your GitHub repository, go to settings and select "Webhooks"
   - Add a webhook
   - Add the following to the payload URL: http://<your ip or domain if you have it>:8080/pull-request
   - Set the Content type to application/json
   - Add your API key from earlier that you should have stored in secrets.json as a secret on the webhook.
   - For the events, select "Let me select individual events" and make sure "Pull Request" is the only event checked.

That's it! Now you have to invite your bot and set the channel id accordingly to the ChannelId property in the secrets.json

Make sure you have at least the intent to send messages on for your discord bot and also have the according message permissions set in the discord developer portal.
