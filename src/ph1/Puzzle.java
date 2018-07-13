/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ph1;

import static java.lang.Math.abs;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;
import javax.sound.midi.Soundbank;

/**
 *
 * @author sepidmnoroozi
 */
public class Puzzle {
    Vector initialState;
    int step_cost = 1;
    Vector goalState;

    public Puzzle(Vector ini) {
        initialState = ini;
        goalState = new Vector();
        for (int i = 0; i < 9; i++) {
            goalState.add(i);
        }
    }
    
    Vector actions(Vector s){
        Vector actions = new Vector();
        
        if(s.indexOf(0) == 0){
            actions.add("r");
            actions.add("d");
            return actions;
        }
        else if (s.indexOf(0) == 1){
            actions.add("r");
            actions.add("d");
            actions.add("l");
            return actions;
        }
        else if (s.indexOf(0) == 2){
            actions.add("d");
            actions.add("l");
            return actions;
        }
        else if (s.indexOf(0) == 3){
            actions.add("u");
            actions.add("r");
            actions.add("d");
            return actions;
        }
        else if (s.indexOf(0) == 4){
            actions.add("u");
            actions.add("r");
            actions.add("l");
            actions.add("d");
            return actions;
        }
        else if (s.indexOf(0) == 5){
            actions.add("u");
            actions.add("l");
            actions.add("d");
            return actions;
        }
        else if (s.indexOf(0) == 6){
            actions.add("r");
            actions.add("u");
            return actions;
        }
        else if (s.indexOf(0) == 7){
            actions.add("u");
            actions.add("r");
            actions.add("l");
            return actions;
        }
        else{
            actions.add("u");
            actions.add("l");
            return actions;
        }
    }
    Vector result(Vector s , String a){
        Vector result = new Vector();
        result.addAll(s);
        int temp;
        int index = s.indexOf(0);
        if ( a == "r" ){
            temp = (int) s.elementAt(index+1);
            result.setElementAt(temp, index);
            result.setElementAt(0, index+1);
        }
        else if ( a == "l" ){
            temp = (int) s.elementAt(index-1);
            result.setElementAt(temp, index);
            result.setElementAt(0, index-1); 
        }
        else if ( a == "u" ){
            temp = (int) s.elementAt(index-3);
            result.setElementAt(temp, index);
            result.setElementAt(0, index-3);
        }
        else { // a == "d"
            temp = (int) s.elementAt(index+3);
            result.setElementAt(temp, index);
            result.setElementAt(0, index+3); 
        }    
        
        return result;
    }
    boolean goalTest(Vector s){
        if ( s.equals(goalState) )
            return true;
        else
            return false;
    }
    
