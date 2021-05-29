# Issues
(This file is same as Issues.md)

## Intermediate Assignment - Login App

Sign in via email, via Google, via Facebook work but sign in via Twitter doesn't work. I used Firebase to add the Google, Facebook and Twitter sign-in features. LoginApp.java file contains the functions for logging in using various methods and is the first file that is displayed when one opens the app. The error that comes when loggin in with Twitter is - 

```
<?xml version="1.0" encoding="UTF-8"?>
<hash>
<error>Callback URL not approved for this client application. Approved callback URLs can be adjusted in your application settings</error>
<request>/oauth/request_token</request>
</hash>
```

I don't know why the Callback URL is not approved. I copy-pasted the callback url displayed in Firebase console to Twitter's developer page and it still doesn't work. I searched extensively for a solution on the net but to no avail. 

