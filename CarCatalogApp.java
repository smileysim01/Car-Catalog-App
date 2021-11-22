import java.util.List;
public class CarCatalogApp{

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Car Catalog");
        List<CarDataInterface> cars = new CarLoader().loadAllFilesInDirectory("./data/");
        SearchBackEndInterface engine = new SearchBackEnd();
        for(CarDataInterface car : cars) engine.addCar(car);
        SearchFrontEndInterface ui = new SearchFrontEnd();
        ui.run(engine);
    }

}

