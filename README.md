<h1> URL Shortener </h1>

<h2> Scope of the project </h2>

<p> User creates an account in the app. The logged-in user provides a long URL and returns a short URL. The user can provide their own alias for the long url, 
the server accepts the alias if it's not already taken by someone else. When you hit the shortened URL, the database checks the shortcode and redirects you to the main URL. 
The user can see their own shorten urls. </p>

<h2> Test cases </h2>

<ul>
   <li> Server authenticates the users using JWT Authentication</li>
   <li> Server validates the URL </li>
   <li> Shorten request returns the encoded short url </li>
   <li> Server need not encode the duplicate destination urls for the same user </li>
   <li> User can provide their own alias for the long url, the server only accepts the alias if it's not already taken by someone else</li>
   <li> Server should redirect the user to destination url if a valid proxy/alias is sent </li>
   <li> Server should throw an exception if incorrect proxy/alias is sent. </li>
   <li> logged-in user can see their own shorten urls. </li>
   <li> Admin user can list all the users and shorten urls and cannot access user specific endpoints</li>
   <li> Server checks the shortened url is password protected or not. Returns a html page asking for password and then validates it. </li>
   <li> Server checks the expiration time of shortened url, returns a json saying expired. </li>
</ul>

<p> After starting the application, run the tests in <a href="https://github.com/jaysampath/url-shortener/tree/level2/src/test/java/com/project/url/shortener">
    test package</a> to check the functionality.</p>

<h2> Database Model </h2>
<p> Two tables are used, "user" for storing user specific information. "shortUrl" for storing encoded string/alias corresponding to long urls. </p>
<p> Find the entity-relationship diagram <a href="https://whimsical.com/mysql-level3-4mqNfuX8S7BtpsUMNqvLo4" target="_blank">here.</a> </p>

<h2> Runbook </h2>
<ol>
 <li> cd docker</li>
 <li> docker compose up </li>
 <li> Run UrlShortenerApplication.java</li>
</ol>

<p>  </p>

<h2> Technologies Involved </h2>

Java, Spring boot, Spring Security, JWT, REST APIs, MySQL, Docker


<h2> Use Case</h2>

<p> Consider a scenario where the marketing/ sales team wants to get feedback on a product demo from a registered user. In the response, all they want to collect is one feedback whether the customer liked the product demo or not. For it to be meaningfully analyzed and uniquely identify the customer, they need to collect the registered details again - for example: name, phone and email.
</p>  

<p> So, the requirement is to convert the long URL to a text friendly URL which can be sent over SMS and when the customer opens the link - his registered info is pre-filled. </p>

<p> https://docs.google.com/forms/d/e/1FAIpQLSdF8g-Jw08ny_UhyqCvEBmZcdtpS53_1pP_kTPELS6lh_AQlA/viewform?usp=pp_url&entry.881032314=xxxemailaddressxxx&entry.285127870=xxphonexx&entry.1604712250=xxnamexx </p>

<p> Default short URL - https://forms.gle/rLSRXJDhswwu3ybh9 pointing to https://docs.google.com/forms/d/e/1FAIpQLSdF8g-Jw08ny_UhyqCvEBmZcdtpS53_1pP_kTPELS6lh_AQlA/viewform </p>

<p> Maps to a custom unique URL like: nexturl.in/x48tr  which contains the pre-filled information as well. Basically, for every customer, there will be a new and unique short URL created. </p>
