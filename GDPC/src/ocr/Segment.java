package ocr;

import GD.Digitalize;
import Platform.CrossBitmap;
import Supp.Colors;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa przechowująca metody statyczne służące do segmentacji obrazu na
 * pojedyncze obiekty reprezentujące znaki
 *
 * @author Maciej Janusz
 */
public class Segment {

    /**
     * Funkcja poszukuje rzędów w obrazie zawierających przynajmniej jeden
     * czarny piksel
     *
     * @param imageIn
     * @return Lista rzędów (indeksy) z przynajmniej jednym czarnym pikselem
     */
    public static List<Integer> searchRows(CrossBitmap imageIn) {
        List<Integer> rows = new ArrayList<>();

        for (int y = 0; y < imageIn.getHeight(); y++) {
            for (int x = 0; x < imageIn.getWidth(); x++) {
                if (Digitalize.RGBDistance(imageIn.getRGB(x, y), Colors.WHITE) < 10) {
                    rows.add(y);
                    break;
                }
            }
        }
        rows.add(Integer.MAX_VALUE);
        return rows;
    }

    /**
     * Funkcja poszukuje kolumn w obrazie zawierających przynajmniej jeden
     * czarny piksel
     *
     * @param imageIn
     * @return Lista kolumn (indeksy) z przynajmniej jednym czarnym pikselem
     */
    public static List<Integer> searchColumns(CrossBitmap imageIn) {
        List<Integer> columns = new ArrayList<>();

        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
                if (Digitalize.RGBDistance(imageIn.getRGB(x, y), Colors.WHITE) < 10) {
                    columns.add(x);
                    break;
                }
            }
        }

        columns.add(Integer.MAX_VALUE);
        return columns;
    }

    /**
     * Funkcja segmentuje obraz na spójne segmenty reprezentujące wiersze tekstu
     * na obrazie
     *
     * @param imageIn
     * @return Lista obrazów - wierszy tekstu
     */
    public static List<CrossBitmap> horizontalSlice(CrossBitmap imageIn) {

        List<Integer> rows = searchRows(imageIn);
        int beg = (int) rows.get(0);

        List<CrossBitmap> slices = new ArrayList();

        for (int i = 0; i < rows.size() - 1; i++) {

            int end = (int) rows.get(i);
            if (end + 1 != (int) (rows.get(i + 1))) {

                CrossBitmap newSlice = new CrossBitmap(
                        new BufferedImage(imageIn.getWidth(), end - beg + 1,
                                BufferedImage.TYPE_INT_RGB));

                for (int x = 0; x < imageIn.getWidth(); x++) {
                    for (int y = beg; y < end + 1; y++) {
                        int r = imageIn.getIntComponent0(x, y);
                        int g = imageIn.getIntComponent1(x, y);
                        int b = imageIn.getIntComponent2(x, y);

                        newSlice.setIntColor(x, y - beg, r, g, b);
                    }

                }

                slices.add(newSlice);

                beg = (int) rows.get(i + 1);
            }
        }

        return slices;
    }

    /**
     * Funkcja segmentuje obraz reprezentujący wiersz tekstu na spójne obrazy
     * reprezentujące słowa (słowa dzielą spacje, które są wykrywane, gdy znaki
     * dzieli przestrzeń większa niż parametr spaceThreshold
     *
     * @param imageIn
     * @param spaceTreshold próg wykrycia spacji
     * @return Lista obrazów reprezentujących wyrazy
     */
    public static List<CrossBitmap> wordsSlice(CrossBitmap imageIn, int spaceTreshold) {
        List<Integer> columns = searchColumns(imageIn);
        int beg = (int) columns.get(0);

        List<CrossBitmap> slices = new ArrayList();

        for (int i = 0; i < columns.size() - 1; i++) {

            int end = (int) columns.get(i);
            if (end + spaceTreshold < (int) (columns.get(i + 1))) {

                CrossBitmap newSlice = new CrossBitmap(
                        new BufferedImage(end - beg + 1, imageIn.getHeight(),
                                BufferedImage.TYPE_INT_RGB));

                for (int x = beg; x < end + 1; x++) {
                    for (int y = 0; y < imageIn.getHeight(); y++) {
                        int r = imageIn.getIntComponent0(x, y);
                        int g = imageIn.getIntComponent1(x, y);
                        int b = imageIn.getIntComponent2(x, y);
                        newSlice.setIntColor(x - beg, y, r, g, b);
                    }
                }

                slices.add(newSlice);

                beg = (int) columns.get(i + 1);
            }
        }
        return slices;
    }

    /**
     * Funkcja dzieli obraz reprezentujący wyraz na obrazy poszczególnych
     * znaków, na podstawie których konstruowane sa obiekty typu OCRCharacter
     *
     * @param imageIn
     * @param cutTreshold
     * @return Lista obiektów typu OCRCharacter
     */
    public static List<OCRCharacter> getOCRCharacters(CrossBitmap imageIn, int cutTreshold) {

        List<Integer> columns = searchColumns(imageIn);
        int beg = (int) columns.get(0);

        List<OCRCharacter> slices = new ArrayList();

        for (int i = 0; i < columns.size() - 1; i++) {

            int end = (int) columns.get(i);
            if (end + 1 != (int) (columns.get(i + 1))) {

                CrossBitmap newSlice = new CrossBitmap(
                        new BufferedImage(imageIn.getWidth(), end - beg + 1,
                                BufferedImage.TYPE_INT_RGB));

                for (int x = beg; x < end + 1; x++) {
                    for (int y = 0; y < imageIn.getHeight(); y++) {
                        int r = imageIn.getIntComponent0(x, y);
                        int g = imageIn.getIntComponent1(x, y);
                        int b = imageIn.getIntComponent2(x, y);
                        newSlice.setIntColor(x - beg, y, r, g, b);
                    }
                }

                slices.addAll(cutIn(horizontalCrop(newSlice), cutTreshold));

                beg = (int) columns.get(i + 1);
            }
        }
        return slices;
    }

    /**
     * Funkcja obcina przestrzeń zawierającą wyłącznie białe piksele od góry i
     * dołu obrazu
     *
     * @param imageIn
     * @return Okrojony obraz
     */
    public static CrossBitmap horizontalCrop(CrossBitmap imageIn) {

        List rows = searchRows(imageIn);
        int beg = (int) rows.get(0);
        int end = (int) rows.get(rows.size() - 2);

        CrossBitmap croppedImage = new CrossBitmap(
                new BufferedImage(imageIn.getWidth(), end - beg + 1,
                        BufferedImage.TYPE_INT_RGB));

        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = beg; y < end + 1; y++) {
                int r = imageIn.getIntComponent0(x, y);
                int g = imageIn.getIntComponent1(x, y);
                int b = imageIn.getIntComponent2(x, y);

                croppedImage.setIntColor(x, y - beg, r, g, b);
            }

        }
        return croppedImage;
    }

    /**
     * Funkcja wyznacza próg wykrycia spacji za pomocą formuły s = x/3, gdzie x
     * to średnia wysokość rzędu znaków na obrazie
     *
     * @param horSlices lista obrazów reprezentujących wiersze tekstu
     * @return Próg wykrycia spacji (minimalna szerokość przestrzeni między
     * znakami)
     */
    private static int getSpaceTreshold(List<CrossBitmap> horSlices) {

        int heightSum = 0;

        for (CrossBitmap im : horSlices) {
            heightSum += im.getHeight();
        }

        return (heightSum / horSlices.size()) / 3;
    }

    /**
     * Funkcja dzieli obraz na segmenty, jeśli jego szerokość jest za duża
     * (wykrywanie sklejonych znaków)
     *
     * @param OCRCharIn wejściowy OCRCharacter
     * @param howMany ilość elementow
     * @return lista OCRcharacter
     */
    private static List<OCRCharacter> cutIn(CrossBitmap imageIn, int widthTreshold) {

        List<OCRCharacter> outList = new ArrayList<>();

        if (imageIn.getWidth() / widthTreshold > 1) {
            int minBlack = imageIn.getHeight();
            int index = imageIn.getWidth();

            for (int x = 0; x < imageIn.getWidth(); x++) {
                int blackCount = 0;
                for (int y = 0; y < imageIn.getHeight(); y++) {
                    if (Digitalize.RGBDistance(imageIn.getRGB(x, y), Colors.WHITE) < 10) {
                        blackCount += 1;
                    }
                }
                if (blackCount < minBlack) {
                    index = x;
                }
            }

            CrossBitmap newPiece = new CrossBitmap(new BufferedImage(index, imageIn.getHeight(),
                    BufferedImage.TYPE_INT_RGB));
            CrossBitmap nextPiece = new CrossBitmap(new BufferedImage(imageIn.getWidth() - index, imageIn.getHeight(),
                    BufferedImage.TYPE_INT_RGB));
            
            for (int i = 0; i < index; i++) {
                for (int j = 0; j < imageIn.getHeight(); j++) {
                    int r = imageIn.getIntComponent0(i, j);
                    int g = imageIn.getIntComponent1(i, j);
                    int b = imageIn.getIntComponent2(i, j);

                    newPiece.setIntColor(i, j, r, g, b);
                }
            }

            for (int i = index; i < imageIn.getWidth(); i++) {
                for (int j = 0; j < imageIn.getHeight(); j++) {
                    int r = imageIn.getIntComponent0(i, j);
                    int g = imageIn.getIntComponent1(i, j);
                    int b = imageIn.getIntComponent2(i, j);

                    nextPiece.setIntColor(i - index, j, r, g, b);
                }
            }

            outList.add(new OCRCharacter(newPiece));
            outList.addAll(cutIn(nextPiece, widthTreshold));

            return outList;
        } else {
            outList.add(new OCRCharacter(imageIn));
            return outList;
        }

    }

    /**
     * Funkcja konstruuje strukturę danych, w której obiekty reprezentujące
     * znaki są złożone w strukturę: Rzędy - wyrazy - znaki
     *
     * @param imageIn
     * @return
     */
    public static List<List<List<OCRCharacter>>> getSegments(CrossBitmap imageIn) {

        List<CrossBitmap> rzedy = horizontalSlice(imageIn);

        int spaceTreshold = getSpaceTreshold(rzedy);

        List<List<CrossBitmap>> rzedy_wyrazy = new ArrayList<>();
        for (CrossBitmap rzad : rzedy) {
            List<CrossBitmap> wyrazy = wordsSlice(rzad, spaceTreshold);
            rzedy_wyrazy.add(wyrazy);
        }

        List<List<List<OCRCharacter>>> lista_rzedy_wyrazy_znaki = new ArrayList<>();
        for (List<CrossBitmap> rzad_wyraz : rzedy_wyrazy) {
            List<List<OCRCharacter>> rzedy_wyrazy_znaki = new ArrayList<>();

            for (CrossBitmap wyraz : rzad_wyraz) {
                rzedy_wyrazy_znaki.add(getOCRCharacters(wyraz, (int) (spaceTreshold * 2))); //lista znaków
            }

            lista_rzedy_wyrazy_znaki.add(rzedy_wyrazy_znaki);
        }

        return lista_rzedy_wyrazy_znaki;
    }
}
