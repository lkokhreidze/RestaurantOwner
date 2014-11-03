/**
 * @(#) Restourant.java
 */

package game;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import lombok.Data;

@Data
public class Restaurant {
	private short weekDay = 0;
	private boolean trainingResult;

	private java.util.List<Client> clients;

	private int budgetAmount = 10000;

	private Player player;

	private int reputation = 15;

	private int income;

	private String name;

	private String address;

	private String city;

	private Menu menu;

	private java.util.List<Waiter> waiters;

	private Barman barman;

	private Chef chef;

	private java.util.List<Order> order;
	private java.util.List<Table> tables;

	private String[] employeeNames = { "one", "two", "three", "four", "five" };

	private int priceForLowDish;

	private int priceForHighDish;

	private int priceForLowBeverage;

	private int priceForHighBeverage;

	public Restaurant() throws IOException {
		clients = new ArrayList<Client>();
		waiters = new ArrayList<Waiter>();
		tables = new ArrayList<Table>();
		menu = new Menu();
		this.startGame();
	}

	public boolean isSufficientBudget() {
		return budgetAmount >= 0;
	}

    private void chooseClientsAndMakeOrder(int tableNumber){
        Random rnd = new Random();
        Client clientOne;
        Client clientTwo;
        clientOne = clients.get(rnd.nextInt(clients.size()-1));
        clientTwo = clients.get(rnd.nextInt(clients.size()-1));
        if (!tables.get(tableNumber).isTableOccupied()) {
            clientOne = clients.get(rnd.nextInt(clients.size()-1));
            clientTwo = clients.get(rnd.nextInt(clients.size()-1));
            // Client one order
            clientOne.orders.add(new Order(menu.getDishes().get(
                    rnd.nextInt(menu.getDishes().size()-1)),menu.getBeverages().get(
                    rnd.nextInt(menu.getBeverages().size()-1))));
            clientOne.setBill(clientOne.orders.get(clientOne.orders.size()-1).calculateIncome());
            // Client two order
            clientTwo.orders.add(new Order(menu.getDishes().get(
                    rnd.nextInt(menu.getDishes().size()-1)),menu.getBeverages().get(
                    rnd.nextInt(menu.getBeverages().size()-1))));
            clientTwo.setBill(clientOne.orders.get(clientOne.orders.size()-1).calculateIncome());
            
            this.reputation += clientOne.orders.get(clientOne.orders.size()-1).calculateSatisfactory(tables.get(tableNumber).getWaiter());
            this.reputation += clientTwo.orders.get(clientOne.orders.size()-1).calculateSatisfactory(tables.get(tableNumber).getWaiter());
            tables.get(tableNumber).setIncome(
                    clientOne.getBill() + clientTwo.getBill());
            tables.get(tableNumber).setTableOccupied(true);
        }
    }
	public void occupyTables() {
		if (this.reputation >= 30) for (int i = 0; i < 5; i++) chooseClientsAndMakeOrder(i);
        else if (this.reputation >= 15) for (int i = 0; i < 5; i++) chooseClientsAndMakeOrder(i);
        else for (int i = 0; i < 2; i++) chooseClientsAndMakeOrder(i);
		endOfTheDay();
	}

