package FxControllers;

public abstract class SuitedController {
    protected Controller rootController;
    protected void setRootController(Controller controller)
    {
        this.rootController = controller;
    }
    protected void loadDataFromRootController() {};
}
