package net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;

public class DefaultPCB implements IPCB {

    PCBData pcbData;

    public DefaultPCB(){
        pcbData = new PCBData();
    }

    @Override
    public void setPCBData(PCBData newPCBData) {
        this.pcbData = newPCBData;
    }

    @Override
    public PCBData getPCBData() {
        return pcbData;
    }
}
