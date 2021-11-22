import java.util.List;

// interface (implemented with proposal)

interface SearchBackEndInterface {    
    public void addData(BackEndDataInterface data);
    public boolean containsCar(BackEndDataInterface data);

    // returns list of the titles of all songs that contain the word titleWord in their song title
    public  findCars(String titleWord);

    // returns list of the artists of all songs that contain the word titleWord in their song title
    public  findPrice(double price);

    // returns the number of songs that contain the word titleWord in their song title, and were published in year
    public int findNumberOfCars();
	public buildTree();
public findModel();
}

// public class (implemented primarilly in final app week)

public class SearchBackEnd implements SearchBackEndInterface {
	buildtree(); 
    public void addData(T data ,double price, String model, String title)
 {
   tree.insert(data,price, model, title);
   
        }
    }

    @Override
    public findPrice(double price) 
{
        // TODO Auto-generated method stub
    tree.containPrice(price);
    }

    @Override
    public int findNumberOfCars() {
        // TODO Auto-generated method stub
        return tree.size;
    }

    @Override
    public findCars(String titleWord)
{
	tree.containCar(titleWord);
}
@Override
public findModel(String model)

{
	tree.containModel(model);
}
 public buildTree()
{
	RedBlackTree tree=new RedBlackTree(); 
}

