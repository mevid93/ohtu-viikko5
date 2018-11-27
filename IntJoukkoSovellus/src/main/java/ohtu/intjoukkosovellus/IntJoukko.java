package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5;    // aloitustalukon koko
    public final static int OLETUSKASVATUS = 5;  // vakio taulukon kapasiteetin kasvatus
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] alkiot;         // Joukon luvut säilytetään taulukossa, jonka täyttö alkaa alkupäästä
    private int alkioidenLkm;     // joukossa esiintyvien alkioiden lukumäärä.

    /**
     * Konstruktori ilman parametria. Asettaa joukon taulukon kooksi oletus
     * kapasiteetin.
     */
    public IntJoukko() {
        alkiot = new int[KAPASITEETTI];
        alustaIntJoukko();
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    /**
     * Konstruktori parametrin kanssa. Asettaa joukon taulukon kooksi parametrin
     * arvon.
     *
     * @param kapasiteetti
     */
    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        alkiot = new int[kapasiteetti];
        alustaIntJoukko();
        this.kasvatuskoko = OLETUSKASVATUS;

    }

    /**
     * Konstruktori kahdella parametrilla. Asettaa joukon taulukon kooksi
     * ensimmäisen parametrin arvon ja kasvatuskooksi toisen parametrin arvon.
     *
     * @param kapasiteetti
     * @param kasvatuskoko
     */
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Joukon taulukon kapasiteetti on oltava positiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Joukon taulukon kasvatuskoon on oltava positiivinen");
        }
        alkiot = new int[kapasiteetti];
        alustaIntJoukko();
        this.kasvatuskoko = kasvatuskoko;
    }

    /**
     * Apumetodi joka alustaa taulukon arvot nolliksi ja määrittelee joukossa
     * olevien alkioiden määrän nollaksi.
     */
    private void alustaIntJoukko() {
        for (int i = 0; i < alkiot.length; i++) {
            alkiot[i] = 0;
        }
        alkioidenLkm = 0;
    }

    /**
     * Lisää parametrina annetun luvun joukkoon. Jos luku ei ole jo joukossa,
     * lisätään se joukkoon. Jos luku on jo joukossa, ei sitä silloin lisätä
     * siihen uudestaan.
     *
     * @param luku
     * @return true jos luku lisättiin joukkoon
     */
    public boolean lisaa(int luku) {
        // lisätään luku vain jos sitä ei ole jo joukossa
        if (alkioidenLkm == 0 || !kuuluu(luku)) {
            alkiot[alkioidenLkm] = luku;
            alkioidenLkm++;
            // kasvatetaam taulukkoa jos se tuli lisäyksen johdosta täyteen
            if (alkioidenLkm % alkiot.length == 0) {
                kasvataJoukonTaulukkoa();
            }
            return true;
        }
        return false;
    }

    /**
     * Apumetodi, joka kasvattaa joukon taulukkoa. Tätä
     */
    private void kasvataJoukonTaulukkoa() {
        int[] taulukkoOld = alkiot;
        alkiot = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(taulukkoOld, alkiot);
    }

    /**
     * Tarkistaa kuuluuko parametrina annettu luku joukkoon.
     *
     * @param luku
     * @return true jos kuuluu
     */
    public boolean kuuluu(int luku) {
        return luvunIndeksiTaulussa(luku) != -1;
    }

    /**
     * Apumetodi, joka etsii indeksin taulukosta, jossa parametrina annettu luku
     * esiintyy. Jos lukua ei ole taulukossa, palautetaan -1.
     *
     * @return indeksi jossa luku sijaitsee
     */
    private int luvunIndeksiTaulussa(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == alkiot[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Poistaa parametrina annetun luvun joukosta. Jos lukua ei esiinny
     * joukossa, niin silloin ei tehdä mitään.
     *
     * @param luku
     * @return true jos luku poistettiin joukosta.
     */
    public boolean poista(int luku) {
        int kohta = luvunIndeksiTaulussa(luku);
        if (kohta != -1) {
            alkiot[kohta] = 0;
            for (int j = kohta; j < alkioidenLkm - 1; j++) {
                int apu = alkiot[j];
                alkiot[j] = alkiot[j + 1];
                alkiot[j + 1] = apu;
            }
            alkioidenLkm--;
            return true;
        }
        return false;
    }

    /**
     * Apumetodi joka kopioi vanhan taulukon sisällön uuteen taulukkoon.
     *
     * @param vanha
     * @param uusi
     */
    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            if (i >= uusi.length) {
                return;
            }
            uusi[i] = vanha[i];
        }
    }

    /**
     * Palauttaa joukkoon kuuluvien alkioiden lukumäärän.
     *
     * @return joukon alkioiden määrä
     */
    public int mahtavuus() {
        return alkioidenLkm;
    }

    /**
     * Palauttaa joukon merrkijonoesityksen.
     *
     * @return merkkijonoesitys joukosta
     */
    @Override
    public String toString() {
        switch (alkioidenLkm) {
            case 0:
                return "{}";
            default:
                String mjono = "{";
                for (int i = 0; i < alkioidenLkm - 1; i++) {
                    mjono += alkiot[i];
                    mjono += ", ";
                }
                mjono += alkiot[alkioidenLkm - 1];
                mjono += "}";
                return mjono;
        }
    }

    /**
     * Palauttaa joukon alkiot kokonaisluku-taulukossa.
     *
     * @return joukon alkiot
     */
    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        kopioiTaulukko(alkiot, taulu);
        return taulu;
    }

    /**
     * Lisää parametrina annetun taulukon alkiot joukkoon.
     *
     * @param taulukko
     */
    public void lisaaTaulukonAlkiotJoukkoon(int[] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            this.lisaa(taulukko[i]);
        }
    }

    /**
     * Poistaa parametrina annetun taulukon alkiot joukosta.
     *
     * @param taulukko
     */
    public void poistaTaulukonAlkiotJoukosta(int[] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            this.poista(taulukko[i]);
        }
    }

    /**
     * Palauttaa kahden joukon yhdisteen, joka siis on myös joukko.
     *
     * @param a ensimmäinen joukko
     * @param b toinen joukko
     * @return yhdiste
     */
    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        x.lisaaTaulukonAlkiotJoukkoon(aTaulu);
        x.lisaaTaulukonAlkiotJoukkoon(bTaulu);
        return x;
    }

    /**
     * Palauttaa kahden joukon leikkauksen.
     *
     * @param a ensimmäinen joukko
     * @param b toinen joukko
     * @return leikkaus
     */
    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }

    /**
     * Palauttaa kahden joukon erotuksen. Joukosta a erotetaan joukon b alkiot.
     *
     * @param a ensimmäinen joukko
     * @param b toinen joukko
     * @return erotus
     */
    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        z.lisaaTaulukonAlkiotJoukkoon(aTaulu);
        z.poistaTaulukonAlkiotJoukosta(bTaulu);
        return z;
    }

}
