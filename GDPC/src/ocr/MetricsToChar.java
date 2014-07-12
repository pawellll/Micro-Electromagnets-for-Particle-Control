/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ocr;

/**
 * Klasa przyporządkowująca wyliczoną odległość do znaku
 *
 * @author Maciej Janusz
 */
public class MetricsToChar implements Comparable<MetricsToChar> {
    
    public MetricsToChar(double metric, char character) {
        this.metric = metric;
        this.character = character;
    }
    
    public Double getMetric() {
        return metric;
    }
    
    public char getChar() {
        return character;
    }
    
    public void setMetric(Double newMetric) {
        this.metric = newMetric;
    }
    
    public void setChar(char newChar) {
        this.character = newChar;
    }
    
    @Override
    public int compareTo(MetricsToChar t) {
        if(this.metric < t.metric) {
            return -1;
        }
        if(this.metric > t.metric) {
            return 1;
        } else { 
            return 0;
        }
    }
    
    private double metric; // wyliczona odległosc współrzędnych wektora cech
    private char character; // odpowiadający znak

}
