import java.util.Scanner;

public class Downscale {
    public static void main(String[] args) throws Exception {
        final Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter the name of the image file:");
        final String fileName = keyboard.nextLine();

        final ImageTensor image = ImageTensor.loadFromFile(fileName);
        System.out.println("\nThe original image is:");
        image.debugPrint();

        final ImageTensor scaledImage = image.downscaleToHalfResolution();
        System.out.println("\nThe scaled image is:");
        scaledImage.debugPrint();

        System.out.println("Done");
        keyboard.close();
    }
}