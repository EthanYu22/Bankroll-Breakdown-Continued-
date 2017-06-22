package com.example.ethan.pokerjournal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StatsFragment extends Fragment {

    DatabaseHelper db;
    List<Game> gameList;

    public StatsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        db = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate Layout
        return inflater.inflate(R.layout.stats, container, false);
    }

    // Action When On Stats Page
    public void onResume() {
        super.onResume();
        displayStats();
    }

    // Displays Overall Game Statistics
    public void displayStats() {
        Game game;
        gameList = db.getAllGames();
        double totalHours = 0;
        double avgHours;
        int netProfit;
        int buyIn = 0;
        int cashOut = 0;
        int avgBuy = 0;
        double hourlyRate;
        int winningSession = 0;
        int losingSession = 0;
        int totalSessions = 0;
        double biggestWin = 0;
        double biggestLoss = 0;
        // Calculation for Statistics
        for (int i = 0; i < gameList.size(); i++) {
            game = gameList.get(i);
            totalHours += game.getTime(); // Net Hours
            buyIn += game.getBuyIn(); // Net Buy In
            cashOut += game.getCashOut(); // Net Cash Out
            avgBuy = buyIn / gameList.size(); // Average Buy In

            // Find Biggest Win and Loss
            double bigwin = game.getCashOut() - game.getBuyIn();
            if(bigwin > biggestWin){
                biggestWin = bigwin;
            } else if(bigwin < biggestLoss){
                biggestLoss = bigwin;
            }

            // Tally Number of Winning and Losing Sessions
            if(game.getCashOut() - game.getBuyIn() > 0){
                winningSession++;
            }else if(game.getBuyIn() - game.getCashOut() > 0){
                losingSession++;
            }

            // Get Total Sessions
            totalSessions = winningSession + losingSession;
            if(game.getCashOut() - game.getBuyIn() == 0){
                totalSessions++;
            }
        }

        // Displays Game Statistics
        TextView x = (TextView) getView().findViewById(R.id.statText); // Total Hours
        TextView ast = (TextView) getView().findViewById(R.id.avgHours); // Average Session Time
        TextView np = (TextView) getView().findViewById(R.id.netProfit); // Net Profit
        TextView hr = (TextView) getView().findViewById(R.id.hourlyRate); // Hourly Rate
        TextView ws = (TextView) getView().findViewById(R.id.winningSession); // Winning Sessions
        TextView ls = (TextView) getView().findViewById(R.id.lossingSession); // Losing Sessions
        TextView ts = (TextView) getView().findViewById(R.id.totalSession); // Total Sessions
        TextView abi = (TextView) getView().findViewById(R.id.avgBuy); // Average Buy In
        TextView bw = (TextView) getView().findViewById(R.id.biggestWin); // Biggest Win
        TextView bl = (TextView) getView().findViewById(R.id.biggestLoss); // Biggest Loss

        if (gameList.size() == 0) { // If No Games Are Played Display This
            x.setText("Total Hours: ");
            ast.setText("Avg Session Time: ");
            np.setText("Net Profit: ");
            hr.setText("Hourly Rate: ");
            ws.setText("Winning Sessions: ");
            ls.setText("Losing Sessions: ");
            ts.setText("Total Sessions: ");
            abi.setText("Avg Buy In: ");
            bw.setText("Biggest Win: ");
            bl.setText("Biggest Loss: ");
        } else { // If Games Are Played Display This
            avgHours = totalHours / gameList.size();
            String avgH = String.format("%.2f", avgHours);
            netProfit = cashOut - buyIn;
            hourlyRate = netProfit / totalHours;
            String hourlyR = String.format("%.2f", hourlyRate);
            x.setText("Total Hours: " + totalHours);
            ast.setText("Avg Session Time: " + avgH);
            np.setText("Net Profit: $" + netProfit);
            hr.setText("Hourly Rate: $" + hourlyR);
            ws.setText("Winning Sessions: " + winningSession);
            ls.setText("Losing Sessions: " + losingSession);
            ts.setText("Total Sessions: " + totalSessions);
            abi.setText("Avg Buy In: $" + avgBuy);
            bw.setText("Biggest Win: $" + biggestWin);
            bl.setText("Biggest Loss: $" + biggestLoss);
        }
        
    }
}
