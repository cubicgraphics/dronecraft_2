package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

public class ComponentXY {
    public int x;
    public int y;
    public boolean BuiltInComponent;
    public int ComponentID;

    public ComponentXY(int X, int Y, Boolean BuiltIn, int componentID){
        x = X;
        y = Y;
        BuiltInComponent = BuiltIn;
        ComponentID = componentID;
    }
}