	private void startGame() throws IOException {
		for (int i = 0; i < 18; i++) {
			clients.add(new Client());
		}
		for (int i = 0; i < 3; i++) {
			waiters.add(new Waiter(employeeNames[i]));
		}

		chef = new Chef(employeeNames[3]);
		barman = new Barman(employeeNames[4]);
		for (int i = 0; i < 9; i++) {
			tables.add(new Table(i));
		}

		// Assign table section
		for (Waiter waiter : waiters) {
			System.out.println("Assign table to waiter " + waiter.name);
			for (int i = 0; i < 3; i++) {
				System.out.print(" Enter table number [1-9]: ");
				int number = Integer.parseInt(ConsoleReader.readLine()) - 1;
				if (!tables.get(number).isTableAssigned() && number <= 9
						&& number >= 0) {
					waiter.getTables().add(tables.get(number));
					tables.get(number).setWaiter(waiter);
					tables.get(number).setTableAssigned(true);
					System.out.print("Table was successfully assigned.");
				} else {
					System.out.print("Incorrect table number!!!");
					i--;
				}
				System.out.println();
			}
		}

		// Set price section
		System.out.println("Enter price for low quality dish: ");
		priceForLowDish = Integer.parseInt(ConsoleReader.readLine());
		System.out.print("Enter price for high quality dish: ");
		priceForHighDish = Integer.parseInt(ConsoleReader.readLine());
		System.out.println("Enter price for low quality baverage: ");
		priceForLowBeverage = Integer.parseInt(ConsoleReader.readLine());
		System.out.println("Enter price for high quality baverage: ");
		priceForHighBeverage = Integer.parseInt(ConsoleReader.readLine());

		// Training section
		System.out
				.print("Would you like to set Dish and Baverage Quality?[Y/N]: ");
		if (ConsoleReader.readLine().toUpperCase().equals("Y")) {
			setDishBeveraQuality(true);
		} else {
			setDishBeveraQuality(false);
			System.out.print("Would you like to train Employee? [W|C|B|N]: ");
			trainEmployee(ConsoleReader.readLine());

		}

	}

	public void setDishBeveraQuality(boolean set) throws IOException {
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

	public void trainEmployee(String employee) throws IOException {
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
			break;
		case "B":
			trainingResult = barman.trainEmployee(budgetAmount);
			budgetAmount = trainingResult ? budgetAmount - 1200 : budgetAmount;
			if (trainingResult)
				System.out.println(MessageFormat.format(
						"Barmen {0} was trained, Experience Level = {1}",
						barman.getName(), barman.getExpLevel()));
			break;

		default:
			break;
		}
		this.occupyTables();
	}

	public void endOfTheDay() {
		
		if(weekDay==7){
			System.out.println("End of the week " + this.budgetAmount);
			System.out.println("Reputation" + this.reputation);
		}			
		else{
			for(Table table: tables){
				if(table.isTableOccupied())
                   this.budgetAmount+=table.getIncome();
			}
			this.weekDay += 1;
            occupyTables();
		}
//		
//				
		
	}

	public void calculatePayment() {

	}

	public int paySalary() {
		int waiterSalaries = 0;
		for(Waiter waiter : waiters) {
			waiterSalaries += waiter.getSalary();
		}
		int totalToPay = waiterSalaries + barman.getSalary() + chef.getSalary();
		return budgetAmount - totalToPay;
	}

	public int paySuppliers() {
		int lowQualityDishCounter = 0;
		int highQualityDishCounter = 0;
		for(Dish dish : menu.getDishes()) {
			if(dish.getQuality() == ItemQuality.LOW) {
				lowQualityDishCounter++;
			} else {
				highQualityDishCounter++;
			}
		}
		int dishSuppliersPrice = (lowQualityDishCounter * 3) + (highQualityDishCounter * 10);
		
		int lowQualityBeverageCounter = 0;
		int highQualityBeverageCounter = 0;
		
		for(Beverage beverage : menu.getBeverages()){
			if(beverage.getQuality() == ItemQuality.LOW) {
				lowQualityBeverageCounter++;
			} else {
				highQualityBeverageCounter++;
			}
		}
		int beverageSuppliersPrice = (lowQualityBeverageCounter * 3) + (highQualityBeverageCounter * 1);
		
		return budgetAmount - (dishSuppliersPrice + beverageSuppliersPrice);
	}

	public void gameOver() {

	}

	public int payAdditionalCosts() {
		return budgetAmount - 4000;
	}

	public boolean endOfTheWeek() {
		return this.weekDay == 6;
	}

	public void endOfTheMonth() {

	}

	public void provideStatistics() {

	}

	public void invalidQuality() {

	}

	public void invalidCharacter() {

	}

}
