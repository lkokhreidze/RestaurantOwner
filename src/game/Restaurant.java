/**
 * @(#) Restourant.java
 */

package game;

import java.util.List;
import lombok.Data;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.text.MessageFormat;

/**
 * @(#) Restaurant.java
 */
@Data
public class Restaurant {
	private short weekDay = 0;
	
	private short weekCount = 1;
	
	private boolean trainingResult;

	private List<Client> clients;

	private int budgetAmount = 10000;

	private int reputation = 15;

	private String ownerName;

	private String name;

	private String address;

	private String city;

	private Menu menu;

	private java.util.List<Waiter> waiters;

	private Barman barman;

	private Chef chef;

	private List<Table> tables;

	private String[] employeeNames = {"Sauron","Voldemort","Chuck Norris","Silvester Stalone","John Snow (Who knows nothing)"};

	private String[] clientNames = {"Olivia Morris","Leslie Richardson","Lindsay Guzman","Teresa Manning","Lois Becker","Ethel Fitzgerald","Lonnie Warren","Hattie Massey","Ken Mcdaniel","Jessie Bryant","Israel Brock","Mary Thompson","Duane Jefferson","Alexis Abbott","Emily Graham","Julia Marsh","Amanda Stokes","Jody Simon"};

	private int priceForLowDish;

	private int priceForHighDish;

	private int priceForLowBeverage;

	private int priceForHighBeverage;

	private String path;

	
	private Waiter waiter;
	
	public Restaurant( String name, String p ) throws IOException {
		this.ownerName = name;
		clients = new ArrayList<Client>();
		waiters = new ArrayList<Waiter>();
		tables = new ArrayList<Table>();
		chef = new Chef(employeeNames[3]);
		barman = new Barman(employeeNames[4]);
		menu = new Menu(chef, barman);
		this.path = p;
		this.startGame();
	}

	public void isSufficientBudget( ) throws IOException {
		if (budgetAmount <= 0) {
			this.gameOver();
		}
	}

	private void chooseClientsAndMakeOrder( int tableNumber ) {
		Random rnd = new Random();
		Client clientOne;
		Client clientTwo;
		clientOne = clients.get(rnd.nextInt(clients.size() - 1));
		clientTwo = clients.get(rnd.nextInt(clients.size() - 1));
		if (!tables.get(tableNumber).isTableOccupied()) {
			clientOne = clients.get(rnd.nextInt(clients.size() - 1));
			clientTwo = clients.get(rnd.nextInt(clients.size() - 1));
			// Client one order
			clientOne.orders.add(new Order(menu.getDishes().get(
					rnd.nextInt(menu.getDishes().size() - 1)), menu
					.getBeverages().get(
							rnd.nextInt(menu.getBeverages().size() - 1))));
			clientOne.setBill(clientOne.orders.get(clientOne.orders.size() - 1)
					.calculateIncome());
			// Client two order
			clientTwo.orders.add(new Order(menu.getDishes().get(
					rnd.nextInt(menu.getDishes().size() - 1)), menu
					.getBeverages().get(
							rnd.nextInt(menu.getBeverages().size() - 1))));
			clientTwo.setBill(clientTwo.orders.get(clientTwo.orders.size() - 1)
					.calculateIncome());
			if (this.reputation < 100 && this.reputation >= 0) {
				this.reputation += clientOne.orders.get(
						clientOne.orders.size() - 1).calculateSatisfactory(
						tables.get(tableNumber).getWaiter());
				this.reputation += clientTwo.orders.get(
						clientTwo.orders.size() - 1).calculateSatisfactory(
						tables.get(tableNumber).getWaiter());
			} else {
				this.reputation = this.reputation < 0 ? 0 : 100;
			}
			tables.get(tableNumber).setIncome(
					clientOne.getBill() + clientTwo.getBill());
			tables.get(tableNumber).setTableOccupied(true);
		}
	}

	public void occupyTables( ) throws IOException {
		if (this.reputation >= 30)
			for (int i = 0; i < 9; i++)
				chooseClientsAndMakeOrder(i);
		else if (this.reputation >= 15)
			for (int i = 0; i < 5; i++)
				chooseClientsAndMakeOrder(i);
		else
			for (int i = 0; i < 2; i++)
				chooseClientsAndMakeOrder(i);
		endOfTheDay();
	}