    public boolean UCS(Puzzle problem){
        
        Vector visited = new Vector();
        
        Node node = new Node(0, initialState);
        Vector<Node> frontier = new Vector<Node>(); //priority queue
        frontier.add(node);
        visited.add(node);
//        System.out.println("node added to fro : "+node.state);
        
        problem.prioritySort(frontier);
        Vector explored = new Vector();
        while(!frontier.isEmpty()){
//            System.out.println("hahaaaaaaa");
            node = frontier.remove(0);
//            System.out.println("node removed from fro : "+node.state);
            if ( problem.goalTest(node.state) ){
                //make path or solution
                System.out.println("num of visited nodes : " + visited.size());
                System.out.println("num of expanded nodes : "+ explored.size());
                System.out.println("solution: "+problem.solution(node));
                System.out.println("path cost : "+node.pathCost);
                return true;
            }
            explored.add(node.state);
//            System.out.println("node added to exp : "+node.state);
            
            Vector<String> actions = problem.actions(node.state);
//            System.out.println(actions);
            for (int i = 0; i < actions.size(); i++) {
                Node child = childNode(node, actions.get(i));
//                System.out.println("child : " + child.state);
//                System.out.println("explored : "+ explored);
                if ( !searchExploredByState(explored, child.state) && !searchFrontierByState(frontier, child) ){
                    frontier.add(child);
                    visited.add(node);
//                    System.out.println(frontier);
                    problem.prioritySort(frontier);
                }
                else if ( searchFrontierByState(frontier, child) ){
                    replaceFrontier(frontier, child);
                    problem.prioritySort(frontier);
                }
            }
        }
        return false;
    }
    public boolean DFS(Puzzle problem){
        Vector visited = new Vector();
        
        Node node = new Node(0, initialState);
        if( problem.goalTest(node.state) )
            return true;
        Vector<Node> frontier = new Vector<>(); //LIFO queue
        frontier.add(node);
        visited.add(node);
        //System.out.println("node added to fro : "+node.state);
        Vector explored = new Vector(); // vector of states
        while(true){
            //System.out.println("hahaaaaaaa");
            if (frontier.isEmpty())
                return false;
            node = frontier.lastElement(); //get last element
            boolean b = frontier.remove(node); //remove last element
            //System.out.println("node removed from fro : "+node.state);
            explored.add(node.state);
            
            //System.out.println("node added to exp : "+node.state);
            Vector<String> actions = problem.actions(node.state);
            //System.out.println(actions);
            for (int i = 0; i < actions.size(); i++) {
                Node child = childNode(node, actions.get(i));
                //System.out.println("child : " + child.state);
                //System.out.println("explored : "+ explored);
                if ( !searchExploredByState(explored, child.state) && !searchFrontierByState(frontier, child) ){
                    if(problem.goalTest(child.state)){
                        System.out.println("num of visited nodes : " + visited.size());
                        System.out.println("num of expanded nodes : "+ explored.size());
                        System.out.println("solution: "+problem.solution(node));
                        System.out.println("path cost : "+node.pathCost);
                        return true;
                    }
                    frontier.add(child);
                    visited.add(node);
                    //System.out.println(frontier);
                }
            }
        } 
    }
    public boolean bidirectional(Puzzle problem){
        int expanded = 0;
        
        // dar in algorithm faghat halat baramoon mohem mishe! node mafhoomi nadare
        Vector<Node> frontierI = new Vector();
        Vector<Node> frontierG = new Vector();
        
        Vector<Vector> visitedI = new Vector();
        Vector<Vector> visitedG = new Vector();
        
        Node initNode = new Node(0 , initialState);
        Node goalNode = new Node(0 , goalState);
        frontierI.add(initNode);
        frontierG.add(goalNode);
        visitedI.add(initNode.state);
        visitedG.add(goalNode.state);
        
        String a = " ";
        String b = " ";
        
        Node x ;
        Node y ;
        while ( !frontierI.isEmpty() && !frontierG.isEmpty() ){
            if (! frontierI.isEmpty() ){
                x = frontierI.firstElement();
                frontierI.remove(x);
                expanded++;
                //System.out.println("x removed from frontier : " + x.state);
                if ( x.state.equals(goalState) ){
                    System.out.println("he1");
                    System.out.println("num of visited nodes : " + visitedI.size()+visitedG.size());
                    System.out.println("num of expanded nodes : "+ expanded);
                    System.out.println("path cost : "+x.pathCost);
                    System.out.println("solution : "+solution(x));
                    return true;
                }
                else if ( searchFrontierByState(frontierG, x) ){
                    Node n = new Node(0, initialState) ;
                    for (int i = 0; i < frontierG.size(); i++) {
                        if ( frontierG.get(i).state.equals(x.state) )
                            n = frontierG.get(i);
                    }
                    System.out.println("he2");
                    System.out.println("num of visited nodes : " + visitedI.size()+visitedG.size());
                    System.out.println("num of expanded nodes : "+ expanded);
                    System.out.println("path cost : "+(x.pathCost + n.pathCost));
                    System.out.println("solution : "+solution(x) + helpBi(n));
                    return true;
                }
                Vector<String> actions = new Vector();
                actions = actions(x.state);
                for (String action : actions) {
                    y = new Node(x,x.pathCost+step_cost ,result(x.state, action), action);
                    if ( !visitedI.contains(y.state) ){
                        visitedI.add(y.state);
                        frontierI.add(y);
                  //      System.out.println("added to frontier I : "+y.state);
                    }
                }
            }
            if ( !frontierG.isEmpty() ){
                y = frontierG.firstElement();
                frontierG.remove(y);
                b = solution(y);
                
                //System.out.println("y emoved from frontier : " + y.state);
                if ( y.state.equals(initialState) ){
                    System.out.println("he3");
                    System.out.println("num of visited nodes : " + visitedI.size()+visitedG.size());
                    System.out.println("num of expanded nodes : "+ expanded);
                    System.out.println("path cost : "+y.pathCost);
                    System.out.println("solution : "+solution(y));
                    return true;
                }
                else if ( searchFrontierByState(frontierI, y) ){
                    Node n = new Node(0, goalState) ;
                    for (int i = 0; i < frontierI.size(); i++) {
                        if ( frontierI.get(i).state.equals(y.state) )
                            n = frontierI.get(i);
                    }
                    System.out.println("he4");
                    System.out.println("num of visited nodes : " + visitedI.size()+visitedG.size());
                    System.out.println("num of expanded nodes : "+ expanded);
                    System.out.println("path cost : "+y.pathCost + n.pathCost);
                    System.out.println("solution : "+solution(n) + helpBi(y));
                    return true;
                }
                Vector<String> actions = new Vector();
                actions = actions(y.state);
                for (String action : actions) {
                    x = new Node(y,y.pathCost+step_cost,result(y.state, action) , action);
                    if ( !visitedG.contains(x.state) ){
                        visitedG.add(x.state);
                        frontierG.add(x);
                        //System.out.println("added to frontier G : "+x.state);
                    }
                }
            }
        }
        return false;
    }    
    public boolean aStar(Puzzle problem){
        Vector visited = new Vector();
        
        Node node = new Node(0, initialState);
        Vector<Node> frontier = new Vector<Node>(); //priority queue
        frontier.add(node);
//        System.out.println("node added to fro : "+node.state);
        visited.add(node);
        problem.aStarfrontierSort(frontier);
        Vector explored = new Vector();
        while(!frontier.isEmpty()){
//            System.out.println("hahaaaaaaa");
            node = frontier.remove(0);
//            System.out.println("node removed from fro : "+node.state);
            if ( problem.goalTest(node.state) ){
                //make path or solution
                System.out.println("num of visited nodes : " + visited.size());
                System.out.println("num of expanded nodes : "+ explored.size());
                System.out.println("solution: "+problem.solution(node));
                System.out.println("path cost : "+node.pathCost);
                return true;
            }
            explored.add(node.state);
//            System.out.println("node added to exp : "+node.state);
            
            
            Vector<String> actions = problem.actions(node.state);
//            System.out.println(actions);
            for (int i = 0; i < actions.size(); i++) {
                Node child = childNode(node, actions.get(i));
//                System.out.println("child : " + child.state);
//                System.out.println("explored : "+ explored);
                if ( !searchExploredByState(explored, child.state) && !searchFrontierByState(frontier, child) ){
                    frontier.add(child);
                    visited.add(node);
//                    System.out.println(frontier);
                    problem.aStarfrontierSort(frontier);
                }
                else if ( searchFrontierByState(frontier, child) ){
                    replaceFrontier(frontier, child);
                    problem.aStarfrontierSort(frontier);
                }
            }
        }
        return false;
    }
    
