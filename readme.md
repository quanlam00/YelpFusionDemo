YELP FUSION DEMO

I picked the MVVM architecture as the core design for the app. This design allow me to reduce coupling of UI logic and Business Logic/Data Logic. Per the requirement, RxJava is the main tool supporting the flow of the app. I choose Retrofit and OkHttp as they together provide a wonderful RxJava Support as Http client. In conjunction with RxJava, LiveData is also used to obtain similar Observers  structure like RxJava, but with better support for the View and ViewModel lifecycle. I slightly want the list of search results to be in its own screen. But I keep it on the same screen as the search box to show a slightly complex UI. Because of time limitation a bland and simple design was adopted. However, this design should run well on all devices of all dimension, since the layout design is scalable. I implemented the extra location text box for searching all locations, and the business detail page.

The app support a simple solution for infinite scrolling through the list of result. As the user scrolling through the list and hit and limit, a call for more items will be make, and the list will be extended almost immediately. 

Third parties library that I used:
- Retrofit
- OkHttp
- Picasso - a very powerful Image Loading library also from Square.

PowerMockito, Mockito, JUnit and Expresso was used for testing.

Trying to mock static method in Kotlin is very challenging and I had spend a bit too much time only to fail to mock static method. In the end I had to find a work around.

If there is more time, a better UI design will be in queue. I also would like to finish the autocomplete feature which I could not get in time. The Detail screen can be improve to display all available information and reviews. The image album is also a nice feature to have in the detail page that would take some time.