	private void startGame( ) throws IOException {
		for (int i = 0; i < 18; i++) {
			clients.add(new Client(clientNames[i]));
		}
		for (int i = 0; i < 3; i++) {
			waiters.add(new Waiter(employeeNames[i]));
		}

		for (int i = 0; i < 9; i++) {
			tables.add(new Table(i));
		}

		// Set price section
		System.out.print("Enter price for low quality dish: ");
		priceForLowDish = Integer.parseInt(ConsoleReader.readLine());
		System.out.print("Enter price for high quality dish: ");
		priceForHighDish = Integer.parseInt(ConsoleReader.readLine());
		System.out.print("Enter price for low quality baverage: ");
		priceForLowBeverage = Integer.parseInt(ConsoleReader.readLine());
		System.out.print("Enter price for high quality baverage: ");
		priceForHighBeverage = Integer.parseInt(ConsoleReader.readLine());
		setDishBeveraQuality();
		trainEmployee();
		this.assignTable();

	}

	private void assignTable( ) throws IOException {
		for (Waiter waiter : waiters) {
			System.out.println("Assign table to waiter " + waiter.name);
			for (int i = 0; i < 3; i++) {
				System.out.print(" Enter table number [1-9]: ");
				String input = ConsoleReader.readLine();
				if (invalidNumericCharacter(input)) {
					int number = Integer.parseInt(input) - 1;
					if (!tables.get(number).isTableAssigned()) {
						waiter.getTables().add(tables.get(number));
						tables.get(number).setWaiter(waiter);
						tables.get(number).setTableAssigned(true);
						System.out.print("Table was successfully assigned.");
					} else {
						System.out.print("Incorrect table number!!!");
						i--;
					}
				} else {
					System.out.print("Incorrect character!!!");
					i--;
				}
				System.out.println();
			}
		}
		this.occupyTables();
	}

	public void setDishBeveraQuality( ) throws IOException {
		System.out
				.print("Would you like to set Dish and Baverage Quality?[Y/N]: ");
		boolean set = ConsoleReader.readLine().toUpperCase().equals("Y");
		if (set) {
			for (Dish dish : menu.getDishes()) {
				System.out.print("Set dish Quality [Low - High]: ");
				ItemQuality quality = ItemQuality.valueOf(ConsoleReader
						.readLine().toUpperCase());
				dish.setQuality(quality);
				dish.setPrice(quality == ItemQuality.HIGH ? priceForHighDish
						: priceForLowDish);
				System.out.println("Dish " + dish.name + " : "
						+ dish.getQuality());
			}
			for (Beverage beverage : menu.getBeverages()) {
				System.out.print("Set baverage Quality [Low - High]: ");
				ItemQuality quality = ItemQuality.valueOf(ConsoleReader
						.readLine().toUpperCase());
				beverage.setQuality(quality);
				beverage.setPrice(quality == ItemQuality.HIGH ? priceForHighDish
						: priceForLowDish);
				System.out.println("Dish " + beverage.name + " : "
						+ beverage.getQuality());
			}
		} else {
			for (int i = 0; i < 5; i++) {
				menu.getDishes().get(i).setPrice(priceForLowDish);
				menu.getBeverages().get(i).setPrice(priceForLowDish);
			}
		}
	}

	public void trainEmployee( ) throws IOException {
		System.out.print("Which Employee to train? [W|C|B|N]: ");
		String employee = ConsoleReader.readLine();
		switch (employee.toUpperCase()) {
		case "W":
			for (Waiter waiter : waiters) {
				System.out.print("Would you like to train waiter "
						+ waiter.getName() + " ?[Y/N]: ");
				if (ConsoleReader.readLine().toUpperCase().equals("Y")) {
					trainingResult = waiter.trainEmployee(budgetAmount);
					budgetAmount = trainingResult ? budgetAmount - 800
							: budgetAmount;
					if (trainingResult)
						System.out
								.println(MessageFormat
										.format("Waiter {0} was trained, Experience Level = {1}",
												waiter.getName(),
												waiter.getExpLevel()));
					else {
						System.out.println("Insufficient budget");
						isSufficientBudget();
					}
				}
			}
			break;
		case "C":
			trainingResult = chef.trainEmployee(budgetAmount);
			budgetAmount = trainingResult ? budgetAmount - 1200 : budgetAmount;
			if (trainingResult)
				System.out.println(MessageFormat.format(
						"Chef {0} was trained, Experience Level = {1}",
						chef.getName(), chef.getExpLevel()));
			else {
				System.out.println("Insufficient budget");
				isSufficientBudget();
			}
			break;
		case "B":
			trainingResult = barman.trainEmployee(budgetAmount);
			budgetAmount = trainingResult ? budgetAmount - 1200 : budgetAmount;
			if (trainingResult)
				System.out.println(MessageFormat.format(
						"Barmen {0} was trained, Experience Level = {1}",
						barman.getName(), barman.getExpLevel()));
			else {
				System.out.println("Insufficient budget");
				isSufficientBudget();
			}
			break;

		default:
			return;
		}
		trainEmployee();
	}

