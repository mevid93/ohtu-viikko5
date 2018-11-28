package ohtu;

public class TennisGame {

    private int player1Points = 0;          // total points for player 1
    private int player2Points = 0;          // total points for player 2
    private final String player1Name;       // name of player 1
    private final String player2Name;       // name of player 2
    private final int ENDGAME_POINTS = 4;   // pisteraja, joka määrittää "endgame"-tilanteen 

    /**
     * Konstruktori, joka luo uuden TennisGame-olion. Konstruktori vastaanottaa
     * parametreinä pelaajien nimet.
     *
     * @param player1Name pelaajan 1 nimi
     * @param player2Name pelaajan 2 nimi
     */
    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    /**
     * Kasvattaa parametrina annetun pelaajan pistemäärää yhdellä.
     *
     * @param playerName pelaajan nimi
     */
    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            player1Points++;
        } else if (playerName.equals(player2Name)) {
            player2Points++;
        }
    }

    /**
     * Kertoo voimassa olevan tilanteen tennispisteenlaskennan määrittelemän
     * tavan mukaan.
     *
     * @return pistetilanne
     */
    public String getScore() {
        // tarkista onko tilanne tasapeli
        if (isTie()) {
            // tilanne on tasan, joten palautetaan sen mukainen tulos
            return getTiePoints();
        } // tarkista onko kyseessä "end game" eli toisella pelaajalla vähintään 4 pistettä
        if (isEndGame()) {
            // "endgame" tilanne, joten palautetaan sen mukainen tulos
            return getEndGamePoints();
        }
        // normaali pelitilanne, joten palautetaan sen mukainen tulos
        return getNormalGamePoints();
    }

    /**
     * Palauttaa tiedon siitä onko pistetilanne tasan.
     *
     * @return true jos pistetilanne on tasan
     */
    private boolean isTie() {
        return player1Points == player2Points;
    }

    /**
     * Palauttaa pistetilanteen tennispisteenlaskennan määrittelemän tavan
     * mukaan, kun tilanne on tasan.
     *
     * @return pistetilanne
     */
    private String getTiePoints() {
        switch (player1Points) {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            case 3:
                return "Forty-All";
            default:
                return "Deuce";
        }
    }

    /**
     * Palauttaa tiedon siitä onko pistetilanne endgame-tilanteessa, eli
     * vähintään toisella pelaajalla on vähintään 4 pistettä.
     *
     * @return true jos endgame
     */
    private boolean isEndGame() {
        return (player1Points >= ENDGAME_POINTS || player2Points >= ENDGAME_POINTS);
    }

    /**
     * Palauttaa pistetilanteen tennispisteenlaskennan määrittelemän tavan
     * mukaan, kun kyseessä on endgame, eli kun vähintään toisella pelaajalla on
     * yli 4 pistettä.
     *
     * @return
     */
    private String getEndGamePoints() {
        int minusResult = player1Points - player2Points;
        if (minusResult == 1) {
            return "Advantage player1";
        } else if (minusResult == -1) {
            return "Advantage player2";
        } else if (minusResult >= 2) {
            return "Win for player1";
        } else {
            return "Win for player2";
        }
    }

    /**
     * Palauttaa normaalin pelitilanteen tennispisteenlaskennan määrittelemän
     * tavan mukaisen tuloksen. Normaalilla pelitilanteella tarkoitetaan tässä
     * tapauksessa tilannetta, jossa kummallakin pelaajalla on alle 4 pistettä.
     *
     * @return tulos
     */
    private String getNormalGamePoints() {
        return getScore(player1Points) + "-" + getScore(player2Points);
    }

    /**
     * Palauttaa tennispisteenlaskennan määrittelemän tuloksen parametrina
     * annetulle pistemäärälle.
     *
     * @return tulos
     */
    private String getScore(int pisteet) {
        switch (pisteet) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
        }
        return null;
    }
}
