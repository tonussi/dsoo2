package coinhunter;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    Graph graph;

    public GraphTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        graph = new Graph();

        for (int i = 1; i <= 20; i++) {
            graph.addVertex(i);
        }

        graph.addEdge(1, 2);
        graph.addEdge(1, 15); // c

        graph.addEdge(2, 1);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5); // c

        graph.addEdge(3, 2);
        graph.addEdge(3, 9); // c

        graph.addEdge(4, 2);
        graph.addEdge(4, 9);
        graph.addEdge(4, 10); // c

        graph.addEdge(5, 2);
        graph.addEdge(5, 6); // c

        graph.addEdge(6, 5);
        graph.addEdge(6, 7);
        graph.addEdge(6, 8); // c

        graph.addEdge(7, 6); // c

        graph.addEdge(8, 6); // c

        graph.addEdge(9, 3);
        graph.addEdge(9, 4);
        graph.addEdge(9, 15);
        graph.addEdge(9, 18); // c

        graph.addEdge(10, 4);
        graph.addEdge(10, 12); // c

        graph.addEdge(11, 12);
        graph.addEdge(11, 14);
        graph.addEdge(11, 17); // c

        graph.addEdge(12, 10);
        graph.addEdge(12, 11);
        graph.addEdge(12, 13); // c

        graph.addEdge(13, 12); // c

        graph.addEdge(14, 11);
        graph.addEdge(14, 16); // c

        graph.addEdge(15, 1);
        graph.addEdge(15, 9); // c

        graph.addEdge(16, 14);
        graph.addEdge(16, 17);
        graph.addEdge(16, 18);
        graph.addEdge(16, 20); // c

        graph.addEdge(17, 11);
        graph.addEdge(17, 16); // c

        graph.addEdge(18, 9);
        graph.addEdge(18, 16);
        graph.addEdge(18, 19); // c

        graph.addEdge(19, 18);
        graph.addEdge(19, 20); // c

        graph.addEdge(20, 16);
        graph.addEdge(20, 19); // c
    }

    @After
    public void tearDown() {
        graph = null;
    }

    @Test
    public void edges() {
        List<Vertex> vertexList = graph.getAdjVertices(1);
        assertEquals(new Vertex(2), vertexList.get(0));
        assertEquals(new Vertex(15), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(2);
        assertEquals(new Vertex(1), vertexList.get(0));
        assertEquals(new Vertex(3), vertexList.get(1));
        assertEquals(new Vertex(4), vertexList.get(2));
        assertEquals(new Vertex(5), vertexList.get(3));
        assertEquals(4, vertexList.size());

        vertexList = graph.getAdjVertices(3);
        assertEquals(new Vertex(2), vertexList.get(0));
        assertEquals(new Vertex(9), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(4);
        assertEquals(new Vertex(2), vertexList.get(0));
        assertEquals(new Vertex(9), vertexList.get(1));
        assertEquals(new Vertex(10), vertexList.get(2));
        assertEquals(3, vertexList.size());

        vertexList = graph.getAdjVertices(5);
        assertEquals(new Vertex(2), vertexList.get(0));
        assertEquals(new Vertex(6), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(5);
        assertEquals(new Vertex(2), vertexList.get(0));
        assertEquals(new Vertex(6), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(6);
        assertEquals(new Vertex(5), vertexList.get(0));
        assertEquals(new Vertex(7), vertexList.get(1));
        assertEquals(new Vertex(8), vertexList.get(2));
        assertEquals(3, vertexList.size());

        vertexList = graph.getAdjVertices(7);
        assertEquals(new Vertex(6), vertexList.get(0));
        assertEquals(1, vertexList.size());

        vertexList = graph.getAdjVertices(8);
        assertEquals(new Vertex(6), vertexList.get(0));
        assertEquals(1, vertexList.size());

        vertexList = graph.getAdjVertices(9);
        assertEquals(new Vertex(3), vertexList.get(0));
        assertEquals(new Vertex(4), vertexList.get(1));
        assertEquals(new Vertex(15), vertexList.get(2));
        assertEquals(new Vertex(18), vertexList.get(3));
        assertEquals(4, vertexList.size());

        vertexList = graph.getAdjVertices(10);
        assertEquals(new Vertex(4), vertexList.get(0));
        assertEquals(new Vertex(12), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(11);
        assertEquals(new Vertex(12), vertexList.get(0));
        assertEquals(new Vertex(14), vertexList.get(1));
        assertEquals(new Vertex(17), vertexList.get(2));
        assertEquals(3, vertexList.size());

        vertexList = graph.getAdjVertices(12);
        assertEquals(new Vertex(10), vertexList.get(0));
        assertEquals(new Vertex(11), vertexList.get(1));
        assertEquals(new Vertex(13), vertexList.get(2));
        assertEquals(3, vertexList.size());

        vertexList = graph.getAdjVertices(13);
        assertEquals(new Vertex(12), vertexList.get(0));
        assertEquals(1, vertexList.size());

        vertexList = graph.getAdjVertices(14);
        assertEquals(new Vertex(11), vertexList.get(0));
        assertEquals(new Vertex(16), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(15);
        assertEquals(new Vertex(1), vertexList.get(0));
        assertEquals(new Vertex(9), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(16);
        assertEquals(new Vertex(14), vertexList.get(0));
        assertEquals(new Vertex(17), vertexList.get(1));
        assertEquals(new Vertex(18), vertexList.get(2));
        assertEquals(new Vertex(20), vertexList.get(3));
        assertEquals(4, vertexList.size());

        vertexList = graph.getAdjVertices(17);
        assertEquals(new Vertex(11), vertexList.get(0));
        assertEquals(new Vertex(16), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(18);
        assertEquals(new Vertex(9), vertexList.get(0));
        assertEquals(new Vertex(16), vertexList.get(1));
        assertEquals(new Vertex(19), vertexList.get(2));
        assertEquals(3, vertexList.size());

        vertexList = graph.getAdjVertices(19);
        assertEquals(new Vertex(18), vertexList.get(0));
        assertEquals(new Vertex(20), vertexList.get(1));
        assertEquals(2, vertexList.size());

        vertexList = graph.getAdjVertices(20);
        assertEquals(new Vertex(16), vertexList.get(0));
        assertEquals(new Vertex(19), vertexList.get(1));
        assertEquals(2, vertexList.size());
    }
}
