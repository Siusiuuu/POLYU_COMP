package hk.edu.polyu.comp.comp2021.assignment2.randomwalk;

import java.util.ArrayList;
import java.util.HashSet;

class Node{

    // degree of a node is the number of adjacency nodes, i.e., the number of nodes that are connected to this node by an edge.
    private int degree;
    //The graph this node belongs to
    private Graph graph;

    public Graph getGraph(){
        return this.graph;
    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }

    public void setDegree(){
        // Task 3.1: Calculate this.degree based on the random walk sequences.
        HashSet<Node> uniqueAjacent=new HashSet<>();
        for(RandomWalkSequence sequence: this.graph.getAllRandomWalkSequences()){
            ArrayList<Node> a=sequence.getSequence();
            for(int i=0;i<a.size()-1;i++){
                if(a.get(i).equals(this)){
                    uniqueAjacent.add(a.get(i+1));
                }
            }
        }
        this.degree=uniqueAjacent.size();

    }

    public int getDegree(){
        return this.degree;
    }

    public double transitionProbability(Node o){
        if(o == null){
            throw new IllegalArgumentException();
        }

        // Task 3.2: Given another node o, obtain the transition probability from this node to the given node.
        // transition probability is calculated by f(this, o) / f(this, all).
        // f(this, o) is the frequency of o as the next node of this within all random walk sequences.
        // f(this, all) is the frequency of this having a next node within all random walk sequences.
        // When f(this, all) = 0, the transition probability is 0.

        int countNode=0;
        int countAll=0;

        for(RandomWalkSequence sequence:this.graph.getAllRandomWalkSequences()){
            ArrayList<Node> sequenceList=sequence.getSequence();
            for(int i=0;i<sequenceList.size()-1;i++){
                if(sequenceList.get(i).equals(this)){
                    countAll++;
                    if(sequenceList.get(i+1).equals(o)){
                        countNode++;
                    }
                }
            }


        }
        if(countAll==0){
            return 0.0;
        }else{
            return (double)countNode/countAll;
        }


    }
}