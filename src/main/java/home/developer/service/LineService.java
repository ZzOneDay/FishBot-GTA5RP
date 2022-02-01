package home.developer.service;

public interface LineService {

    /**
     * Generate points for way
     *
     * @param Y current value of Y
     * @return value of X
     */
    int getX(int Y);

    /**
     * Generate points for way
     *
     * @param X current value of x
     * @return value of Y
     */
    int getY(int X);
}
