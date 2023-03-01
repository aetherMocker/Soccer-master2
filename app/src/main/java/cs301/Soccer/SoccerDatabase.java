package cs301.Soccer;

import android.util.Log;

import cs301.Soccer.soccerPlayer.SoccerPlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private HashMap<String, SoccerPlayer> database = new HashMap<>();
    //key: string
    //value: soccerplayer

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {
        String name = firstName + "|" + lastName;
        if (database.get(name) != null) {
            return false;
        }
        else {
            SoccerPlayer player = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            database.put(name, player);
            return true;
        }
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String name = firstName + "|" + lastName;
        if (database.get(name) != null) {
            database.remove(name);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        String name = firstName + "|" + lastName;
        if (database.get(name) != null) {
            return database.get(name);
        }
        else {
            return null;
        }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        String name = firstName + "|" + lastName;
        if(database.get(name) != null) {
            database.get(name).bumpGoals();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        String name = firstName + "|" + lastName;
        if(database.get(name) != null) {
            database.get(name).bumpYellowCards();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        String name = firstName + "|" + lastName;
        if(database.get(name) != null) {
            database.get(name).bumpRedCards();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        if(teamName == null) {
            return database.size();
        }
        else {
            Iterator<SoccerPlayer> i = database.values().iterator();
            int num = 0;
            while (i.hasNext()) {
                SoccerPlayer player = i.next();
                if (player.getTeamName().equals(teamName)) {
                    num++;
                }
            }
            return num;
        }
    }
    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        Iterator<SoccerPlayer> i = database.values().iterator();
        int num = 0;
        while (i.hasNext()) {
            SoccerPlayer player = i.next();
            if (player.getTeamName().equals(teamName)) {
                if (num == idx) {
                    return player;
                }
                num++;
            }
        }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        FileReader inputStream = null;
        try {
            inputStream = new FileReader(file);
            BufferedReader bf = new BufferedReader(inputStream);
            String first;
            while ((first = bf.readLine()) != null) {
                String last = bf.readLine();
                String team = bf.readLine();
                int uniform = Integer.parseInt(bf.readLine());
                SoccerPlayer x = new SoccerPlayer(first, last, uniform, team);
                int goals = Integer.parseInt(bf.readLine());
                for (int i = 0; i < goals; ++i) {
                    x.bumpGoals();
                }
                int yellowCards = Integer.parseInt(bf.readLine());
                for (int i = 0; i < yellowCards; ++i) {
                    x.bumpYellowCards();
                }
                int redCards = Integer.parseInt(bf.readLine());
                for (int i = 0; i < redCards; ++i) {
                    x.bumpRedCards();
                }
                String name = x.getFirstName() + "|" + x.getLastName();
                database.put(name, x);
            }
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        FileWriter outputStream = null;
        try {
            outputStream = new FileWriter(file);
            PrintWriter pw = new PrintWriter(outputStream);

            Iterator<SoccerPlayer> i = database.values().iterator();
            while (i.hasNext()) {
                SoccerPlayer player = i.next();
                pw.println(logString(player.getFirstName()));
                pw.println(logString(player.getLastName()));
                pw.println(logString(player.getTeamName()));
                pw.println(player.getUniform());
                pw.println(player.getGoals());
                pw.println(player.getYellowCards());
                pw.println(player.getRedCards());
            }
            outputStream.close();
            return true;
        } catch (IOException e) {
           return false;
        }
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     *
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        HashSet<String> h = new HashSet<String>();
        Iterator<SoccerPlayer> i = database.values().iterator();
        while (i.hasNext()) {
            SoccerPlayer player = i.next();
            h.add(player.getTeamName());
            }
        return h;
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if (database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
