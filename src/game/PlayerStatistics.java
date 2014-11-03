/**
* @(#) PlayerStatistics.java

*/

package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import lombok.Data;

@Data
public class PlayerStatistics
{
	private int numberOfPlayers;
	private int playerRanks;
	private Player player;
	String playerName;
	Integer budget;
	String filePath;
	
	File statFile;
	
	public PlayerStatistics(String name, String path, Integer b){
		this.playerName = name;
		this.filePath = path;
		this.statFile = new File(filePath);
		this.budget = b;
	}
	
	public void createStatistics() throws IOException {
		ArrayList<RankingList> playersList = new ArrayList<RankingList>();
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
			for(RankingList player : playersList) {
				i++;
				player.Rank = i;
			}
		} catch(IOException e) {
			System.out.println("Error Creating Statistics");
		}
	}
	
	public ArrayList<RankingList> getRankingList() throws IOException {
		Scanner read = new Scanner (statFile);
		ArrayList<RankingList> rankingList = new ArrayList<RankingList>();
		read.useDelimiter("\\s*,\\s*");
		while (read.hasNext()) {
			RankingList rankLst = new RankingList();
			rankLst.setPlayerName(read.next());
			rankLst.setBudget(Integer.parseInt(read.next()));
			rankingList.add(rankLst);
		}
		read.close();
		return rankingList;
	}
	
	public class RankComparator implements Comparator<RankingList> {
		@Override
		public int compare(RankingList o1, RankingList o2) {
			return o2.getBudget().compareTo(o1.getBudget());
		}
	}
	
	public void playerExists() {
	
	}
}