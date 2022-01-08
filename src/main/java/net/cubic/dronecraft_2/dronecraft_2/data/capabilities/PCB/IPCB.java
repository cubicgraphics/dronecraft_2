package net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;

public interface IPCB {

    void setPCBData(PCBData newPCBData); //sets the pcb data in the PCB
    PCBData getPCBData(); //gets the pcb data in the pcb

}
