/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ph1;

import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author sepidmnoroozi
 */
public class PH1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
//2
//        Scanner scan = new Scanner(System.in);
//        Vector<Integer> s = new Vector();
//        int[] in = new int[9];
//        for (int i = 0; i < 9; i++) {
//            s.add(scan.nextInt());
//        }
//        Puzzle p = new Puzzle(s);
//        p.aStar(p);

//3
        Scanner scan = new Scanner(System.in);
        Vector s = new Vector();
        for (int i = 0; i < 24; i++) {
            s.add(scan.next());
        }    
        Rubik r = new Rubik(s);
        r.IDS(r);
//1        
//        Scanner scan = new Scanner(System.in);
//        Vector s = new Vector(4);
//        int n = scan.nextInt();
//        int m = scan.nextInt();
//        System.out.println("n : " + n);
//        System.out.println("m : " + m);
//        int numOfWalls = scan.nextInt();
//        System.out.println("num of walls : " + numOfWalls);
//        Vector walls = new Vector(numOfWalls);
//        for (int i = 0; i < numOfWalls; i++) {
//            Vector wall = new Vector(2);
//            Vector side1 = new Vector(2);
//            Vector side2 = new Vector(2);
//            side1.add(scan.nextInt());
//            side1.add(scan.nextInt());
//            side2.add(scan.nextInt());
//            side2.add(scan.nextInt());
//            wall.add(side1);
//            wall.add(side2);
//            walls.add(wall);
//        }
//        RescueRobot rr = new RescueRobot(n, m, walls);
//        rr.aStar(rr);
    }
    
    
    
}
