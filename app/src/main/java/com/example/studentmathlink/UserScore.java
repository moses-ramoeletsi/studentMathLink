package com.example.studentmathlink;

public class UserScore {
    private String userId;
    private String userName;
    private long score;
    private long timeTaken;
    private int position;
    private boolean isCurrentUser;

    public UserScore(String userId, String name, long score, long timeTaken, int position) {
        this.userId = userId;
        this.userName = name;
        this.score = score;
        this.timeTaken = timeTaken;
        this.position = position;
        this.isCurrentUser = false;
    }


    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public long getScore() { return score; }
    public long getTimeTaken() { return timeTaken; }
    public int getPosition() { return position; }
    public boolean isCurrentUser() { return isCurrentUser; }
    public void setCurrentUser(boolean currentUser) { isCurrentUser = currentUser; }
}