	public void endOfTheDay( ) throws IOException {
		this.weekDay += 1;
		if (this.weekDay == 7) {
			for (Table table : tables)
				table.setTableAssigned(false);
			endOfTheWeek();
		} else {
			for (Table table : tables) {
				if (table.isTableOccupied()) {
					this.budgetAmount += table.getIncome();
					table.setTableOccupied(false);
				}
				table.setTableAssigned(false);
			}
			provideGameInfo();
			assignTable();
		}
	}


	public void paySalary( ) throws IOException {
		int waiterSalaries = 0;
		for (Waiter waiter : waiters) {
			waiterSalaries += waiter.getSalary();
		}
		int totalToPay = waiterSalaries + barman.getSalary() + chef.getSalary();
		budgetAmount -= totalToPay;
		isSufficientBudget();
	}

	public void paySuppliers( ) throws IOException {
		int lowQualityDishCounter = 0;
		int highQualityDishCounter = 0;
		for (Dish dish : menu.getDishes()) {
			if (dish.getQuality() == ItemQuality.LOW) {
				lowQualityDishCounter++;
			} else {
				highQualityDishCounter++;
			}
		}
		int dishSuppliersPrice = (lowQualityDishCounter * 3)
				+ (highQualityDishCounter * 10);

		int lowQualityBeverageCounter = 0;
		int highQualityBeverageCounter = 0;

		for (Beverage beverage : menu.getBeverages()) {
			if (beverage.getQuality() == ItemQuality.LOW) {
				lowQualityBeverageCounter++;
			} else {
				highQualityBeverageCounter++;
			}
		}
		int beverageSuppliersPrice = (lowQualityBeverageCounter)
				+ (highQualityBeverageCounter * 3);

		this.budgetAmount -= (dishSuppliersPrice + beverageSuppliersPrice);
		isSufficientBudget();
	}

	public void gameOver( ) throws IOException {
		payAdditionalCosts();
		provideGameInfo();
		System.out.println("!!!===============GAME IS OVER===============!!!");
		System.out.println("Your budget  " + this.budgetAmount);
		System.out.println("Your reputation  " + this.reputation);
		System.out.println("!!!===============CURRENT RANKING LIST===============!!!");
		new PlayerStatistics(ownerName, path, budgetAmount).createStatistics();
	}

	public void payAdditionalCosts( ) {
		budgetAmount= budgetAmount - 4000;
	}

	public void endOfTheWeek( ) throws IOException {
		if (weekCount == 4) {
			this.gameOver();
		} else {
			this.trainEmployee();
			this.paySuppliers();
			this.paySalary();
			this.weekDay = 0;
			this.weekCount += 1;
			assignTable();
		}
	}


	public void provideGameInfo( ) {
		System.out.println(MessageFormat.format(
				"==================\n\rTotal budget for day {0} - week {1} is {2}\n\r==================", this.weekDay,this.weekCount,
				this.budgetAmount));
		System.out.println(MessageFormat.format(
				"Restaurant reputation for day {0} - week {1} is {2}\n\r==================", this.weekDay,this.weekCount,
				this.reputation));
		for (Client client : clients) {
			if (client.totalSpending() != 0) {
				System.out
						.println(MessageFormat
								.format("{0} visited the restaurant {1} times, and spent {2} amount",
										client.getName(),client.getOrders().size(),
										client.totalSpending()));
				int[] dishCount = client.getOrderedDishes();
				int[] beverageCount = client.getOrderedBeverages();
				for (int i = 0; i < 5; i++) {
					if (dishCount[i] != 0)
						System.out.println(MessageFormat.format(
								"{0} ordered {1} {2} times",
								client.getName(), menu.getDishes().get(i)
										.getName(), dishCount[i]));
					if (beverageCount[i] != 0)
						System.out
								.println(MessageFormat.format(
										"{0} ordered {1} {2} times",
										client.getName(), menu
												.getBeverages().get(i)
												.getName(),
										beverageCount[i]));

				}
				
				System.out
				.println(MessageFormat
						.format("{0} average consumed volume {1} ",
								client.getName(),client.getAverageVolumeConsumed()));
				System.out
				.println(MessageFormat
						.format("{0} average consumed calories {1} \n----------",
								client.getName(),client.getAverageCaloriesConsumed()));
			}
		}

	}

	public boolean invalidNumericCharacter( String text ) {
		int number;
		try {
			number = Integer.parseInt(text);
			if (number > 9 || number <= 0)
				return false;
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
