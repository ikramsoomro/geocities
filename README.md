# mc-geocities
spring-boot app which determines if two cities are connected. Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.


Note: I have implemented graph traversal using Depth First method.
I have tested code with limited set of data due to time limitation, I would like to test with test data having 1000+ cities.
I can implement using Breadth First also. ~Ikram Soomro


Coding exercise
Langalia, Bhavin <Bhavin.Langalia@mastercard.com>	Tue, Feb 13, 2018 at 5:08 PM
To: "ikramsoomro@gmail.com" <ikramsoomro@gmail.com>
Hi Ikram,

 

To follow up on the interview process steps, as a next step, you’re asked to write a program in Java which determines if two cities are connected.

Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.

 

List of roads is available in a file.

File contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities.

 

It will be deployed as a spring-boot app and expose one endpoint:

http://localhost:8080/connected?origin=city1&destination=city2

 

Your program should respond with ‘yes’ if city1 is connected to city2, ’no’ if city1 is not connected to city2.

Any unexpected input should result in a ’no’ response.

 

Example:

city.txt content is:

Boston, New York

Philadelphia, Newark

Newark, Boston

Trenton, Albany

 

http://localhost:8080/connected?origin=Boston&destination=Newark

Should return yes

http://localhost:8080/connected?origin=Boston&destination= Philadelphia

Should return yes

http://localhost:8080/connected?origin=Philadelphia&destination=Albany

Should return no

 

———————————————

 

This exercise is a take-home assignment. I expect it to be completed within 1-3 days.

The completed assignment should be shared with us for review.

 

Regards,

-- 

Bhavin Langalia
Senior Software Engineer
Digital Payments Technology

Mastercard
114 5th Ave, 10th Floor, New York, NY
tel 914-536-4085 | mobile 630-765-1248 
