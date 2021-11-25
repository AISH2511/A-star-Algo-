import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class City{
    double g_dist = Integer.MAX_VALUE;
    double h_dist;
    double f_dist = Integer.MAX_VALUE;
    int x_coord;
    int y_coord;
    int id;
    City parentCity;
    ArrayList<Edge> adjacency = new ArrayList<>();
    City(int id) {
        this.id=id;
    }
    public void setCoord(int x, int y){
        x_coord = x;
        y_coord = y;
    }
    public void setHdist(City c2){
        //Set the h distance between 2 cities using coordinates.
        int x_dif = Math.abs(this.x_coord - c2.x_coord);
        int y_dif = Math.abs(this.y_coord - c2.y_coord);
        this.h_dist = Math.hypot(x_dif, y_dif);
    }
    public void addNeigh(City n, double dist){
        Edge edge = new Edge(n, dist);
        adjacency.add(edge);
    }
}
 
class Edge {
    public final double dist;
    public final City destination;
    public Edge(City destination, double dist){
        this.destination = destination;
        this.dist = dist;
    }
}

class InvalidCityException extends Exception{

}
 
class A_star{
    PriorityQueue<City> pathq = new PriorityQueue<>(new Comparator<City>() {
        @Override
        public int compare(City c1, City c2){
            return Double.compare(c1.f_dist, c2.f_dist);
        }        
    });
    double finaldist = 0.0;
    public double searchAStar(City source, City dest, ArrayList<City> allCities) throws InvalidCityException{
        if(!allCities.contains(source) || !allCities.contains(dest)){
            throw new InvalidCityException();
        }
        else{
        for(City town: allCities){
            town.setHdist(dest);
        }
        source.g_dist = 0;
        source.f_dist = source.h_dist;
        pathq.add(source);
        while(!pathq.isEmpty()){
            City current = pathq.poll();
            finaldist = current.g_dist;
            if(current.id==dest.id){
                break;
            }
            for(Edge edge: current.adjacency){
                City neighbour = edge.destination;
                double dist = edge.dist;
                double new_g_dist = current.g_dist + dist;
                double new_f_dist = new_g_dist + neighbour.h_dist;
                if(new_f_dist<=neighbour.f_dist){
                    neighbour.parentCity = current;
                    neighbour.g_dist = new_g_dist;
                    neighbour.f_dist = new_f_dist;
               
                if(pathq.contains(neighbour)){
                    pathq.remove(neighbour);
                }
                pathq.add(neighbour);
            }
            }
        }
        return finaldist;}
 
    }
}
 
class Dijkstra{
    PriorityQueue<City> pathq = new PriorityQueue<>(new Comparator<City>() {
        @Override
        public int compare(City c1, City c2){
            return Double.compare(c1.g_dist, c2.g_dist);
        }        
    });
  double finaldist = 0.0;
    public double searchDijkstra(City source, City dest, ArrayList<City> allCities) throws InvalidCityException{
        if(!allCities.contains(source) || !allCities.contains(dest)){
            throw new InvalidCityException();
        }
        else{
        source.g_dist = 0;
        pathq.add(source);
        while(!pathq.isEmpty()){
            City current = pathq.poll();
            finaldist = current.g_dist;
            if(current.id==dest.id){
                break;
            }
            for(Edge edge: current.adjacency){
                City neighbour = edge.destination;
                double dist = edge.dist;
                double new_dist = current.g_dist+dist;
                if(new_dist<=neighbour.g_dist){
                    neighbour.parentCity=current;
                    neighbour.g_dist=new_dist;
                    if(pathq.contains(neighbour)){
                        pathq.remove(neighbour);
                    }
                    pathq.add(neighbour);
                }
            }
 
    }
    return finaldist;}
}
}
 
public class App {
    public static void main(String[] args) {
      ArrayList<City> allCities = new ArrayList<>();
      int x0 = Integer.parseInt(JOptionPane.showInputDialog("Enter number of cities").trim());
      for(int m=0; m<x0; m++) {
          allCities.add(new City(m));
      }
      String city[]= new String[3];
      int cities[] = new int[3];
     
      int i = Integer.parseInt(JOptionPane.showInputDialog("Enter number of connections"));
       //JOptionPane.showMessageDialog(null,"Thanks for the response");
       for(int j=0;j<i;j++)
       {
           String s1 = JOptionPane.showInputDialog("Enter two city names and the distance between them");
           city = s1.split(" ");
           for(int x=0;x<3;x++) {
               cities[x] = Integer.parseInt(city[x].trim());
           }
           allCities.get(cities[0]).addNeigh(allCities.get(cities[1]), cities[2]);
       }
     
       //JOptionPane.showMessageDialog(null,"Thanks for the response");
 
       for(int k=0;k<x0;k++) {
       String s2 = JOptionPane.showInputDialog("Enter city followed by coordinates");
       city = s2.split(" ");
       for(int x=0;x<3;x++) {
           cities[x] = Integer.parseInt(city[x].trim());
       }
       allCities.get(cities[0]).setCoord(cities[1], cities[2]);
       }
       
     
       String s3 = JOptionPane.showInputDialog("Enter the two cities between which you need to get the shortest distance");
       JOptionPane.showMessageDialog(null,"Thanks for the response");
       A_star first_dist = new A_star();
       Dijkstra second_dist = new  Dijkstra();
       city = s3.split(" ");
       cities[0] = Integer.parseInt(city[0]);
       cities[1] = Integer.parseInt(city[1]);
       try{
       double res1 = first_dist.searchAStar(allCities.get(cities[0]), allCities.get(cities[1]), allCities);
       JOptionPane.showMessageDialog(null,res1,"SHORTEST DISTANCE USING A* ALGORITHM",JOptionPane.PLAIN_MESSAGE);
       }
       catch (Exception e){
           //We need to show message - City does not exist. Add kar dena
           JOptionPane.showMessageDialog(null,"City does not exist");
       }
       try{
       double res2 = second_dist.searchDijkstra(allCities.get(cities[0]), allCities.get(cities[1]), allCities);
       JOptionPane.showMessageDialog(null,res2,"SHORTEST DISTANCE USING DIJKSTRA ALGORITHM",JOptionPane.PLAIN_MESSAGE);
       }
       catch (Exception e){
        //We need to show message - City does not exist.
        JOptionPane.showMessageDialog(null,"City does not exist");
    }
    }
   
}