    public int directDist(Vector s){
        int count = 0;
        for (int i = 0; i < 9; i++) {
            int index = s.indexOf(i);
            count = count + abs(i - index);
        }
       // System.out.println(s + "" + count);
        return count;
    }
    public void aStarfrontierSort(Vector<Node> f){
        Node n1 , n2;
        for (int i = 0; i < f.size() - 1; i++) {
            for (int j = 0; j < f.size() - i - 1; j++) {
                if ( (f.get(j).pathCost + directDist(f.get(j).state)) > (f.get(j+1).pathCost + directDist(f.get(j+1).state)) ) {
                    n1 = f.elementAt(j);
                    n2 = f.elementAt(j+1);
                    f.setElementAt(n1 , j+1);
                    f.setElementAt(n2, j);
                }
            }
        }
    }
    public String solution(Node n){
        String sol = "";
        Node node = n;
        while ( !node.action.equals(" ") ){
            sol = sol.concat(node.action);
            node = node.parent;
        }
        String reverse = "";
        for(int i = sol.length() - 1; i >= 0; i--)
        {
            reverse = reverse + sol.charAt(i);
        }
        return reverse;
    }
    public String helpBi(Node n){
        String sol = solution(n);
        String res = "";
        for(int i = sol.length() - 1; i >= 0; i--)
        {
            if ( sol.charAt(i) == 'u')
                res = res.concat("d");
            else if ( sol.charAt(i) == 'd' )
                res = res.concat("u");
            else if ( sol.charAt(i) == 'r' )
                res = res.concat("l");
            else // = l
                res = res.concat("r");
        }
        return res;
    }
    public void replaceFrontier(Vector<Node> v , Node node){
        for (int i = 0; i < v.size(); i++) {
            if( v.get(i).state == node.state && node.pathCost > v.get(i).pathCost ){
                v.setElementAt(node , i);
            }
        }
    } 
    
    public boolean searchFrontierByState(Vector<Node> v , Node node){
        for (int i = 0; i < v.size(); i++) {
            if( v.get(i).state.equals(node.state) ){ //peydash kardim
                return true;
            }
        }
        //nist
        return false;
    }
    public boolean searchExploredByState(Vector e , Vector s){
        if ( e.contains(s)){
            return true;
        }
        //nist
        return false;
    }
    
    //for frontier in UCS
    public void prioritySort( Vector<Node> v ){
        Node n1 , n2;
        for (int i = 0; i < v.size() - 1; i++) {
            for (int j = 0; j < v.size() - i - 1; j++) {
                if (v.get(j).pathCost  > v.get(j+1).pathCost ) {
                    n1 = v.elementAt(j);
                    n2 = v.elementAt(j+1);
                    v.setElementAt(n1 , j+1);
                    v.setElementAt(n2, j);
                }
            }
        }
    }
    
    
    
    public Node childNode(Node parent ,String action ){
        return new Node(parent, parent.pathCost+step_cost, result(parent.state, action), action);
    }
    private class Node {
        Node parent;
        int pathCost;
        Vector state;
        String action;

        public Node(int pathCost, Vector s){
            this.parent = null;
            this.pathCost = pathCost;
            this.action = " ";
            this.state = s;
        }
        public Node(Node parent, int pathCost, Vector s, String action) {
            this.parent = parent;
            this.pathCost = pathCost;
            this.state = s;
            this.action = action;
        }
        public Node(Node p, Vector s , String action){
            this.parent = p ;
            this.pathCost = 0;
            this.action = action;
            this.state = s;
        }
        
    
    }    
}
 