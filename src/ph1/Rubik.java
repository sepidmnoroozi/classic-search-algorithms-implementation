/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ph1;

import java.util.Vector;

/**
 *
 * @author sepidmnoroozi
 */
public class Rubik {
    
    Vector initialState;
    int step_cost = 1;
    Vector goalState;

    public Rubik(Vector ini) {
        initialState = ini;
    }
    Vector actions(Vector s){
        Vector actions = new Vector();
        actions.add("T");
        actions.add("TC");
        actions.add("F");
        actions.add("FC");
        actions.add("R");
        actions.add("RC");
        return actions;
    }
    Vector result(Vector s , String a){
        Vector result = new Vector();
        result.addAll(s);
        String tmp1, tmp2, tmp3, tmp4;
        if( a.equals("T") ){
            result.setElementAt(s.get(16), 15);
            result.setElementAt(s.get(17), 14);
            result.setElementAt(s.get(4), 16);
            result.setElementAt(s.get(5), 17);
            result.setElementAt(s.get(20), 4);
            result.setElementAt(s.get(21), 5);
            result.setElementAt(s.get(14), 21);
            result.setElementAt(s.get(15), 20);
            
            result.setElementAt(s.get(0), 1);
            result.setElementAt(s.get(2), 0);
            result.setElementAt(s.get(1), 3);
            result.setElementAt(s.get(3), 2);
            return result;
        }
        else if ( a.equals("TC") ){
            result.setElementAt(s.get(14), 17);
            result.setElementAt(s.get(15), 16);
            result.setElementAt(s.get(21), 14);
            result.setElementAt(s.get(20), 15);
            result.setElementAt(s.get(5), 21);
            result.setElementAt(s.get(4), 20);
            result.setElementAt(s.get(17), 5);
            result.setElementAt(s.get(16), 4);
            
            result.setElementAt(s.get(1), 0);
            result.setElementAt(s.get(3), 1);
            result.setElementAt(s.get(0), 2);
            result.setElementAt(s.get(2), 3);
            return result;
        }
        else if ( a.equals("F") ){
            result.setElementAt(s.get(17), 3);
            result.setElementAt(s.get(19), 2);
            result.setElementAt(s.get(2), 20);
            result.setElementAt(s.get(3), 22);
            result.setElementAt(s.get(22), 8);
            result.setElementAt(s.get(20), 9);
            result.setElementAt(s.get(8), 17);
            result.setElementAt(s.get(9), 19);
            
            result.setElementAt(s.get(4), 5);
            result.setElementAt(s.get(6), 4);
            result.setElementAt(s.get(5), 7);
            result.setElementAt(s.get(7), 6);
            return result;
        }
        else if ( a.equals("FC") ){
            result.setElementAt(s.get(22), 3);
            result.setElementAt(s.get(20), 2);
            result.setElementAt(s.get(9), 20);
            result.setElementAt(s.get(8), 22);
            result.setElementAt(s.get(17), 8);
            result.setElementAt(s.get(19), 9);
            result.setElementAt(s.get(3), 17);
            result.setElementAt(s.get(2), 19);
            
            result.setElementAt(s.get(7), 5);
            result.setElementAt(s.get(5), 4);
            result.setElementAt(s.get(4), 6);
            result.setElementAt(s.get(6), 7);
            return result;
        }
        else if ( a.equals("R") ){
            result.setElementAt(s.get(3), 15);
            result.setElementAt(s.get(1), 13);
            result.setElementAt(s.get(13), 9);
            result.setElementAt(s.get(15), 11);
            result.setElementAt(s.get(9), 5);
            result.setElementAt(s.get(11), 7);
            result.setElementAt(s.get(5), 1);
            result.setElementAt(s.get(7), 3);
            
            result.setElementAt(s.get(20), 21);
            result.setElementAt(s.get(21), 23);
            result.setElementAt(s.get(22), 20);
            result.setElementAt(s.get(23), 22);
            return result;
        }
        else if ( a.equals("RC") ){
            result.setElementAt(s.get(15), 3);
            result.setElementAt(s.get(13), 1);
            result.setElementAt(s.get(9), 13);
            result.setElementAt(s.get(11), 15);
            result.setElementAt(s.get(5), 9);
            result.setElementAt(s.get(7), 11);
            result.setElementAt(s.get(1), 5);
            result.setElementAt(s.get(3), 7);
            
            result.setElementAt(s.get(21), 20);
            result.setElementAt(s.get(23), 21);
            result.setElementAt(s.get(20), 22);
            result.setElementAt(s.get(22), 23);
            return result;
        }
        else
            return result;
    }
    boolean goalTest(Vector s){
        int res = 0;
        for (int i = 0; i < 21; i = i +4) {
            if ( s.get(i).equals(s.get(i+1)) && s.get(i).equals(s.get(i+2)) && s.get(i).equals(s.get(i+3)) )
                res++;
        }
        if ( res == 6 )
            return true;
        else
            return false;
    }
    
