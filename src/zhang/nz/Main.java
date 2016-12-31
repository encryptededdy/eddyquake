package zhang.nz;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter minimum MMI (Modified Mercalli Intensity Scale), 1 - 8: ");
        int mmi = reader.nextInt();
        GeoNet.showQuakes(mmi);
        System.out.print("Enter Quake ID to show more information: ");
        int id = reader.nextInt();
        GeoNet.quakeDetail(id);
    }
}
