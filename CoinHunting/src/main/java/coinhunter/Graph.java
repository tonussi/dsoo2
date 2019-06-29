package coinhunter;

import java.util.*;

public class Graph {

    Vertex root;
    Random rand;
    Set<Dog> sleeping;
    Map<Integer, Vertex> vertexes;
    Map<Vertex, List<Vertex>> adjVertices;

    public Graph() {
        this.rand = new Random(System.nanoTime());
        this.adjVertices = new HashMap<>();
        this.vertexes = new HashMap<>();
        this.sleeping = new HashSet<>();
    }

    public Map<Vertex, List<Vertex>> getAdjVertices() {
        return adjVertices;
    }

    public void setAdjVertices(Map<Vertex, List<Vertex>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    public Vertex getRoot() {
        return root;
    }

    public void setRoot(Vertex root) {
        this.root = root;
    }

    public void addVertex(Integer label) {
        Vertex v = new Vertex(label);
        adjVertices.putIfAbsent(v, new ArrayList<>());

        vertexes.putIfAbsent(label, v);

        if (label == 1) {
            root = v;
        }
    }

    public void removeVertex(Integer label) {
        Vertex v = new Vertex(label);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));
    }

    public void addEdge(Integer label1, Integer label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);

        if (!adjVertices.get(v1).contains(v2)) {
            adjVertices.get(v1).add(v2);
        }

        if (!adjVertices.get(v2).contains(v1)) {
            adjVertices.get(v2).add(v1);
        }
    }

    public void removeEdge(Integer label1, Integer label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null) {
            eV1.remove(v2);
        }
        if (eV2 != null) {
            eV2.remove(v1);
        }
    }

    public List<Vertex> getAdjVertices(Integer label) {
        return adjVertices.get(new Vertex(label));
    }

    public List<Vertex> getRandomAdjVertices() {
        return adjVertices.get(new Vertex(Util.rand(1, 20, rand)));
    }

    public Vertex getNextRandomAdjVertex(Integer position) {
        List<Vertex> l = adjVertices.get(new Vertex(position));
        // escolhe um vertice aleatorio da lista de adjacentes
        int n = Util.rand(0, l.size() - 1, rand);
        return l.get(n);
    }

    public Vertex getNodeAt(Integer position) {
        return vertexes.get(position);
    }

}
