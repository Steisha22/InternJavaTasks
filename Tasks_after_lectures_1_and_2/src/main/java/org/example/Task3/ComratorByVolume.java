package org.example.Task3;

import java.util.Comparator;

public class ComratorByVolume implements Comparator<Figure> {

    //Allows to sort figures by volume using method list.sort()
    @Override
    public int compare(Figure o1, Figure o2) {
        return o1.compareTo(o2);
    }
}
