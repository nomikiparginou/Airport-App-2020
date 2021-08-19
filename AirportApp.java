import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.time.*;
import java.util.ArrayList; 
import java.time.format.DateTimeFormatter;  


public class AirportApp extends JFrame implements ActionListener{
	
	JMenu update, browse, exit;
	static JMenuItem u1, u2, u3, u4, u5, b1, b2, b3, b4;
	private static final String[] types = {"Economy Seat","Business Seat"};
	private static final String[] airlines = {"AirCan","USAir","BritAir","AirFrance","LuftAir","ItalAir"};
	private static final String[] cities = {"Toronto","Montreal","New York","Chicago","London","Edinbrugh","Paris","Nice","Berlin","Bonn","Rome","Naples"};
  AirportApp(){  
	JFrame f= new JFrame("Airport Application");  
	JMenuBar mb=new JMenuBar();  
    update=new JMenu("Update Database");
	u1=new JMenuItem("New Flight");  
	u2=new JMenuItem("New Booking");
	u3=new JMenuItem("New Passenger");
	u4=new JMenuItem("Cancel Booking");
	u5=new JMenuItem("PayOff a Booking");
	update.add(u1); update.add(u3); update.add(u2); update.add(u4); update.add(u5);
	mb.add(update);
	browse=new JMenu("Browse Database");
	b1=new JMenuItem("Flights");
	b2=new JMenuItem("Fully Booked Flights");
	b3=new JMenuItem("Cancelled Tickets");
	b4=new JMenuItem("Toronto-New York flights");
	browse.add(b1); browse.add(b2); browse.add(b3); browse.add(b4);
	mb.add(browse);
	
	f.setJMenuBar(mb);  
	f.setSize(400,300);  
	f.setLayout(null);  
	f.setVisible(true); 
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  
	public static void main(String args[]) throws SQLException{
		
		Connection conn = null;
	    String url = "jdbc:mysql://localhost:3306/";
	    String dbName = "Airport";
	    String driver = "com.mysql.jdbc.Driver";
	    String userName = "root"; 
	    String password = "";
	    Random rand = new Random(System.currentTimeMillis());
	    JFrame fr = new JFrame("Inputs"); 
	    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
	    ArrayList<String> flights = new ArrayList<String>();
	    ArrayList<String> phone = new ArrayList<String>();
	    ArrayList<String> fax = new ArrayList<String>();
	    ArrayList<String> email = new ArrayList<String>();
	    ArrayList<String> bookings = new ArrayList<String>();
	    ArrayList<String> IDs = new ArrayList<String>();
	    ArrayList<Integer> seat = new ArrayList<>();
	    
	    //Connecting to Database
	    try {
	        Class.forName(driver).newInstance();
	        conn = DriverManager.getConnection(url+dbName,userName,password);
	        System.out.println("Connected to database");
	    }catch(Exception e) 
	    {
	        e.printStackTrace();
	    }

	    //Fill tables country, airline and bookingagency if they are empty
	    try {
	        Statement st = conn.createStatement();
	        st.execute("SET FOREIGN_KEY_CHECKS = 0 ");

	        ResultSet rc = st.executeQuery("SELECT * from country");
	        if (rc.next() == false) 
	        {
	          st.executeUpdate("INSERT INTO country VALUES('Canada','Canada Dollars',0.66,'AirCan',2.3),"+
	                " ('USA','USA Dollars',0.92,'USAir',4.5),"+
	                " ('Italy','Italian Lira',0.0005,'ItalAir',1.3),"+
	                " ('Great Britain','GBP',0.88,'BritAir',1.9),"+
	                " ('France','French Francs',1.05,'AirFrance',2.6),"+
	                " ('Germany','Deutsche Mark',1.95,'AirLuft',2.5)");
	        }

	        ResultSet ra = st.executeQuery("SELECT * from airline");
	        if (ra.next() == false) 
	        {
	          st.executeUpdate("INSERT INTO airline VALUES('AirFrance','France'),"+
	                " ('BritAir','United Kingdom'),"+
	                " ('LuftAir','Germany'),"+
	                " ('ItalAir','Italy'),"+
	                " ('AirCan','Canada'),"+
	                " ('USAir','USA')");
	        }

	        ResultSet rb = st.executeQuery("SELECT * from bookingagency");
	        if (rb.next() == false) 
	        {
	          st.executeUpdate("INSERT INTO bookingagency VALUES('YYZ','Toronto','Canada'),"+
	                " ('YUL','Montreal','Canada'),"+
	                " ('JFK','New York','USA'),"+
	                " ('ORD','Chicago','USA'),"+
	                " ('LCY','London','Great Britain'),"+
	                " ('EDI','Edinbrugh','Great Britain'),"+
	                " ('CDG','Paris','France'),"+
	                " ('NCE','Nice','France'),"+
	                " ('FCO','Rome','Italy'),"+
	                " ('NAP','Naples','Italy'),"+
	                " ('TXL','Berlin','Germany'),"+
	                " ('CGN','Bonn','Germany')");
	        }

	        
	        new AirportApp();
	        
	        //New Flight
	        u1.addActionListener(new ActionListener(){
			    public void actionPerformed(ActionEvent e){
			    	
			    	   String flightCode = Integer.toString(rand.nextInt(9999));
	                   String airline = (String) JOptionPane.showInputDialog(fr, "Airline to add flight for: ","Creating new Flight", JOptionPane.QUESTION_MESSAGE,null,airlines,airlines[0]);
	                   String depDate = JOptionPane.showInputDialog(fr, "Enter Departure Date (yyyy-mm-dd): ");
	                   String depTime = JOptionPane.showInputDialog(fr, "Enter Departure Time (hh:mm): ");
	                   String depDateTime = depDate+" "+depTime;
	                   String flightdur;
	                   do {
	                        flightdur = JOptionPane.showInputDialog(fr, "Enter Flight Duration (in minutes): ");
	                        if(Integer.parseInt(flightdur)<=0)
	                        	JOptionPane.showMessageDialog(null,"Flight duration can't be negative or 0 ","New Flight",JOptionPane.WARNING_MESSAGE);
	                   }while(Integer.parseInt(flightdur)<=0);;
	                   String business;
                       do {
                           business = JOptionPane.showInputDialog(fr, "Does it contain Bussiness Seats? (yes/no)");
                       }while((!business.equalsIgnoreCase("yes"))&&(!business.equalsIgnoreCase("no")));
	                   String smokeSeat;
                       do {
                           smokeSeat = JOptionPane.showInputDialog(fr, "Does it contain Smoker seats? (yes/no)");
                       }while((!smokeSeat.equalsIgnoreCase("yes"))&&(!smokeSeat.equalsIgnoreCase("no")));
	                   String depCity = (String) JOptionPane.showInputDialog(fr, "Choose Departure City: ","Creating new Flight", JOptionPane.QUESTION_MESSAGE,null,cities,cities[0]);
	                   String arrCity;
	                   do {
	                        arrCity = (String) JOptionPane.showInputDialog(fr, "Choose Arrival City: ","Creating new Flight", JOptionPane.QUESTION_MESSAGE,null,cities,cities[0]);
	                        
	                        if (depCity.equals(arrCity))
	                        	JOptionPane.showMessageDialog(null,"Arrival City is the same as Departure City ","New Flight",JOptionPane.WARNING_MESSAGE);
	                   }while(depCity.equals(arrCity));;

	                   String depCode="";
	                   String arrCode="";
	                   try {
	                   ResultSet result;
	                   result=st.executeQuery("SELECT citycode FROM bookingagency WHERE cityname='"+depCity+"'");
	                   result.next();
	                   depCode=result.getString("citycode");
	                   result=st.executeQuery("SELECT citycode FROM bookingagency WHERE cityname='"+arrCity+"'");
	                   result.next();
	                   arrCode=result.getString("citycode");
	                   }catch(SQLException e1) {
							e1.printStackTrace();
					   }
	                   String ticketPrice;
	                   do {
	                        ticketPrice = JOptionPane.showInputDialog(fr, "Enter default Eco Ticket Price: ");
	                        if (Integer.parseInt(ticketPrice)<=0)
	                        	JOptionPane.showMessageDialog(null,"Ticket price can't be negative or 0 ","New Flight",JOptionPane.WARNING_MESSAGE);	
	                   }while(Integer.parseInt(ticketPrice)<=0);
	                   int classtype=0;
	                   int ecoSeats = 100;
	                   int resEco=0;
	                   int busSeats=0;
	                   int smokers=0;
	                   double price = Double.parseDouble(ticketPrice);
	                   if(business.equalsIgnoreCase("yes")) {
	                     busSeats= 20;
	                     ecoSeats=80;
	                     classtype = 1;
	                   }
	                   if(smokeSeat.equalsIgnoreCase("yes")) {
	                	   smokers=1;
	                   }
	                   int resBus=0;
	                   String update = "INSERT INTO flight VALUES('"+flightCode+"',1,'"+classtype+"','"+smokers+"','"+depDateTime+"',"
	                                                                +flightdur+","+busSeats+","+resBus+","
	                                                                +ecoSeats+","+resEco+",'"+airline+"','"+depCode+"','"+arrCode+"',"+price+")";
		               
	                   try {
	                   st.executeUpdate(update); 
	                   JOptionPane.showMessageDialog(null,"Flight was added successfully!","New Flight",JOptionPane.INFORMATION_MESSAGE);
	                   }catch(SQLException s){
	                	   JOptionPane.showMessageDialog(null,"Flight wasn't added successfully\nPlease try again! ","New Flight",JOptionPane.WARNING_MESSAGE);
	           	       }
			    }
	        });
	        
	        //New Booking
			u2.addActionListener(new ActionListener(){ 
				  public void actionPerformed(ActionEvent e){ 
					
					try {
				    ResultSet result;
					result=st.executeQuery("SELECT * FROM flight");
					if(result.next()==false) {
						JOptionPane.showMessageDialog(null,"No flights from any airline currently\nPlease add from 'New Flight' option","New Booking",JOptionPane.INFORMATION_MESSAGE);
					}else{
					String name = JOptionPane.showInputDialog(fr, "Enter Name: ");
					String surname = JOptionPane.showInputDialog(fr, "Enter Last Name: "); 
						result = st.executeQuery("SELECT passengerid FROM passenger WHERE name='"+name+"' and surname='"+surname+"'");
						if(result.first()==false) 
							JOptionPane.showMessageDialog(null,"No Passengers Under this name\nPlease add from 'New Passenger' option","New Booking",JOptionPane.WARNING_MESSAGE);
                      else{
						  do {
						    IDs.add(result.getString("passengerid"));
						  }while(result.next());
						Object[] options = IDs.toArray();
					    String passid = (String) JOptionPane.showInputDialog(fr, "Passenger IDs under the name\n"+name+" "+surname+"\nChoose: ","Creating new Booking", 
					   		                                                 JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
					   do{ 
					    String airline = (String) JOptionPane.showInputDialog(fr, "Airline to show flights for: ","Creating new Booking", JOptionPane.QUESTION_MESSAGE,null,airlines,airlines[0]);
						result = st.executeQuery("SELECT flightcode, availability FROM flight WHERE airline_airlinecode = '"+airline+"'");
						while (result.next()) {
							if(result.getInt("availability")==1);
							  flights.add(result.getString("flightcode"));
					   }
					  if(flights.size()==0)
						  JOptionPane.showMessageDialog(null,"No Flights for this airline currently\nPlease choose another one","New Booking",JOptionPane.WARNING_MESSAGE); 
					 }while(flights.size()==0);
					Object[] options2 = flights.toArray();
					String flightcode = (String) JOptionPane.showInputDialog(fr, "Select flight to make booking: ","Creating new Booking", JOptionPane.QUESTION_MESSAGE,null,options2,options2[0]);						
					flights.clear();
					result = st.executeQuery("SELECT * FROM flight WHERE flightcode = "+flightcode);
					result.next();
					int smoker = result.getInt("smokerseat");
					int ecoSeat=result.getInt("economyseat");
					int rsvdEco=result.getInt("reseconomy");
					int duration = result.getInt("flightdur");
					double price = result.getDouble("ticketprice");
					String date = result.getString("depDateTime");
					String departure = date.substring(0, date.length()-5);
					LocalDateTime depDateTime = LocalDateTime.parse(departure,df);
					LocalDateTime arrDateTime = depDateTime.plusMinutes(duration);
					String depcity = result.getString("depcity");
					String arrcity = result.getString("arrcity");
					int classtype =result.getInt("classtype");
					result = st.executeQuery("SELECT country_countryname FROM bookingagency WHERE citycode='"+depcity+"'");
					result.next();
					String depCountry = result.getString("country_countryname");
					result = st.executeQuery("SELECT exchangerate, tax FROM country WHERE countryname='"+depCountry+"'");
					result.next();
					double exrate = result.getDouble("exchangerate");
					double tax = result.getDouble("tax");
					double totaltax = exrate*tax;
					result = st.executeQuery("SELECT country_countryname FROM bookingagency WHERE citycode='"+arrcity+"'");
					result.next();
                    String arrCountry = result.getString("country_countryname");
					result = st.executeQuery("SELECT exchangerate, tax FROM country WHERE countryname='"+arrCountry+"'");
					result.next();
					exrate = result.getDouble("exchangerate");
					tax = result.getDouble("tax");
					totaltax = totaltax + (exrate*tax);
					
				    String bookCode = Integer.toString(rand.nextInt(99999));
				    java.sql.Date bookDate = new java.sql.Date(System.currentTimeMillis());
				    String seatType="";
				    if(classtype==0)
				        seatType = "Economy Seat";
				    else if(classtype==1 && (rsvdEco==ecoSeat))
				    	seatType="Business Seat";
				    else
				    	seatType = (String) JOptionPane.showInputDialog(fr, "Select Seat Type ","Creating new Booking", JOptionPane.QUESTION_MESSAGE,null,types,types[0]);
				    if(seatType.equals("Business Seat")) {
				      price = price*1.5;
				      st.executeUpdate("UPDATE flight SET resbusiness = resbusiness + 1 WHERE flightcode = '"+flightcode+"'");
				    }else 
				      st.executeUpdate("UPDATE flight SET reseconomy = reseconomy + 1 WHERE flightcode = '"+flightcode+"'");
				    if(smoker==1)
				    	JOptionPane.showInputDialog(fr, "Do you want a smoker seat? (yes/no): ");
				    st.executeUpdate("UPDATE flight SET availability=0 WHERE (resbusiness=businessseat and reseconomy=economyseat)");
				    double total = price + totaltax; 
				    String deposit = JOptionPane.showInputDialog(fr, "Total Booking Price is: "+String.format("%.2f", total)+"\n Enter how much will be paid now.");
				    double dep =  Double.parseDouble(deposit);  
				    double balance = total - dep;
				    String status;
				    if(balance==0)
				      status = "paid";
				    else
				      status = "reserved";
				    
					String update = "INSERT INTO booking VALUES('"+bookCode+"','"+bookDate+"','"+seatType+"','"+arrDateTime+"',"+price+","+String.format("%.2f", total)+",'"
					                                              +status+"',"+String.format("%.2f", dep)+","+String.format("%.2f", balance)+",'"+passid+"','"+flightcode+"')";
					
	                   try {
	                   st.executeUpdate(update); 
	                   JOptionPane.showMessageDialog(null,"Booking was added successfully!","New Booking",JOptionPane.INFORMATION_MESSAGE);
	                   }catch(SQLException s){
	                	   JOptionPane.showMessageDialog(null,"Booking wasn't added successfully\nPlease try again! ","New Booking",JOptionPane.WARNING_MESSAGE);
	           	       }
                     }
					}
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
				  }
			 });
			
			//New Passenger
			u3.addActionListener(new ActionListener(){ 
				  public void actionPerformed(ActionEvent e){ 
					  
					  String passID = Integer.toString(rand.nextInt(9999));
					  String name = JOptionPane.showInputDialog(fr, "Enter Name: ");
					  String surname = JOptionPane.showInputDialog(fr, "Enter Last Name: ");
					  String country = JOptionPane.showInputDialog(fr, "Enter Country of origin: ");
					  String address = JOptionPane.showInputDialog(fr, "Enter Home Address: ");
					  String update = "INSERT INTO passenger VALUES('"+passID+"','"+name+"','"+surname+"','"+country+"','"+address+"')";
					  String input="";
					  do {
						  input=JOptionPane.showInputDialog(fr, "Enter email address: ");
						  if(input!=null)
							email.add(input);
					  }while(input!=null);
					  do {
						  input=JOptionPane.showInputDialog(fr, "Enter Phone Number (country code - local code - number )\n" + 
						                                        "(+xxx-xxx-xxxxx) ");
						  if(input!=null)
							phone.add(input);
					  }while(input!=null);
					  do {
						  input=JOptionPane.showInputDialog(fr, "Enter Fax Number (country code - city code -  fax number )\n" + 
						  		                                "(+xxx-xxx-xxxx) ");
						  if(input!=null)
							fax.add(input);
					  }while(input!=null);

					  
					  try {
						  st.executeUpdate(update);
						  int i=0;
						  while(i<email.size()){
							  st.executeUpdate("INSERT INTO emailaddress VALUES('"+email.get(i)+"','"+passID+"')");
							  i+=1;
						  }
						  email.clear();
						  i=0;
						  while(i<fax.size()){
							  st.executeUpdate("INSERT INTO faxnumber VALUES('"+Integer.toString(rand.nextInt(99999))+"','"+fax.get(i)+"','"+passID+"')");
							  i+=1;
						  }
						  fax.clear();
						  i=0;
						  while(i<phone.size()){
							  st.executeUpdate("INSERT INTO phonenumber VALUES('"+Integer.toString(rand.nextInt(99999))+"','"+phone.get(i)+"','"+passID+"')");
							  i+=1;
						  }
						  phone.clear();
						  JOptionPane.showMessageDialog(null,"Passenger was added successfully!","New Passenger",JOptionPane.INFORMATION_MESSAGE);
					  }catch(SQLException e1){
						  JOptionPane.showMessageDialog(null,"Passenger wasn't added successfully\nPlease try again! ","New Passenger",JOptionPane.WARNING_MESSAGE);
		              }
				  }
			 });
			
			//Cancel Booking
	        u4.addActionListener(new ActionListener(){
			    public void actionPerformed(ActionEvent e){
			    	
			    	String name = JOptionPane.showInputDialog(fr, "Enter Name: ");
			    	String surname = JOptionPane.showInputDialog(fr, "Enter Last Name: ");
                    ResultSet result;
                    try {
                    	result=st.executeQuery("SELECT passengerid FROM passenger WHERE name='"+name+"' and surname='"+surname+"'");
						if(result.first()==false) 
							JOptionPane.showMessageDialog(null,"No Passengers Under this name\nPlease add from 'New Passenger' option","New Booking",JOptionPane.WARNING_MESSAGE);
                      else{
						  do {
						    IDs.add(result.getString("passengerid"));
						  }while(result.next());
						Object[] options = IDs.toArray();
						IDs.clear();
					    String passid = (String) JOptionPane.showInputDialog(fr, "Passenger IDs under the name\n"+name+" "+surname+"\nChoose: ","Creating new Booking", 
					   		                                                 JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
               
      
                    	result=st.executeQuery("SELECT bookingcode, flight_flightcode FROM booking WHERE passenger_passengerid='"+passid+"'");
                    	String output="Bookings under this name: \n";
                        if(result.first()==false) {
                        	  JOptionPane.showMessageDialog(null,"No Bookings under this name","Cancel Booking",JOptionPane.INFORMATION_MESSAGE);
                          }else {
                        	  do{
                                  bookings.add(result.getString("bookingcode"));
                        		  output+="booking code: "+result.getString("bookingcode")+"  for flight: "+result.getString("flight_flightcode")+"\n";	  
                        	  }while(result.next());
                        	  Object[] options2 = bookings.toArray();
          					  String bookingcode = (String) JOptionPane.showInputDialog(fr,output+"Select booking to cancel","Cancel Booking", JOptionPane.QUESTION_MESSAGE,
          							                                                        null,options2,options2[0]);
          					  bookings.clear();
          					  st.executeUpdate("UPDATE booking SET seatstatus='canceled' WHERE bookingcode='"+bookingcode+"'");
                          }
                      }
                    }catch (SQLException e1) {
						e1.printStackTrace();
				    }
			    }
	        });
	        
	        //PayOff a Booking
	        u5.addActionListener(new ActionListener(){
			    public void actionPerformed(ActionEvent e){
                     
			    	String name = JOptionPane.showInputDialog(fr, "Enter Name: ");
			    	String surname = JOptionPane.showInputDialog(fr, "Enter Last Name: ");
                    ResultSet result;
					try {
    						result = st.executeQuery("SELECT passengerid FROM passenger WHERE name='"+name+"' and surname='"+surname+"'");
    						if(result.first()==false) 
    							JOptionPane.showMessageDialog(null,"No Passengers Under this name\nPlease add from 'New Passenger' option","New Booking",JOptionPane.WARNING_MESSAGE);
                          else{
    						  do {
    						    IDs.add(result.getString("passengerid"));
    						  }while(result.next());
    						Object[] options = IDs.toArray();
    					    String passid = (String) JOptionPane.showInputDialog(fr, "Passenger IDs under the name\n"+name+" "+surname+"\nChoose: ","Creating new Booking", 
    					   		                                                 JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                    	result=st.executeQuery("SELECT bookingcode FROM booking WHERE passenger_passengerid='"+passid+"' and seatstatus='reserved'");
                        if(result.first()==false) {
                        	  JOptionPane.showMessageDialog(null,"No Reserved Bookings under this name","Payoff a Booking",JOptionPane.INFORMATION_MESSAGE);
                          }else {
                        	  do{
                                  bookings.add(result.getString("bookingcode"));	  
                        	  }while(result.next());
                        	  Object[] options2 = bookings.toArray();
          					  String bookingcode = (String) JOptionPane.showInputDialog(fr,"Select booking to pay off","Cancel Booking", JOptionPane.QUESTION_MESSAGE,
          							                                                        null,options2,options2[0]);
          					  bookings.clear();
          					  result=st.executeQuery("SELECT balance FROM booking WHERE bookingcode='"+bookingcode+"'");
          					  result.next();
          					  String balance = result.getString("balance");
          					  JOptionPane.showMessageDialog(null,"Your Balance for this booking is: "+balance+"\n Do you wish to pay it off?",
          							                              "PayOff a Booking",JOptionPane.INFORMATION_MESSAGE);
          					  st.executeUpdate("UPDATE booking SET seatstatus='paid', balance=0, deposit=totalcost WHERE bookingcode='"+bookingcode+"'");
                          }
                       }
                    }catch (SQLException e1) {
						e1.printStackTrace();
				    }
			    }
               
	        });
			
	        //Flights
			b1.addActionListener(new ActionListener(){ 
				  public void actionPerformed(ActionEvent e){ 
					  
					  String airline = (String) JOptionPane.showInputDialog(fr, "Airline to show flights for: ","Flights", JOptionPane.QUESTION_MESSAGE,null,airlines,airlines[0]);
					  ResultSet result;
					  String output="";
					  try{
							result = st.executeQuery("SELECT flightcode, depcity, arrcity, depDateTime FROM flight WHERE airline_airlinecode = '"+airline+"'");
							if(result.first()==false){
								 JOptionPane.showMessageDialog(null,"No flights for this airline currently","Flights",JOptionPane.INFORMATION_MESSAGE);  
		                        }else {
		                      	  do{
		                      		  output+=result.getString("flightcode")+":  "+result.getString("depcity")+"-"+result.getString("arrcity")+"  "+result.getString("depDateTime")+"\n";	  
		                      	  }while(result.next());
		                      	JOptionPane.showMessageDialog(null, "Flights For "+airline+": \n"+output,"Flights",JOptionPane.INFORMATION_MESSAGE);
		                        }
                     
				    
				     }catch (SQLException e1) {
						e1.printStackTrace();
				     }
				 }	
			 });
			
			//Fully booked flights
			b2.addActionListener(new ActionListener(){ 
				  public void actionPerformed(ActionEvent e){ 
					  
	                ResultSet result;
	                String output="";
	                try {
	                	result = st.executeQuery("SELECT flightcode, depcity, arrcity, depDateTime FROM flight WHERE availability=0");
                        if(result.first()==false) {
                      	  JOptionPane.showMessageDialog(null,"No Fully Booked flights currently","Fully Booked Flights",JOptionPane.INFORMATION_MESSAGE);
                        }else {
                      	  do{
                      		  output+=result.getString("flightcode")+":  "+result.getString("depcity")+"-"+result.getString("arrcity")+"  "+result.getString("depDateTime")+"\n";	  
                      	  }while(result.next());
                      	  JOptionPane.showMessageDialog(null, output,"Fully Booked Flights: \n",JOptionPane.INFORMATION_MESSAGE);
                        }
	                }catch(SQLException e1){
	                	e1.printStackTrace();
	                }
				    
				  }
			 });
			
			//Cancelled bookings
			b3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
					ResultSet result;
					String output="";
					try {
						result = st.executeQuery("SELECT bookingcode, passenger_passengerid, flight_flightcode, seattype FROM booking WHERE seatstatus='canceled'");
						if(result.first()==false) {
							JOptionPane.showMessageDialog(null,"No Cancelled Bookings currently","Cancelled Bookings",JOptionPane.INFORMATION_MESSAGE);
						}else {
							do {
								output+="Booking Code: "+result.getString("bookingcode")+",  passenger id: "+result.getString("passenger_passengerid")+
										", for flight: "+result.getString("flight_flightcode")+"\n";
								if(result.getString("seattype").equals("Business Seat"))
									seat.add(1);
								else
									seat.add(0);
                                flights.add(result.getString("flight_flightcode"));
								bookings.add(result.getString("bookingcode"));
							}while(result.next());
							for(int i=0; i<bookings.size(); i++) {
								if(seat.get(i)==1)
									st.executeUpdate("UPDATE flight SET resbusiness = resbusiness - 1 WHERE flightcode = '"+flights.get(i)+"'");
								else
								    st.executeUpdate("UPDATE flight SET reseconomy = reseconomy - 1 WHERE flightcode = '"+flights.get(i)+"'");
								st.executeUpdate("DELETE FROM booking WHERE bookingcode = '"+bookings.get(i)+"'");
							}
							bookings.clear();
							flights.clear();
							seat.clear();
							JOptionPane.showMessageDialog(null, output,"Cancelled Bookings: \n",JOptionPane.INFORMATION_MESSAGE);
						}	
					}catch(SQLException e1){
	                	e1.printStackTrace();
	                }

				}
			});
			
			//New York - Toronto flights
			b4.addActionListener(new ActionListener(){ 
				  public void actionPerformed(ActionEvent e){ 

					  ResultSet result;
					  String output="";
					  try {
						  result=st.executeQuery("SELECT flightcode, depcity, arrcity, depDateTime FROM flight WHERE "
						  		+ "(depcity='YYZ' and arrcity='JFK') or (depcity='JFK' and arrcity='YYZ') ORDER BY flightcode");
                          if(result.first()==false) {
                        	  JOptionPane.showMessageDialog(null,"No flights between New York and Toronto currently","Toronto/New York flights",JOptionPane.INFORMATION_MESSAGE);
                          }else {
                        	  do{
                        		  output+=result.getString("flightcode")+":  "+result.getString("depcity")+"-"+result.getString("arrcity")+"  "+result.getString("depDateTime")+"\n";	  
                        	  }while(result.next());
                        	  JOptionPane.showMessageDialog(null, output,"New York-Toronto Flights : \n",JOptionPane.INFORMATION_MESSAGE);
                          }
		                }catch(SQLException e1){
		                	e1.printStackTrace();
		                }	  
				  }
			 });
			

            }catch(SQLException s){
	          System.out.println("SQL statement is not executed!");
	        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
