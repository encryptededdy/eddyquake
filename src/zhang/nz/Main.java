package zhang.nz;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter minimum MMI (Modified Mercalli Intensity Scale), 1 - 10: ");
        int mmi = reader.nextInt();
        GeoNet.showQuakes(mmi);
    }
}
