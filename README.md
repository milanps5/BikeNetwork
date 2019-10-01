# BikeNetwork

Application that displays all bike stores from API in a Recycler View which you can search with SearchView in the menu bar,  and when you open store it's showing company location on google maps.


BikeNetwork is app that uses http://api.citybik.es/v2/networks, api is pulled with Retrofit2 and stored in local SQLite Database and displayer with RecyclerView and CardView, Maps are done with GoogleMaps API and when you click on the marker you get pop-up dialog with bike store info. Also app has service with timer for saving cache and contacting the server when the cache times out(can set timer manualy between 15-60 minutes).

techonogies used:
-Retrofit2
-SQLite
-GSon Converter
-SearchView
-Service
-Maps Api
-RecyclerView
-CardView
-Stetho



