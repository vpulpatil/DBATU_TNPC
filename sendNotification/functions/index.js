var functions = require('firebase-functions');
var admin = require('firebase-admin');
 
admin.initializeApp(functions.config().firebase);
exports.sendNotification = functions.database.ref('/Notifications/{notificationId}')
        .onWrite(event => {
 
        // Grab the current value of what was written to the Realtime Database.
        var eventSnapshot = event.data;
        var str = (eventSnapshot.child("title").val());
        console.log(str);
 
        var topic = "android";
        var payload = {
            data: {
                title: eventSnapshot.child("title").val(),
                author: eventSnapshot.child("message").val()
            }
        };
 
        // Send a message to devices subscribed to the provided topic.
        return admin.messaging().sendToTopic(topic, payload)
            .then(function (response) {
                // See the MessagingTopicResponse reference documentation for the
                // contents of response.
                console.log("Successfully sent message:", response);
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
            });
        });