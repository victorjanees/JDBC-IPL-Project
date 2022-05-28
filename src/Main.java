import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerYear(matches);
        findMatchesWonByEachTeamOverYears(matches);
        findExtraRunsConcededPerTeamIn2016(matches, deliveries);
//        findEconomicalBowlerOf2015(matches, deliveries);
//        findTotalSixesByTeams(deliveries);
    }

    public static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();

        try {
            String url = "jdbc:mysql://localhost:3306/IPL";
            String username = "root";
            String password = "Dhoni@007";
            String query = "select * from Matches";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
//            String matchData = rs.getString(2);
                Match match = new Match();

                int matchId = Integer.parseInt(rs.getString(1));
                int season = Integer.parseInt(rs.getString(2));

                match.setMatchId(matchId);
                match.setYear(season);
                match.setWinner(rs.getString(11));
                matches.add(match);

//            st.close();
//            con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    public static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList<>();

        try {
            String url = "jdbc:mysql://localhost:3306/IPL";
            String username = "root";
            String password = "Dhoni@007";
            String query = "select * from Deliveries";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Delivery delivery = new Delivery();

                int matchId = Integer.parseInt(rs.getString(1));
                int extraRuns = Integer.parseInt(rs.getString(17));
                int totalRuns = Integer.parseInt(rs.getString(18));
                int batsmanRuns = Integer.parseInt(rs.getString(16));

                delivery.setExtraRuns(extraRuns);
                delivery.setTotalRuns(totalRuns);
                delivery.setMatchId(matchId);
                delivery.setBowlingTeam(rs.getString(4));
                delivery.setBowler(rs.getString(9));
                delivery.setBatsmanRuns(batsmanRuns);
                delivery.setBattingTeam(rs.getString(3));
                deliveries.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    public static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        Map<Integer, Integer> matchesPerSeason = new HashMap<Integer, Integer>();
        Iterator<Match> totalMatches = matches.iterator();
        while (totalMatches.hasNext()) {
            int year = totalMatches.next().getYear();
            if (matchesPerSeason.containsKey(year)) {
                matchesPerSeason.put(year, matchesPerSeason.get(year) + 1);
            } else {
                matchesPerSeason.put(year, 1);
            }
        }
        System.out.println("Number Of Matches Played Per Season");
        System.out.println(matchesPerSeason);

    }

    public static void findMatchesWonByEachTeamOverYears(List<Match> matches) {
        Map<String, Integer> matchesWonByTeam = new HashMap<String, Integer>();
        Iterator<Match> totalMatches = matches.iterator();
        while (totalMatches.hasNext()) {
            String wins = totalMatches.next().getWinner();
            if (matchesWonByTeam.containsKey(wins)) {
                matchesWonByTeam.put(wins, matchesWonByTeam.get(wins) + 1);
            } else {
                matchesWonByTeam.put(wins, 1);
            }
        }
        System.out.println("Matches won by teams over years");
        System.out.println(matchesWonByTeam);
    }

    public static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries) {
        HashMap<String, Integer> extraRuns = new HashMap<String, Integer>();
        Iterator<Match> matchIterator = matches.iterator();
        while (matchIterator.hasNext()) {
            Match match = matchIterator.next();
            if (match.getYear() == 2016) {
                Iterator<Delivery> deliveryIterator = deliveries.iterator();
                while (deliveryIterator.hasNext()) {
                    Delivery delivery = deliveryIterator.next();
                    if (delivery.getMatchId() == match.getMatchId()) {
                        if (extraRuns.containsKey(delivery.getBowlingTeam())) {
                            extraRuns.put(delivery.getBowlingTeam(), extraRuns.get(delivery.getBowlingTeam()) + delivery.getExtraRuns());
                        } else extraRuns.put(delivery.getBowlingTeam(), delivery.getExtraRuns());

                    }
                }
            }
        }
        System.out.println("Extra runs conceded in 2016");
        System.out.println(extraRuns);
    }
}
