package trys;

/**
 * Created by liguodong on 2016/12/29.
 */
public class SuperMan implements Animal,Human{

    @Override
    public void printHello() {
        Human.super.printHello();
        Animal.super.printHello();
    }

    public String run() {
        return "d";
    }


}
