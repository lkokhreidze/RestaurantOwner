/**
* @(#) PlayerStatistics.java

*/

package game;

import java.io.PrintWriter;
import java.io.FileWriter;
import lombok.Data;
import java.util.Scanner;
import java.io.IOException;
import java.util.Comparator;
import java.util.Collections;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * @(#) PlayerStatistics.java
 */
@Data
public class PlayerStatistics
{
	private int numberOfPlayers;
	private int playerRanks;
	String playerName;
	Integer budget;
	String filePath;
	
	File statFile;
	
	public PlayerStatistics( String name, String path, Integer b ){
		this.playerName = name;
		this.filePath = path;
		this.budget = b;
	}
	
	public void createStatistics( ) throws IOException {
		this.statFile = new File(filePath);
		ArrayList<PlayerRanking> playersList = new ArrayList<PlayerRanking>();
		try {
			if(statFile.exists() == false){
				System.out.println("We had to make a new file.");
				statFile.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(statFile, true));
			out.append(playerName + "," + budget + "," + System.getProperty("line.separator"));
			out.close();
			playersList = getRankingList();
			Collections.sort(playersList, new RankComparator());
			int i = 0;
			for(PlayerRanking player : playersList) {
				i++;
				player.Rank = i;
				System.out.println(MessageFormat.format("{0} : {1} - {2}", player.getRank(), player.getPlayerName(),player.getBudget()));
			}
		} catch(IOException e) {
			System.out.println("Error Creating Statistics");
		}
	}
	
	public ArrayList<PlayerRanking> getRankingList( ) throws IOException {
		Scanner read = new Scanner (statFile);
		ArrayList<PlayerRanking> playerRanking = new ArrayList<PlayerRanking>();
		read.useDelimiter("\\s*,\\s*");
		while (read.hasNext()) {
			PlayerRanking rankLst = new PlayerRanking();
			rankLst.setPlayerName(read.next());
			rankLst.setBudget(Integer.parseInt(read.next()));
			playerRanking.add(rankLst);
		}
		read.close();
		return playerRanking;
	}
	
	public class RankComparator implements Comparator<PlayerRanking> {
		@Override
		public int compare( PlayerRanking o1, PlayerRanking o2 ) {
			return o2.getBudget().compareTo(o1.getBudget());
		}
	}
	
}