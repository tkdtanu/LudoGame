package com.company;


import java.util.Random;

public class LudoGame {
    public static void main(String[] args) throws InterruptedException {
        PlayerDetail player=new PlayerDetail(4,1);
        Random random = new Random();
        Thread player1 = new LudoPlayer("Player1", 1,player, random);
        Thread player2 = new LudoPlayer("Player2", 2,player, random);
        Thread player3 = new LudoPlayer("Player3", 3,player, random);
        Thread player4 = new LudoPlayer("Player4", 4,player, random);
        player1.start();
        player2.start();
        player3.start();
        player4.start();

    }
}
class LudoPlayer extends Thread{
    private final PlayerDetail playerDetail;
    private final int playerId;
    private final Random random;
    public LudoPlayer(String name,int playerId, PlayerDetail playerDetail, Random random) {
        super(name);
        this.playerDetail = playerDetail;
        this.playerId = playerId;
        this.random = random;
    }

    @Override
    public void run() {
            try {
                synchronized (this.playerDetail) {
                    for (int i = 0; i < 100; i++) {
                        while (this.playerId != this.playerDetail.getCurrentPlayerId()) {
                            this.playerDetail.wait();
                        }
                        System.out.println("Player :" + this.getName() + " Term");
                        Thread.sleep(500);
                        System.out.println("Rolling Dice.... And Here it came");
                        Thread.sleep(500);
                        int number = random.nextInt(7);
                        System.out.println(number);
                        if(number != 6) {

                            if (this.playerDetail.getNoOfPlayer() == this.playerId) {
                                this.playerDetail.setCurrentPlayerId(1);
                            } else {
                                this.playerDetail.increaseId();
                            }
                            Thread.sleep(500);
                            this.playerDetail.notifyAll();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
class PlayerDetail {
    private int noOfPlayer;
    private volatile int currentPlayerId;

    public PlayerDetail(int noOfPlayer, int currentPlayerId) {
        this.noOfPlayer = noOfPlayer;
        this.currentPlayerId = currentPlayerId;
    }

    public int getNoOfPlayer() {
        return noOfPlayer;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
    public void increaseId(){
        this.currentPlayerId++;
    }
}
