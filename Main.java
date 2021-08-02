package Mini_Project2;

import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Scanner scn=new Scanner(System.in);

		Passenger p=new Passenger();
		//Display:

		System.out.println("\n\n----------------------WELCOME---------------------------\n");
		int ch1;
		int ch2=0;

		do {
			System.out.println("DO YOU WANT TO : ");
			System.out.println("0. EXIT\n" + "1. LOGIN AS ADMIN\n" + "2. LOGIN AS DRIVER\n" + "3. BOOK A CAB\n");
			System.out.println("--------------------------------------------------------");

			System.out.print("Enter your choice: "); 
			ch1=scn.nextInt();
			System.out.println();

			switch(ch1)
			{
			case 1:
				//*************************** Admin ***********************************
				int password;
				System.out.print("Enter password :");
				password=scn.nextInt();
				System.out.println();

				if(password==1234)
				{

					System.out.println("-------------------WELCOME ADMIN------------------------\n");
					do
					{
						System.out.println("DO YOU WANT TO : ");
						System.out.println("0. EXIT\n" 
						                 + "1. FIND PROFIT\n" + "2. FIND TOTAL REVENUE\n" + "3. FIND TOTAL EXPENDITURE\n" 
								         + "4. FIND TOTAL EXPENDITURE ON DAILY WAGES\n" + "5. FIND LOCATION GENERATING MAXIMUM REVENUE");
						System.out.println("6. UPDATE FULE PRICES\n" + "7. CHANGE WAGE PER KM RATE\n");
						System.out.println("--------------------------------------------------------\n");

						System.out.println("What is your choice ?");
						ch2=scn.nextInt();
						System.out.println();

						switch(ch2)
						{
						case 1:p.profit();
						break;

						case 2:p.totalRevenue();
						break;

						case 3:p.totalExpFuel();
						break;

						case 4:p.expOnDailyWage();
						break;

						case 5:p.expAtLocation();
						break;

						case 6:p.UpdateFuelPrice();
						break;

						case 7:p.changeWageRate();
						break;

						case 0:
							System.out.println("**ADMIN EXIT COMPLETED !!");
							System.out.println("---------------------------------------------------------");
							break;

						default:System.out.println("INVALID CHOICE !!");break;
						}

					}while(ch2!=0);  
				}//if-end
				else
				{
					System.out.println(" PASSWORD MISMATCH !!");
				}
				break;

			case 2:
				//*************************** driver ***********************************

				System.out.println("-------------------WELCOME DRIVER-----------------------\n");

				
				do
				{
					System.out.println("DO YOU WANT TO : ");
					System.out.print("0. EXIT\n" + "1. DRIVER REGISTRATION\n" + "2. KNOW YOUR DAILY WAGE\n" +"3. SHOW PATH\n");
					System.out.println("--------------------------------------------------------\n");

					System.out.println("What is your choice ?");
					ch2=scn.nextInt();
					System.out.println();

					switch(ch2)
					{
					case 1:p.addDriver();
					break;

					case 2:p.dailyWage();
					break;
					
//					case 3:p.show_Path();
//					break;

					case 0:
						System.out.println("**DRIVER EXIT COMPLETED !!");
						System.out.println("--------------------------------------------------------");
						break;

					default:System.out.println("INVALID CHOICE !!");
					break;
					}

				}while(ch2!=0);
				break;

			case 3:
				System.out.println("--------------------WELCOME USER------------------------\n");

				do
				{
					System.out.println("DO YOU WANT TO : ");
					System.out.print("0. EXIT\n" + "1. BOOK RIDE\n");
					System.out.println("--------------------------------------------------------\n");

					System.out.println("What is your choice ?");
					ch2=scn.nextInt();
					System.out.println();

					switch(ch2)
					{
					case 1:
					{
						p.signUP(); 
						p.bookRide();
						p.makePayment();
					}
					break;

					case 0:
						System.out.println("**PASSENGER EXIT COMPLETED !!");
						System.out.println("---------------------------------------------------------");
						break;

					default:
						System.out.println("INVALID CHOICE !!");
						break;
					}

				}while(ch2!=0);
				break;

			case 0:
				System.out.println("***TERMINATED***");
				System.out.println("--------------------------------------------------------");
				break;

			default:System.out.println("INVALID CHOICE !!");
			break;

			}//outer-switch

		}while(ch1!=0);

		//scn.close();
	}//class-close
}
