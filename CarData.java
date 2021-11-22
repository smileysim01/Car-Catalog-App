// --== CS400 Project Two File Header ==--
// Name: FNU Simran
// Email: simran4@wisc.edu
// Team: Red
// Group: DQ
// TA: Yuye J
// Lecturer: Florian Heimerl

interface CarDataInterface {
    public String getMake();
    public String getModel();
    public int getPrice();
}

/**
 * This is an object that stores the information about the individual cars
 *
 * @author Simran
 *
 */
public class CarData implements CarDataInterface {
    private String make;
    private String model;
    private int price;

    /**
     * This is the constructor that sets the variables
     *
     * @param make - The Maker or brand of the car
     * @param model - The model of the car
     * @param price - The price of the car
     */
    public CarData(String make, String model, int price)
    {
        this.make = make;
        this.model = model;
        this.price = price;
    }


    /**
     * @return - the Maker or brand of the car
     */
    @Override
    public String getMake() {
        return make;
    }

    /**
     * @return - the model of the car
     */
    @Override
    public String getModel() {
        return model;
    }

    /**
     * @return - the price of the car
     */
    @Override
    public int getPrice() {
        return price;
    }
}

