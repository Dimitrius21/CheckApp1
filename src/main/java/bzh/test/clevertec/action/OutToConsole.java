package bzh.test.clevertec.action;


public class OutToConsole implements OutDataInterface {

    @Override
    public void out(String data) {
        System.out.println(data);
    }
}
