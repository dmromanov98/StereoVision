package WorkWithImages;

public class Pixel {

    private int posX;
    private int posY;

    private byte alpha;
    private byte red;
    private byte green;
    private byte blue;


    public Pixel(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Pixel(int posX, int posY, int pixelColor) {
        this.posX = posX;
        this.posY = posY;

        alpha = (byte) ((pixelColor >> 24) & 0xff); // alpha
        blue = (byte) (pixelColor & 0xff); // blue
        green = (byte) ((pixelColor >> 8) & 0xff); // green
        red = (byte) ((pixelColor >> 16) & 0xff); // red

    }

    public Pixel(int pixelColor) {

        alpha = (byte) ((pixelColor >> 24) & 0xff); // alpha
        blue = (byte) (pixelColor & 0xff); // blue
        green = (byte) ((pixelColor >> 8) & 0xff); // green
        red = (byte) ((pixelColor >> 16) & 0xff); // red

    }

    public int[] getPosition() {
        int position[] = new int[2];
        position[0] = posX;
        position[1] = posY;
        return position;
    }

    public byte[] getColorRGB() {
        byte color[] = new byte[4];
        color[0] = alpha;
        color[1] = red;
        color[2] = green;
        color[3] = blue;

        return color;
    }

    public byte getRed() {
        return red;
    }

    public byte getGreen() {
        return green;
    }

    public byte getBlue() {
        return blue;
    }

    public byte getAlpha() {
        return alpha;
    }

}
