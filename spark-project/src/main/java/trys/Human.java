package trys;

/**
 * Created by liguodong on 2016/12/29.
 */
public interface Human extends IFather{

    default void printHello(){
        System.out.println("Human");
    }

    String run();

}