    boolean BFS(Rubik problem){
        Vector visited = new Vector();
        
        Node node = new Node(0, initialState);
        if( problem.goalTest(node.state) ){
            System.out.println("initial State in goal test");
            return true;
        }
        Vector<Node> frontier = new Vector<>(); //FIFO queue
        frontier.add(node);
        visited.add(node);
        //System.out.println("node added to fro : "+node.state);
        Vector explored = new Vector(); // vector of states
        while(true){
            //System.out.println("hahaaaaaaa");
            if (frontier.isEmpty())
                return false;
            node = frontier.firstElement(); //get last element
            boolean b = frontier.remove(node); //remove first element
//            System.out.println("node removed from fro : "+node.state);
            explored.add(node.state);
            
//            System.out.println("node added to exp : "+node.state);
            Vector<String> actions = problem.actions(node.state);
//            System.out.println(actions);
            for (int i = 0; i < actions.size(); i++) {
                Node child = childNode(node, actions.get(i));
//                System.out.println("child : " + child.state);
//                System.out.println("explored : "+ explored);
                if ( !searchExploredByState(explored, child.state) && !searchFrontierByState(frontier, child) ){
                    if(problem.goalTest(child.state)){
                        System.out.println("num of visited nodes : " + visited.size());
                        System.out.println("num of expanded nodes : "+ explored.size());
                        System.out.println("solution: "+problem.solution(child));
                        System.out.println("path cost : "+child.pathCost);
                        return true;
                    }
                    frontier.add(child);
                    visited.add(node);
//                    System.out.println(frontier);
                }
            }
        }
    }
    boolean DLS(Rubik problem , int limit){
        Vector visited = new Vector();
        
        Node node = new Node(0, initialState);
        if( problem.goalTest(node.state) )
            return true;
        Vector<Node> frontier = new Vector<>(); //LIFO queue
        frontier.add(node);
        visited.add(node);
        //System.out.println("node added to fro : "+node.state);
        Vector explored = new Vector(); // vector of states
        
        Node n ;
        
        while(true){
            
            if (frontier.isEmpty())
                return false;
            
            n = frontier.lastElement(); //get last element
            boolean b = frontier.remove(node); //remove last element
            //dar in masale pathcost = depth ast
            if ( n.pathCost < limit ){
                node = n;
                explored.add(node.state);
                
                Vector<String> actions = problem.actions(node.state);
                for (int i = 0; i < actions.size(); i++) {
                    Node child = childNode(node, actions.get(i));
                    if ( !searchExploredByState(explored, child.state) && !searchFrontierByState(frontier, child) ){
                        if(problem.goalTest(child.state)){
                            
                            System.out.println("num of visited nodes : " + visited.size());
                            System.out.println("num of expanded nodes : "+ explored.size());
                            System.out.println("solution: "+problem.solution(child));
                            System.out.println("path cost : "+child.pathCost);
                            return true;
                        }
                        frontier.add(child);
                        visited.add(node);
                    }
                }
            }  
        }
    }
    
    boolean IDS(Rubik problem){
        int limit = 0;
        while (limit < 15){
            if(DLS(problem, limit)){
                System.out.println("limit : "+limit+ " Successful");
                return true;
            }
            else{
                System.out.println("limit : "+limit+ " Unsuccessful");
                limit++;
            }
                
        }
        return false;
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
        
    
    }    
}
