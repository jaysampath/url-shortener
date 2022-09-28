<h1> URL Shortener </h1>

<h2> Scope of the project </h2>

<p> When you hit the shortened URL, the database checks the shortcode and redirects you to the main URL. The URL shortener gets a long URL and returns a short URL.  </p>

<h2> Test cases </h2>

<ul>
   <li> Server validates the URL </li>
   <li> Shorten request returns the encoded short url </li>
   <li> Server need not encode for duplicate destination urls </li>
   <li> Server should redirect the user to destination url if a valid proxy is sent </li>
   <li> Server should throw an exception if incorrect proxy is sent. </li>
</ul>

<p>Run the tests in  to check the functionality.</p>

<h2> Database Model </h2>
<p> <b>urls</b> database has a single table <i>shortenUrls</i> which has encoded string ('proxy') as Id and destination url column </p>
<p> https://whimsical.com/mysql-level1-Vus5kGib74B83hAhwVHSi1 </p>

<h2> HLD Diagram </h2>

<p>  </p>

<h2> Technologies Involved </h2>

Java Spring boot, REST APIs, MySQL, Docker


<h2> Use Case</h2>

<p> Consider a scenario where the marketing/ sales team wants to get feedback on a product demo from a registered user. In the response, all they want to collect is one feedback whether the customer liked the product demo or not. For it to be meaningfully analyzed and uniquely identify the customer, they need to collect the registered details again - for example: name, phone and email.
</p>  

<p> So, the requirement is to convert the long URL to a text friendly URL which can be sent over SMS and when the customer opens the link - his registered info is pre-filled. </p>

<p> https://docs.google.com/forms/d/e/1FAIpQLSdF8g-Jw08ny_UhyqCvEBmZcdtpS53_1pP_kTPELS6lh_AQlA/viewform?usp=pp_url&entry.881032314=xxxemailaddressxxx&entry.285127870=xxphonexx&entry.1604712250=xxnamexx </p>

<p> Default short URL - https://forms.gle/rLSRXJDhswwu3ybh9 pointing to https://docs.google.com/forms/d/e/1FAIpQLSdF8g-Jw08ny_UhyqCvEBmZcdtpS53_1pP_kTPELS6lh_AQlA/viewform </p>

<p> Maps to a custom unique URL like: nexturl.in/x48tr  which contains the pre-filled information as well. Basically, for every customer, there will be a new and unique short URL created. </p>
