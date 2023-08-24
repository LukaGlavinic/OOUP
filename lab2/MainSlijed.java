package lab2;

public class MainSlijed {
    
    public static void main(String[] args) {
        // SlijedBrojeva slijedTip = new SlijedBrojeva(new TipkovnickiIzvor());
        // Akcija aSum = new AkcijaSuma(slijedTip);
        // Akcija aPros = new AkcijaProsjek(slijedTip);
        // Akcija aMed = new AkcijaMedian(slijedTip);
        // Akcija aZap = new AkcijaZapisUDat("C:\\Users\\Luka\\!!HelloWorld\\OOUP\\lab2\\zapisi.txt", slijedTip);
        // slijedTip.dodajAkciju(aSum);
        // slijedTip.dodajAkciju(aPros);
        // slijedTip.dodajAkciju(aMed);
        // slijedTip.dodajAkciju(aZap);
        // slijedTip.kreni();
        // slijedTip.odjaviAkciju(aSum);
        // slijedTip.odjaviAkciju(aPros);
        // slijedTip.odjaviAkciju(aMed);
        // slijedTip.odjaviAkciju(aZap);

        SlijedBrojeva slijedTip = new SlijedBrojeva(new DatotecniIzvor("C:\\Users\\Luka\\!!HelloWorld\\OOUP\\lab2\\izvor.txt"));
        Akcija aSum = new AkcijaSuma(slijedTip);
        Akcija aPros = new AkcijaProsjek(slijedTip);
        Akcija aMed = new AkcijaMedian(slijedTip);
        Akcija aZap = new AkcijaZapisUDat("C:\\Users\\Luka\\!!HelloWorld\\OOUP\\lab2\\zapisi.txt", slijedTip);
        slijedTip.dodajAkciju(aSum);
        slijedTip.dodajAkciju(aPros);
        slijedTip.dodajAkciju(aMed);
        slijedTip.dodajAkciju(aZap);
        slijedTip.kreni();
        slijedTip.odjaviAkciju(aSum);
        slijedTip.odjaviAkciju(aPros);
        slijedTip.odjaviAkciju(aMed);
        slijedTip.odjaviAkciju(aZap);
    }
}