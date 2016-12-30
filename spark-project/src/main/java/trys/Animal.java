package trys;

/**
 * Created by liguodong on 2016/12/29.
 */
public interface Animal extends IFather{

    default void printHello(){
        System.out.println("Animal");
    }
    String run();

}
